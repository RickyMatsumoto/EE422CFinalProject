package client;

import java.io.*;
import java.util.HashMap;
import java.util.Scanner;
import java.net.Socket;
import java.util.Timer;
import java.util.TimerTask;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Client extends Application {

  private static String host = "127.0.0.1";
  private BufferedReader fromServer;
  private PrintWriter toServer;
  public gBay controller;
  public Sold soldController;
  public Sell sellController;
  public Parent root;
  public HashMap<Integer, Listing> listings = new HashMap<>();
  public static Client thisClient;
  public static Stage mainStage;
  public static Scene home;
  public static Scene history;
  public static Scene sell;



  @Override
  public void start(Stage stage) throws Exception {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"));
    Parent base = loader.load();
    Login control = loader.getController();
    Scene scene = new Scene(base);
    stage.setScene(scene);
    stage.setTitle("gBay");
    mainStage = stage;
    thisClient = this;
    stage.show();
  }

  public void homePage(Stage stage) throws Exception {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("Sell.fxml"));
    Parent sLoad = loader.load();
    if(sell == null){
      sellController = loader.getController();
      sell = new Scene(sLoad);
    }
    loader = new FXMLLoader(getClass().getResource("Sold.fxml"));
    Parent hLoad = loader.load();
    if(history == null){
      soldController = loader.getController();
      history = new Scene(hLoad);
    }
    if(root == null){
      loader = new FXMLLoader(getClass().getResource("gBay.fxml"));
      root = loader.load();
      controller = loader.getController();
      setUpNetworking();
      home = new Scene(root);
    }
    stage.setScene(home);
    stage.setTitle("gBay");
    stage.show();
  }

  public void historyPage(Stage stage) throws Exception {
    stage.setScene(history);
    stage.show();
  }

  public void sellPage(Stage stage) throws Exception {
    sellController.invalid.setVisible(false);
    stage.setScene(sell);
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
    readerThread.start();
  }

  protected void processRequest(Message input) {
    switch(input.code){
      case 0:             //initialize listings
        initListings(input.content);
        break;

      case 1:             //update price after a bid
        updatePrice(input.content);
        break;

      case 2:             //get guest number
        if(Login.clientID.equals("guest")){
          Login.clientID = Login.clientID + input.content;
        }
        controller.setUsername(Login.clientID);
        sendUserID();
        break;

      case 3:             //close an auction
        closeBidding(input.content);
        break;

      case 4:
        listItem(input.content);
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
    sendToServer("2 " + item + " " + output + " " + Login.clientID);
  }

  protected void listToServer(String item, String startPrice, String buyNowPrice, String time) {
    sendToServer("3 " + item + " " + startPrice + " " + buyNowPrice + " " + time);
  }

  public void initListings(String message){
    String[] messageArr = message.split(" ");
    int numItems = Integer.parseInt(messageArr[0]);
    String name;
    double price;
    double buyNow;
    int time;
    int i = 1;
    if(numItems != 0) {
      for (; i <= numItems * 4; i++) {
        name = messageArr[i];
        i++;
        price = Double.parseDouble(messageArr[i]);
        i++;
        buyNow = Double.parseDouble(messageArr[i]);
        i++;
        time = Integer.parseInt(messageArr[i]);
        Item add = new Item(name, price, buyNow, time, i / 4, null);
        Data.items.add(add);
      }
    }
    numItems = Integer.parseInt(messageArr[i]);
    if(numItems != 0){
      i++;
      String customer;
      int bound = i + numItems * 3;
      for(; i < bound; i++){
        name = messageArr[i];
        i++;
        price = Double.parseDouble(messageArr[i]);
        i++;
        customer = messageArr[i];
        Item add = new Item(name, price, 0, -1, (i-1)/4, customer);
        Data.sold.add(add);
      }
    }
    for(Item item : Data.items){
      try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Listing.fxml"));
        Node node = loader.load();
        Listing temp = loader.getController();
        temp.setup(item.name, item.price, item.buyNow, item.time, this, item.id, null);
        controller.add(node);
        listings.put(item.id, temp);
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    for(Item item : Data.sold){
      try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Listing.fxml"));
        Node node = loader.load();
        Listing temp = loader.getController();
        temp.setup(item.name, item.price, item.buyNow, item.time, this, item.id, item.customer);
        soldController.add(node);
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    Timer timer = new Timer();
    timer.schedule(new countdownTimer(), 0, 1000);
  }

  public void updatePrice(String message){
    String[] messageArr = message.split(" ");
    String name = messageArr[0];
    double price = Double.parseDouble(messageArr[1]);
    double buyNow = Double.parseDouble(messageArr[2]);
    String customer = messageArr[3];
    Item found;
    for(Item item : Data.items){
      if(item.name.equals(name)){
        item.price = price;
        item.buyNow = buyNow;
        item.customer = customer;
        found = item;
        Listing listing = listings.get(found.id);
        Platform.runLater(new Runnable() {
          @Override
          public void run() {
            listing.setPrice(price);
            listing.setPriceBuyNow(buyNow);
          }
        });
        break;
      }
    }
  }


  private void sendUserID(){
    sendToServer("0 " + Login.clientID);
  }

  private void closeBidding(String message) {
    String[] messageArr = message.split(" ");
    String name = messageArr[0];
    double price = Double.parseDouble(messageArr[1]);
    String customer = messageArr[2];
    for (Item item : Data.items) {
      if(item.name.equals(name)){
        item.price = price;
        item.customer = customer;
        Listing listing = listings.get(item.id);
        Platform.runLater(new Runnable() {
          @Override
          public void run() {
            listing.setPrice(price);
            listing.close(customer);
            soldController.add(listing);
            controller.remove(listing);
          }
        });
        Data.sold.add(item);
        if(Data.sold.size() > 7){
          Data.sold.remove(0);
        }
        Data.items.remove(item);
        break;
      }
    }
  }
  private void listItem(String message){
    String[] messageArr = message.split(" ");
    Item item = new Item(messageArr[0], Double.parseDouble(messageArr[1]), Double.parseDouble(messageArr[2]), Integer.parseInt(messageArr[3]), 0, null);
    Data.items.add(item);
    try {
      FXMLLoader loader = new FXMLLoader(getClass().getResource("Listing.fxml"));
      Node node = loader.load();
      Listing temp = loader.getController();
      temp.setup(item.name, item.price, item.buyNow, item.time, this, item.id, null);
      controller.add(node);
      listings.put(item.id, temp);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}

class countdownTimer extends TimerTask {

  @Override
  public void run() {
    for (Item item : Data.items) {
      Platform.runLater(new Runnable() {
        @Override
        public void run() {
          Listing listing = Client.thisClient.listings.get(item.id);
          listing.decrementSecond();
        }
      });
    }
  }
}