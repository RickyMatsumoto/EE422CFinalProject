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
    try {
      String[] split = input.content.split(" ");
      switch (input.code) {
        case 0:
          client.username = split[0];
          break;

        case 1:
          synchronized(o){
            Item item = data.getItem(split[0]);
            item.time = -1;
            item.price = item.buyNow;
            item.customer = split[1];
            output = data.getPriceUpdateMessage(item);
            this.setChanged();
            this.notifyObservers(output);
            output = data.closeBidding(item);
            this.setChanged();
            this.notifyObservers(output);
          }
          break;

        case 2:
          synchronized(o){
            Item item = data.getItem(split[0]);
            String customer = split[2];
            if(item != null){
              double bid = Double.parseDouble(split[1]);
              if(item.bid(bid, customer)){
                item.price = bid;
                item.customer = customer;
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

        case 3:
          String name = split[0];
          double price = Double.parseDouble(split[1]);
          double buyNow = Double.parseDouble(split[2]);
          int time = 60 * Integer.parseInt(split[3]);
          Item add = new Item(name, price, buyNow, time, null);
          output = data.list(add);
          this.setChanged();
          this.notifyObservers(output);
          break;

        default:
          break;

      }

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