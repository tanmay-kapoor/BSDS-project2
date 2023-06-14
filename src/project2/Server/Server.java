package project2.Server;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import project2.RequestHandler;

public class Server {
  public static void main(String[] args) {
    try {
      if (args.length != 2) {
        throw new IllegalArgumentException("Incorrect cli arguments. Must be exactly 2. ip and port");
      }
      int port = Integer.parseInt(args[1]);

      System.out.println("Server running");

      System.setProperty("java.rmi.server.hostname", args[0]);
      RequestHandler handler = new RequestHandlerImpl();

      Registry registry = LocateRegistry.createRegistry(port);
      System.out.println("Registry created");

      registry.rebind("handler", handler);
      System.out.println("Rebind for request handler done\n");
    } catch (IllegalArgumentException | RemoteException e) {
      System.out.println(e.getMessage());
    }
  }
}
