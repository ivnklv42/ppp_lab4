package application.controllers.productsControllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import onlinestore.InputCheck;
import onlinestore.Product;
import onlinestore.ProductType;

import java.io.IOException;

public class EditProductController extends GeneralProductsController {
    @FXML
    protected Label productNotExistLabel;

    @FXML
    protected GridPane gridPane;

    @FXML
    protected TextField existingTextField;

    @FXML
    protected VBox inputVBox;

    @FXML
    protected TextField titleTextField;

    @FXML
    protected TextField priceTextField;

    @FXML
    protected Label wrongData;

    @FXML
    protected GridPane choiceGridPane;

    @FXML
    public void continuee(ActionEvent event) throws IOException {
        find(existingTextField.getText());
        changeMenu(gridPane, inputVBox, productNotExistLabel, editIndex != -1);
    }

    @FXML
    public void continuee2(ActionEvent event) {
        newTitle = titleTextField.getText();
        newPrice = priceTextField.getText();
        changeMenu(inputVBox, choiceGridPane, wrongData,
                InputCheck.productTitleCheck(newTitle) && InputCheck.productPriceCheck(newPrice));
    }

    @FXML
    public void onTypeButtonPressed(ActionEvent event) throws IOException {
        ProductType productType = processType((Button) (event.getSource()));
        file.set(editIndex, new Product(newTitle, Double.parseDouble(newPrice), productType) + "\n");
        writeInFile(event);
    }
}
