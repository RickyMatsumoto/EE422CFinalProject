package client;

import javafx.application.Application;
import javafx.application.Platform;
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
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class gBay implements Initializable {

    @FXML
    private ScrollPane scroll;
    @FXML
    public VBox vbox;
    @FXML
    public Text username;

    @FXML
    private void quit(ActionEvent event) {
        System.exit(1);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    public void add(Node in){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                vbox.getChildren().add(in);
            }
        });
    }

    public void setUsername(String name){
        username.setText(name);
    }

}
