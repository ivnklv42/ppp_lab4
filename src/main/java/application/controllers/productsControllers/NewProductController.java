package application.controllers.productsControllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import onlinestore.InputCheck;
import onlinestore.Product;
import onlinestore.ProductType;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;

public class NewProductController extends GeneralProductsController {
    @FXML
    protected TextField titleField;

    @FXML
    protected TextField priceField;

    @FXML
    protected Label wrongData;

    @FXML
    protected GridPane gridPane;

    @FXML
    protected GridPane choiceGridPane;

    @FXML
    public void continuee(ActionEvent event) {
        newTitle = titleField.getText();
        newPrice = priceField.getText();
        changeMenu(gridPane, choiceGridPane, wrongData,
                InputCheck.productTitleCheck(newTitle) && InputCheck.productPriceCheck(newPrice));
    }

    @FXML
    public void onTypeButtonPressed(ActionEvent event) throws IOException {
        ProductType productType = processType((Button) (event.getSource()));
        find(newTitle);

        if (editIndex == -1) {
            try (BufferedWriter writer = Files.newBufferedWriter(
                    path,
                    StandardOpenOption.APPEND)) {
                writer.write((new Product(newTitle, Double.parseDouble(newPrice), productType)) + "\n");
            } catch (IOException e) {
                throw new IOException("Не удалось добавить новый продукт");
            }
        } else {
            file.set(editIndex, new Product(newTitle, Double.parseDouble(newPrice), productType) + "\n");
            writeInFile(event);
        }

        toPreviousMenu(event);
    }
}
