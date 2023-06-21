package project2.client;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

import project2.Logger;
import project2.RequestHandler;

/**
 * Class that represents a client in the Remote Method Invocation simulation. This is where all
 * users connect to the server and send requests which send back a response.
 */
public class Client extends Logger {
  private RequestHandler server;
  private final Scanner sc;

  /**
   * Constructor to initialize the scanner object for user input. This is called when the object
   * to export has not been created yet.
   */
  public Client() {
    this.server = null;
    this.sc = new Scanner(System.in);
  }

  private void setServer(RequestHandler server) {
    this.server = server;
  }

  private void start() {
    String command = "";
    showInfo("Connected to server\n");
    showInfo(
            "\nAll valid request formats:\n\n" +
                    "GET x\n" +
                    "PUT x y\n" +
                    "DELETE x\n" +
                    "STOP\n\n" +
                    "Requests are tab separated. eg : PUT \\t This is the key \\t This is the value\n");
    while (true) {
      try {
        if (command.trim().equalsIgnoreCase("stop")) {
          break;
        }
        showInfo("REQ to send: ");
        command = sc.nextLine();
        String res = server.handleRequest(command);
        showResponse(res);
      } catch (IllegalArgumentException | IOException e) {
        showError(e.getMessage());
      }
    }
  }

  /**
   * Driver that is the entry point for the client. This method is executed when we run the
   * program where it validates the cli arguments received and calls the required methods to
   * proceed further in the program execution.
   *
   * @param args String array for command line arguments to be passed when running the program.
   *             For this program this array should have two elements which are the ip address
   *             and port number to locate the exported remote object.
   */
  public static void main(String[] args) {
    Client client = new Client();
    try {
      if (args.length != 2) {
        throw new IllegalArgumentException("Incorrect cli arguments. Must be exactly 2. ip and port");
      }

      int port = Integer.parseInt(args[1]);

      client.showInfo("Establishing connection with server...\n");

      Registry registry = LocateRegistry.getRegistry(args[0], port);
      client.showInfo("Located registry\n");

      RequestHandler server = (RequestHandler) registry.lookup("handler");
      client.showInfo("Found request handler\n");
      client.setServer(server);

      client.start();
    } catch (IllegalArgumentException | RemoteException | NotBoundException e) {
      client.showError(e.getMessage());
    }
  }
}
