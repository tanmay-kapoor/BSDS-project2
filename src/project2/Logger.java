package project2;

import java.sql.Timestamp;

public class Logger {
  protected void showInfo(String msg) {
    System.out.print(msg);
  }

  protected void showRequest(String req) {
    System.out.println(getTimestamp() + " REQ: " + req);
  }

  protected void showResponse(String res) {
    System.out.println(getTimestamp() + " RES: " + res);
  }

  protected void showError(String msg) {
    System.out.println(getTimestamp() + " ERROR: " + msg);
  }

  private String getTimestamp() {
    Timestamp timestamp = new Timestamp(System.currentTimeMillis());
    return "[" + timestamp + "]";
  }
}
