package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class OnlineStoreApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        DataInitializer.init();

        FXMLLoader fxmlLoader = new FXMLLoader(OnlineStoreApplication.class.getResource("loginmenu.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 640, 480);

        Image icon = new Image(OnlineStoreApplication.class.getResourceAsStream("/data/icon.png"));
        stage.getIcons().add(icon);
        stage.setTitle("Управление онлайн магазином");
        stage.setScene(scene);

        stage.show();
    }
}
