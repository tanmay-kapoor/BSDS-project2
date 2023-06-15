package project2;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RequestHandler extends Remote {

  String handleRequest(String command) throws RemoteException;
}
