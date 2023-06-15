package project2;

import java.io.IOException;
import java.rmi.Remote;

public interface RequestHandler extends Remote {

  String handleRequest(String command) throws IOException;
}
