package project2.Server;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import project2.Logger;
import project2.RequestHandler;

/**
 * Class that simulates the server in the Java RMI client/server model.
 * This class validates the arguments provided from cli, establishes connection at the specified
 * ip address, and also creates the rmi registry which will hold or RequestHandler object.
 */
public class Server extends Logger {

  /**
   * Driver method that is the entry point of the program.
   * This method is executed when we run the program where it validates the cli arguments received
   * and calls the required methods to proceed further in the program execution.
   *
   * @param args String array for command line arguments to be passed when running the program.
   *             For this program this array should have two elements which are the ip address
   *             of host and port number to export the object to.
   */
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
