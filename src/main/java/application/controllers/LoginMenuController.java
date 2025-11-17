package application.controllers;

import application.OnlineStoreApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import onlinestore.RegisteredUsers;

import java.io.IOException;

public class LoginMenuController {
    @FXML
    protected TextField userNameField;

    @FXML
    protected PasswordField passwordField;

    @FXML
    protected Label wrongData;

    @FXML
    public void login(ActionEvent event) throws IOException {
        String username = userNameField.getText();
        String password = passwordField.getText();

        if (!RegisteredUsers.signUp(username, password)) {
            wrongData.setVisible(true);
        } else {
            Scene scene = ((Node) event.getSource()).getScene();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(OnlineStoreApplication.class.getResource("mainmenu.fxml"));
            scene = new Scene(fxmlLoader.load(), scene.getWidth(), scene.getHeight());
            stage.setScene(scene);
            stage.show();
        }
    }
}
