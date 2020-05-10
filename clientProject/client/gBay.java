package client;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class gBay implements Initializable {

    @FXML
    private ScrollPane scroll;
    @FXML
    private VBox vbox;


    public void initScroll(String message){
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
        for(Item item : Data.items){
            try {
                Node listing = FXMLLoader.load(getClass().getResource("Listing.fxml"));

            }

        }
    }



    @FXML
    private void quit(ActionEvent event) {
        System.exit(1);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Node item1 = null;
        Node item2 = null;
        try {
            item1 = FXMLLoader.load(getClass().getResource("Listing.fxml"));
            item2 = FXMLLoader.load(getClass().getResource("Listing.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        vbox.getChildren().add(item1);
        vbox.getChildren().add(item2);
    }
}
