package project2;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RequestHandler extends Remote {

  String handleRequest(String command) throws RemoteException;

  String get(String key) throws RemoteException;

  void put(String key, String value) throws RemoteException;

  void delete(String key) throws RemoteException;

  void disconnectClient() throws RemoteException;
}
