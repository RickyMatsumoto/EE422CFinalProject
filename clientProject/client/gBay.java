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

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.ObservableList;
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
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
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

    public void remove(Listing rem) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                vbox.getChildren().remove(rem.node);
            }
        });
    }

    public void setUsername(String name){
        username.setText(name);
    }

    public void historyPage(ActionEvent actionEvent) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {
                    Client.thisClient.historyPage(Client.mainStage);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void sell(ActionEvent actionEvent) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {
                    Client.thisClient.sellPage(Client.mainStage);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
