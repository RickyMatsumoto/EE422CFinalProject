package client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class Sold implements Initializable {
    public VBox vbox;
    public Text username;

    @FXML
    public void quit(ActionEvent actionEvent) {
        System.exit(1);
    }
    @FXML
    public void goHome(ActionEvent actionEvent) throws Exception {
        Client.thisClient.homePage(Client.mainStage);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
