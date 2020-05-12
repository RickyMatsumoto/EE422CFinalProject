package client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class Sell implements Initializable {

    @FXML
    public TextField nameText;
    @FXML
    public TextField startPriceText;
    @FXML
    public TextField buyNowText;
    @FXML
    public TextField timeText;
    @FXML
    public Text invalid;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void quit(ActionEvent actionEvent) {
        System.exit(1);
    }

    public void list(ActionEvent actionEvent) {
        if (nameText.getText().trim().isEmpty() || startPriceText.getText().trim().isEmpty() || buyNowText.getText().trim().isEmpty() || timeText.getText().trim().isEmpty()) {
            invalid.setVisible(true);
            return;
        }
        String name = nameText.getText();
        double startPrice = Double.parseDouble(startPriceText.getText());
        double buyNow = Double.parseDouble(buyNowText.getText());
        int time = Integer.parseInt(timeText.getText());
        if((name == null) || (startPrice <= 0) || (buyNow < startPrice) || (time <= 0)){
            invalid.setVisible(true);
        } else {
            nameText.clear();
            startPriceText.clear();
            buyNowText.clear();
            timeText.clear();
            invalid.setVisible(false);
            Client.thisClient.listToServer(name, Double.toString(startPrice), Double.toString(buyNow), Integer.toString(time));
        }
    }

    public void back(ActionEvent actionEvent) throws Exception {
        Client.thisClient.homePage(Client.mainStage);
    }
}
