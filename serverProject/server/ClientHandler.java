// Copy-paste this file at the top of every file you turn in.
/*
 * EE422C Final Project submission by
 * Replace <...> with your actual data.
 * Tatsushi Matsumoto
 * trm2796
 * 16295
 * Spring 2020
 */

package server;

import java.io.*;
import java.net.Socket;
import java.util.Observer;
import java.util.Observable;

class ClientHandler implements Runnable, Observer {

  private Server server;
  private Socket clientSocket;
  private BufferedReader fromClient;
  private PrintWriter toClient;
  private int clientID;
  public String username;

  protected ClientHandler(Server server, Socket clientSocket) {
    this.server = server;
    this.clientSocket = clientSocket;
    clientID = Server.clientIDCount;
    Server.clientIDCount++;
    try {
      fromClient = new BufferedReader(new InputStreamReader(this.clientSocket.getInputStream()));
      toClient = new PrintWriter(this.clientSocket.getOutputStream());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  protected void sendToClient(String string) {
    System.out.println("Sending to client: " + string);
    toClient.println(string);
    toClient.flush();
  }

  @Override
  public void run() {
    sendToClient("2 " + clientID);
    sendToClient(Server.data.getInitMessage());
    String input;
    try {
      boolean done = false;
      while ((input = fromClient.readLine()) != null) {
        System.out.println("From client: " + input);
        Message in = new Message(input);
        server.processRequest(in, this);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void update(Observable o, Object arg) {
    this.sendToClient((String) arg);
  }
}