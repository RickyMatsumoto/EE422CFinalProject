package example.client;

import example.server.Item;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class gBay extends Application implements Initializable {

    @FXML
    private ScrollPane scroll;
    @FXML
    private Button bidBut;
    @FXML
    private TextField bidText;

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("Client.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static void updateScroll(String message){
        String[] messageArr = message.split(" ");
        int numItems = Integer.parseInt(messageArr[0]);
        String name;
        double price;
        double buyNow;
        int time;
        for(int i = 1; i <= numItems; i++){
            int j = i;
            if(i != 1){
                j = (j * 4) + 1;
            }
            name = messageArr[j];
            j++;
            price = Double.parseDouble(messageArr[j]);
            j++;
            buyNow = Double.parseDouble(messageArr[j]);
            j++;
            time = Integer.parseInt(messageArr[j]);
            Item add = new Item(name, price, buyNow, time);
            Data.items.add(add);
        }
    }

    @FXML
    private void bid(ActionEvent event){
        bidBut.setVisible(false);
        bidText.setVisible(true);
    }

    @FXML
    private void takeBid(ActionEvent event){
        TextField source = (TextField) event.getSource();
        String in = source.getText();
        bidText.clear();
        bidText.setVisible(false);
        bidBut.setVisible(true);
        Client.bidToServer(in);
    }

    @FXML
    private void quit(ActionEvent event) {
        System.exit(1);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
