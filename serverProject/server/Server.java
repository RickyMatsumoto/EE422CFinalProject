package server;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.Observable;

import com.google.gson.Gson;

class Server extends Observable {

  public static Data data;

  public static void main(String[] args) {
    new Server().runServer();
  }

  private void runServer() {
    try {
      data = new Data();
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

  protected void processRequest(Message input) {
    String output = "0";
//    Gson gson = new Gson();
//    Message message = gson.fromJson(input, Message.class);
    try {
      String temp = "";
      switch (input.code) {
        case 2:
          String[] split = input.content.split(" ");
          Item item = data.getItem(split[0]);
          if(item != null){
            double bid = Double.parseDouble(split[1]);
            if(item.bid(bid)){
              item.price = bid;
              output = data.getPriceUpdateMessage(item);
              //update everyone on new bid price
            } else {
              //send invalid bid price
            }
            this.setChanged();
            this.notifyObservers(output);
          }
          break;

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

}