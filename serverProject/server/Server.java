package server;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Observable;
import java.util.TimerTask;

import com.google.gson.Gson;
import javafx.application.Platform;

class Server extends Observable {

  public static Data data;
  public static int clientIDCount;
  public static Server thisServer;
  public static final Object o = new Object();

  public static void main(String[] args) {
    new Server().runServer();
  }

  private void runServer() {
    try {
      data = new Data();
      clientIDCount = 1;
      thisServer = this;
      setUpNetworking();
    } catch (Exception e) {
      e.printStackTrace();
      return;
    }
  }

  private void setUpNetworking() throws Exception {
    @SuppressWarnings("resource")
    ServerSocket serverSock = new ServerSocket(4242);
    while (true) {
      Socket clientSocket = serverSock.accept();
      System.out.println("Connecting to... " + clientSocket);

      ClientHandler handler = new ClientHandler(this, clientSocket);
      this.addObserver(handler);

      Thread t = new Thread(handler);
      t.start();
    }
  }

  protected void processRequest(Message input, ClientHandler client) {
    String output = null;
//    Gson gson = new Gson();
//    Message message = gson.fromJson(input, Message.class);
    try {
      String[] split = input.content.split(" ");
      switch (input.code) {
        case 0:
          client.username = split[0];
          break;
          
        case 2:
          Item item = data.getItem(split[0]);
          String customer = split[1];
          synchronized(o){
            if(item != null){
              double bid = Double.parseDouble(split[1]);
              if(item.bid(bid, customer)){
                item.price = bid;
                output = data.getPriceUpdateMessage(item);
                //update everyone on new bid price
              } else {
                output = "99 " + item.name;
              }
              this.setChanged();
              this.notifyObservers(output);
            }
            break;
          }

        default:
          break;

      }
//      output = "";
//      for (int i = 0; i < message.number; i++) {
//        output += temp;
//        output += " ";
//      }

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void updateObservers(String output) {
    this.setChanged();
    this.notifyObservers(output);
  }

}

class countdownTimer extends TimerTask {

  @Override
  public void run() {
    ArrayList<Item> toSell = new ArrayList<>();
    for (Item item : Data.getItems()) {
      if(!item.decrementSecond()){
        toSell.add(item);
      }
    }
    for (Item item : toSell){
      String output = Server.data.closeBidding(item);
      Server.thisServer.updateObservers(output);
    }
  }
}