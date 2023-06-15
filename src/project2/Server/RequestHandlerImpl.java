package project2.Server;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URISyntaxException;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import project2.Logger;
import project2.RequestHandler;

public class RequestHandlerImpl extends Logger implements RequestHandler {
  private final Map<String, String> map;
  private final ReadWriteLock rwlock;
  private String filePath;

  public RequestHandlerImpl() throws RemoteException {
    super();
    map = new HashMap<>();
    rwlock = new ReentrantReadWriteLock();
    showInfo("Populating HashMap\n");

    // read values from file
    try {
      String fileName = "contents.json";
      File file = new File(getClass().getProtectionDomain().getCodeSource().getLocation().toURI());
      filePath = file.getParent() + "/" + fileName;
      readFromFile();
    } catch (URISyntaxException e) {
      showError(e.getMessage());
    }
  }

  private void readFromFile() {
    try {
      InputStream is = new FileInputStream(filePath);
      Reader reader = new InputStreamReader(is);

      JSONParser jsonParser = new JSONParser();
      JSONObject jsonObject = (JSONObject) jsonParser.parse(reader);
      JSONArray data = (JSONArray) jsonObject.get("data");
      for (Object pair : data) {
        JSONObject jsonPair = (JSONObject) pair;
        String key = (String) jsonPair.get("key");
        String value = (String) jsonPair.get("value");
        map.put(key, value);
      }
    } catch (FileNotFoundException ignored) {
      // file does not exist. But hashmap is already initialized hence ignore.
    } catch (IOException | ParseException e) {
      showError(e.getMessage());
    }
  }

  @Override
  public String handleRequest(String command) throws IOException {
    command = command.trim();
    String res;

    showRequest(command);

    String[] req = command.split("\\t+");
    req[0] = req[0].toUpperCase();

    switch (req[0]) {
      case "GET":
        validateRequest(req, 2);
        res = this.get(req[1]);
        break;

      case "PUT":
        validateRequest(req, 3);
        this.put(req[1], req[2]);
        res = "Put successful";
        break;

      case "DELETE":
        validateRequest(req, 2);
        this.delete(req[1]);
        res = "Delete successful";
        break;

      case "STOP":
        validateRequest(req, 1);
        this.writeToFile();
        res = "Disconnected client";
        break;

      default:
        showError("Invalid request");
        throw new IllegalArgumentException("Invalid request. Must be GET, PUT, DELETE or STOP only.");
    }

    return res;
  }

  private String get(String key) {
    rwlock.readLock().lock();
    if (!map.containsKey(key)) {
      rwlock.readLock().unlock();
      throw new IllegalArgumentException("Can't get key that doesn't exist");
    }
    rwlock.readLock().unlock();
    return map.get(key);
  }

  private void put(String key, String value) {
    rwlock.writeLock().lock();
    map.put(key, value);
    rwlock.writeLock().unlock();
  }

  private void delete(String key) {
    rwlock.writeLock().lock();
    if (!map.containsKey(key)) {
      rwlock.writeLock().unlock();
      throw new IllegalArgumentException("Can't delete key that doesn't exist");
    }
    map.remove(key);
    rwlock.writeLock().unlock();
  }

  private void writeToFile() throws IOException {
    // write to file and disconnect client
    JSONObject jsonObject = new JSONObject();
    JSONArray data = new JSONArray();
    for (String key : map.keySet()) {
      JSONObject details = new JSONObject();
      details.put("key", key);
      details.put("value", map.get(key));
      data.add(details);
    }
    jsonObject.put("data", data);

    FileWriter writer = new FileWriter(filePath);
    writer.write(jsonObject.toJSONString());
    writer.close();
  }

  private void validateRequest(String[] req, int len) {
    if (req.length != len) {
      String msg = "Invalid number of arguments with " + req[0] + " request. Must be exactly " + (len - 1);
      throw new IllegalArgumentException(msg);
    }
  }
}
