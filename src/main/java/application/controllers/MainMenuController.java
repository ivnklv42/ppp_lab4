package application.controllers;

import application.OnlineStoreApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainMenuController {
    @FXML
    public void changeScene(ActionEvent event, String path) throws IOException {
        Scene scene = ((Node) event.getSource()).getScene();

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(OnlineStoreApplication.class.getResource(path));
        scene = new Scene(fxmlLoader.load(), scene.getWidth(), scene.getHeight());
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void showProductsMenu(ActionEvent event) throws IOException {
        changeScene(event, "/application/product/productsmenu.fxml");
    }

    @FXML
    public void showCustomersMenu(ActionEvent event) throws IOException {
        changeScene(event, "/application/customer/customersmenu.fxml");
    }
}
