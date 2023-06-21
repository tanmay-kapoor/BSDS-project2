package project2;

import java.io.IOException;
import java.rmi.Remote;

/**
 * Interface that holds the common methods that can be performed by the server. Implementations of
 * this interface are the classes that handle all the requests. This interface's implementations
 * are also added to the rmi registry which is then used at the client side to retrieve and
 * call its methods.
 */
public interface RequestHandler extends Remote {

  /**
   * Method that is the gateway for all GET, PUT, DELETE or disconnect requests. All requests are
   * taken in the parameter as a tab separated string and depending on the request type retrieved
   * from the content of the string, it is processed differently.
   *
   * @param command The request as a tab separated String.
   * @return The response that is sent to the client as a String.
   * @throws IOException in case of any errors while interaction between the server/client and/or
   *                     any read-write problems.
   */
  String handleRequest(String command) throws IOException;
}
