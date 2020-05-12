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

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
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

    public void add(Listing in){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                vbox.getChildren().add(in.node);
                if(vbox.getChildren().size() > 7) {
                    vbox.getChildren().remove(0);
                }
            }
        });
    }

    public void add(Node in){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                vbox.getChildren().add(in);
            }
        });
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
