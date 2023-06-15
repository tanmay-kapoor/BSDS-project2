package project2.Server;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Timestamp;

import project2.Logger;
import project2.RequestHandler;

public class Server extends Logger {

  @Override
  public String getTimestamp() {
    Timestamp timestamp = new Timestamp(System.currentTimeMillis());
    return "[" + timestamp + "]";
  }

  public static void main(String[] args) {
    Server server = new Server();
    try {
      if (args.length != 2) {
        throw new IllegalArgumentException("Incorrect cli arguments. Must be exactly 2. ip and port");
      }
      int port = Integer.parseInt(args[1]);

      server.showInfo("Starting server...\n");

      System.setProperty("java.rmi.server.hostname", args[0]);
      RequestHandler obj = new RequestHandlerImpl();
      RequestHandler handler = (RequestHandler) UnicastRemoteObject.exportObject(obj, port);

      server.showInfo("Creating Registry\n");
      Registry registry = LocateRegistry.createRegistry(port);

      registry.rebind("handler", handler);
      server.showInfo("Server started successfully\n\n");
    } catch (IllegalArgumentException | RemoteException e) {
      server.showError(e.getMessage());
    }
  }
}
