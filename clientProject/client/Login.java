package client;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import java.awt.event.ActionEvent;
import java.net.URL;
import java.util.ResourceBundle;

public class Login implements Initializable {


    public String clientID;

    @FXML
    private TextField user;
    @FXML
    private Button guest;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void login(javafx.event.ActionEvent actionEvent) {
        clientID = user.getText();
    }

    public void guest(javafx.event.ActionEvent actionEvent) {
    }
}
