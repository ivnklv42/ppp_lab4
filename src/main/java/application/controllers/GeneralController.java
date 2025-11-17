package application.controllers;

import application.OnlineStoreApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public abstract class GeneralController {
    protected static final ArrayList<String> file = new ArrayList<>();

    @FXML
    public abstract void toPreviousMenu(ActionEvent event) throws IOException;

    public abstract void writeInFile(ActionEvent event) throws IOException;
    
    @FXML
    public void changeMenu(Pane pane1, Pane pane2, Label label, boolean flag) {
        if (!flag) {
            label.setVisible(true);
        } else {
            pane1.setDisable(true);
            pane1.setVisible(false);

            pane2.setDisable(false);
            pane2.setVisible(true);
        }
    }

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
    public void toMainMenu(ActionEvent event) throws IOException {
        changeScene(event, "/application/mainmenu.fxml");
    }
}
