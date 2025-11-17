package application.controllers.productsControllers;

import application.OnlineStoreApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Comparator;

public class ProductsMenuController extends GeneralProductsController {
    @FXML
    protected TextArea readProductsTextArea;

    @FXML
    public void addProduct(ActionEvent event) throws IOException {
        changeScene(event, "/application/product/newproduct.fxml");
    }

    @FXML
    public void deleteProduct(ActionEvent event) throws IOException {
        changeScene(event, "/application/product/deleteproduct.fxml");
    }

    @FXML
    public void showAllProducts(ActionEvent event) throws IOException {
        ArrayList<String> lines = (ArrayList<String>) Files.readAllLines(path);
        lines.sort(Comparator.comparing(String::toString)
                .thenComparing(s -> {
                    return Double.parseDouble(s.split(" - ")[1].replace(",", "."));
                }));
        String content = "";
        for (String line : lines) {
            content += line + "\n";
        }

        FXMLLoader loader = new FXMLLoader(
                OnlineStoreApplication.class.getResource("/application/product/readproductsfile.fxml"));
        Pane root = loader.load();

        ProductsMenuController ctrl = loader.getController();
        ctrl.readProductsTextArea.setText(content);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene old = stage.getScene();
        stage.setScene(new Scene(root, old.getWidth(), old.getHeight()));
        stage.show();
        changeScene(event, "/application/product/readproductsfile.fxml");
    }

    @FXML
    public void editProduct(ActionEvent event) throws IOException {
        changeScene(event, "/application/product/editproduct.fxml");
    }

    @FXML
    public void replaceProducts(ActionEvent event) throws IOException {
        changeScene(event, "/application/product/replaceproducts.fxml");
    }

    @FXML
    public void updateProducts(ActionEvent event) throws IOException {
        changeScene(event, "/application/product/updateproducts.fxml");
    }
}
