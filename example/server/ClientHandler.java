package example.server;

import java.io.*;
import java.net.Socket;
import java.util.Observer;
import java.util.Observable;

class ClientHandler implements Runnable, Observer {

  private Server server;
  private Socket clientSocket;
  private DataInputStream fromClient;
  private PrintWriter toClient;

  protected ClientHandler(Server server, Socket clientSocket) {
    this.server = server;
    this.clientSocket = clientSocket;
    try {
      fromClient = new DataInputStream(this.clientSocket.getInputStream());
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
    String input;
    try {
      boolean done = false;
      while(!done){
        byte type = fromClient.readByte();
        switch(type){
          case 1:
            String in = fromClient.readUTF();
        }
      }
      while ((input = fromClient.readLine()) != null) {
        System.out.println("From client: " + input);
        server.processRequest(input);
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