import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;

import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.UnknownHostException;

public class LoginController {

    @FXML
    private BorderPane rootLogin;
    @FXML
    private TextField clientIP;
    @FXML
    private TextField clientPortNumber;
    @FXML
    private Label errorMessage;

    private Client client;

    public void setClient(Client client) {
        this.client = client;
    }

    // check if the ip address is valid
    public boolean isValidIP(String ip) {
        try {
            return Inet4Address.getByName(ip).getHostAddress().equals(ip);
        }
        catch (UnknownHostException e) {
            return false;
        }
    }

    // check for a valid port number
    public boolean isValidPort(int port) {
        return ((port > 1023) && (port < 65536));
    }

    @FXML
    void handleCloseButton() {
        Platform.exit();
        System.exit(0);
    }

    @FXML
    void handleConnectButton() throws IOException {

        if (!isValidIP(clientIP.getText())) {
            errorMessage.setText("Invalid IP Address");
            clientIP.clear();
        }
        else if (!isValidPort(Integer.parseInt(clientPortNumber.getText()))) {
            errorMessage.setText("Invalid Port #");
            clientPortNumber.clear();
        }
        else {
            errorMessage.setText("");
            client = new Client(clientIP.getText(), Integer.parseInt(clientPortNumber.getText()));
            client.start();
            clientPortNumber.clear();
            clientIP.clear();
            nextScene();
        }

    }

    void nextScene() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/difficulty.fxml"));
        Parent difficultyInfo = loader.load();
        Scene diffScene = new Scene(difficultyInfo,700,700);
        Stage currStage = (Stage) rootLogin.getScene().getWindow();
        currStage.setScene(diffScene);
    }
}
