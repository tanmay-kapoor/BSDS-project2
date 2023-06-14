package project2.Client;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

import project2.RequestHandler;


public class Client implements Runnable {
  RequestHandler server;
  Scanner sc;

  public Client(RequestHandler server) {
    this.server = server;
    this.sc = new Scanner(System.in);
  }

  @Override
  public void run() {
    String command;
    System.out.println("Enter commands\n");
    while (true) {
      try {
        command = sc.nextLine();
        String res = server.handleRequest(command);
        System.out.println("RES: " + res);
        if (command.trim().equalsIgnoreCase("stop")) {
          break;
        }
      } catch (IllegalArgumentException | RemoteException e) {
        System.out.println(e.getMessage());
      }
    }
  }

  public static void main(String[] args) {
    try {
      if (args.length != 2) {
        throw new IllegalArgumentException("Incorrect cli arguments. Must be exactly 2. ip and port");
      }

      int port = Integer.parseInt(args[1]);

      Registry registry = LocateRegistry.getRegistry(args[0], port);
      RequestHandler server = (RequestHandler) registry.lookup("handler");
      new Thread(new Client(server)).start();
    } catch (IllegalArgumentException | RemoteException | NotBoundException e) {
      System.out.println(e.getMessage());
    }
  }
}
