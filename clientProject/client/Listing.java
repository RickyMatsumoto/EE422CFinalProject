package client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class Listing implements Initializable {

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
        Client.bidToServer(in);
    }

    public void updateListing(Item item){

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
}
