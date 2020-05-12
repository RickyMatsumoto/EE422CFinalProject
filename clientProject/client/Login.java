// Copy-paste this file at the top of every file you turn in.
/*
 * EE422C Final Project submission by
 * Replace <...> with your actual data.
 * Tatsushi Matsumoto
 * trm2796
 * 16295
 * Spring 2020
 */

package client;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import java.awt.event.ActionEvent;
import java.net.URL;
import java.util.ResourceBundle;

public class Login implements Initializable {


    public static String clientID;

    @FXML
    private TextField user;
    @FXML
    private Button guest;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    public void login(javafx.event.ActionEvent actionEvent) throws Exception {
        clientID = user.getText();
        Client.thisClient.homePage(Client.mainStage);
    }

    @FXML
    public void guest(javafx.event.ActionEvent actionEvent) throws Exception {
        clientID = "guest";
        Client.thisClient.homePage(Client.mainStage);
    }
}
