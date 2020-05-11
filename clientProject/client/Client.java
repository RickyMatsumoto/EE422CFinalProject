package client;

import java.io.*;
import java.util.HashMap;
import java.util.Scanner;
import java.net.Socket;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Client extends Application {

  private static String host = "127.0.0.1";
  private BufferedReader fromServer;
  private PrintWriter toServer;
  public gBay controller;
  private HashMap<Integer, Listing> listings = new HashMap<>();


  @Override
  public void start(Stage stage) throws Exception {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("Client.fxml"));
    Parent root = loader.load();
    controller = loader.getController();
    setUpNetworking();
    Scene scene = new Scene(root);
    stage.setScene(scene);
    stage.setTitle("gBay");
    stage.show();
  }


  public static void main(String[] args) {
    launch(args);
  }

  private void setUpNetworking() throws Exception {
    @SuppressWarnings("resource")
    Socket socket = new Socket(host, 4242);
    System.out.println("Connecting to... " + socket);
    fromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    toServer = new PrintWriter(new PrintWriter(socket.getOutputStream()));

    Thread readerThread = new Thread(new Runnable() {
      @Override
      public void run() {
        String input;
        try {
          while ((input = fromServer.readLine()) != null) {
            System.out.println("From server: " + input);
            Message in = new Message(input);
            processRequest(in);
          }
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    });

    Thread writerThread = new Thread(new Runnable() {
      @Override
      public void run() {

        while (true) {
//          String input;
//          int code = Integer.parseInt(input.charAt(0));
//          Message request = new Message(code, input);
//          GsonBuilder builder = new GsonBuilder();
//          Gson gson = builder.create();
//          sendToServer(gson.toJson(request));
        }
      }
    });

    readerThread.start();
    writerThread.start();
  }

  protected void processRequest(Message input) {
    switch(input.code){
      case 0:
        initListings(input.content);
        break;

      case 1:
        updatePrice(input.content);
        break;


      default:
        break;
    }
  }

  protected void sendToServer(String string) {
    System.out.println("Sending to server: " + string);
    toServer.println(string);
    toServer.flush();
  }

  protected void bidToServer(String item, String amount) {
    double output = Double.parseDouble(amount);
    sendToServer("2 " + item + " " + output);
  }

  public void initListings(String message){
    String[] messageArr = message.split(" ");
    int numItems = Integer.parseInt(messageArr[0]);
    String name;
    double price;
    double buyNow;
    int time;
    for(int i = 1; i <= numItems*4; i++){
      name = messageArr[i];
      i++;
      price = Double.parseDouble(messageArr[i]);
      i++;
      buyNow = Double.parseDouble(messageArr[i]);
      i++;
      time = Integer.parseInt(messageArr[i]);
      Item add = new Item(name, price, buyNow, time, i);
      Data.items.add(add);
    }
    for(Item item : Data.items){
      try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Listing.fxml"));
        Node node = loader.load();
        Listing temp = loader.getController();
        temp.setup(item.name, item.price, item.buyNow, item.time, this, item.id);
        controller.add(node);
        listings.put(item.id, temp);
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  public void updatePrice(String message){
    String[] messageArr = message.split(" ");
    String name = messageArr[0];
    double price = Double.parseDouble(messageArr[1]);
    Item found;
    for(Item item : Data.items){
      if(item.name.equals(name)){
        item.price = price;
        found = item;
        Listing listing = listings.get(found.id);
        Platform.runLater(new Runnable() {
          @Override
          public void run() {
            listing.setPrice(price);
          }
        });
        break;
      }
    }
  }

}