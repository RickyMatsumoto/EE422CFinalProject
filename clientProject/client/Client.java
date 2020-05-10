package client;

import java.io.DataOutputStream;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

class Client extends Application {

  private static String host = "127.0.0.1";
  private BufferedReader fromServer;
  private DataOutputStream toServer;
  private Scanner consoleInput = new Scanner(System.in);

  @Override
  public void start(Stage stage) throws Exception {
    Parent root = FXMLLoader.load(getClass().getResource("Client.fxml"));
    Scene scene = new Scene(root);
    stage.setScene(scene);
    stage.setTitle("gBay");
    stage.show();
  }


  public static void main(String[] args) {
    try {
      new Client().setUpNetworking();
      launch(args);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void setUpNetworking() throws Exception {
    @SuppressWarnings("resource")
    Socket socket = new Socket(host, 4242);
    System.out.println("Connecting to... " + socket);
    fromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    toServer = new DataOutputStream(socket.getOutputStream());

    Thread readerThread = new Thread(new Runnable() {
      @Override
      public void run() {
        String input;
        try {
          while ((input = fromServer.readLine()) != null) {
            System.out.println("From server: " + input);
            processRequest(input);
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
          String input = consoleInput.nextLine();
          String[] variables = input.split(",");
          Message request = new Message(variables[0], variables[1], Integer.valueOf(variables[2]));
          GsonBuilder builder = new GsonBuilder();
          Gson gson = builder.create();
          sendToServer(gson.toJson(request));
        }
      }
    });

    readerThread.start();
    writerThread.start();
  }

  protected void processRequest(String input) {
    return;
  }

  protected void sendToServer(String string) {
    System.out.println("Sending to server: " + string);
//    toServer.println(string);
//    toServer.flush();
  }

  protected static void bidToServer(String amount) {

  }

}