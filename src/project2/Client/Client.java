package project2.Client;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

import project2.Logger;
import project2.RequestHandler;


public class Client extends Logger implements Runnable {
  RequestHandler server;
  Scanner sc;

  public Client() {
    this.server = null;
    this.sc = new Scanner(System.in);
  }

  private void setServer(RequestHandler server) {
    this.server = server;
  }

  @Override
  public void run() {
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

      Thread thread = new Thread(client);
      thread.start();
    } catch (IllegalArgumentException | RemoteException | NotBoundException e) {
      client.showError(e.getMessage());
    }
  }
}
