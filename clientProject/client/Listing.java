package client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Listing implements Initializable {

    public Client parent;
    public int id;

    @FXML
    private Text name;
    @FXML
    private Text price;
    @FXML
    private Text priceBuyNow;
    @FXML
    private Text time;
    @FXML
    private Button bidBut;
    @FXML
    private TextField bidText;

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
        parent.bidToServer(name.getText(), in);
    }

    public void updateListing(Item item){

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    public void setup(String name, double price, double priceBuyNow, int time, Client parent, int id){
        setName(name);
        setPrice(price);
        setPriceBuyNow(priceBuyNow);
        setTime(time);
        this.parent = parent;
        this.id = id;
    }

    public void setName(String name){
        this.name.setText(name);
    }

    public void setPrice(double price){
        this.price.setText("$" + price);
    }

    public void setPriceBuyNow(double priceBuyNow){
        this.priceBuyNow.setText("Buy Now for $" + priceBuyNow);
    }

    public void setTime(int time){
        String hours = Integer.toString(time / 3600);
        String minutes = Integer.toString((time / 60) % 60);
        String seconds = Integer.toString(time % 60);
        if(hours.length() == 1){
            hours = "0" + hours;
        }
        if(minutes.length() == 1){
            minutes = "0" + minutes;
        }
        if(seconds.length() == 1){
            seconds = "0" + seconds;
        }
        this.time.setText(hours + ":" + minutes + ":" + seconds + " remaining");
    }
}
