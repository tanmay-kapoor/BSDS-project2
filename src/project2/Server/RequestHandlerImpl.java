package project2.Server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;

import project2.RequestHandler;

public class RequestHandlerImpl extends UnicastRemoteObject implements RequestHandler {
  private final Map<String, String> map;

  public RequestHandlerImpl() throws RemoteException {
    super();
    map = new HashMap<>();
    // read values from file
  }

  @Override
  public String handleRequest(String command) throws RemoteException {
    command = command.trim();
    String res;

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
        this.disconnectClient();
        res = "Disconnected client";
        break;

      default:
        System.out.println("invalid request");
        throw new IllegalArgumentException("Invalid request. Must be GET, PUT, DELETE or STOP only.");
    }
    return res;
  }

  @Override
  public String get(String key) throws RemoteException {
    if (!map.containsKey(key)) {
      throw new IllegalArgumentException("Can't get key that doesn't exist");
    }

    return map.get(key);
  }

  @Override
  public void put(String key, String value) throws RemoteException {
    map.put(key, value);
  }

  @Override
  public void delete(String key) throws RemoteException {
    if (!map.containsKey(key)) {
      throw new IllegalArgumentException("Can't delete key that doesn't exist");
    }

    map.remove(key);
  }

  @Override
  public void disconnectClient() throws RemoteException {
    // write to file and disconnect client
  }

  private void validateRequest(String[] req, int len) {
    if (req.length != len) {
      String msg = "Invalid number of arguments with " + req[0] + " request. Must be exactly " + (len - 1);
      throw new IllegalArgumentException(msg);
    }
  }
}
