package application.controllers.customersControllers;

import application.controllers.productsControllers.GeneralProductsController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import onlinestore.InputCheck;

import java.io.IOException;
import java.util.Locale;

public class NewOrderController extends GeneralCustomersController {
    @FXML
    protected GridPane customerGridPane;

    @FXML
    protected TextField nameField;

    @FXML
    protected Label wrongData1;

    @FXML
    protected GridPane productGridPane;

    @FXML
    protected TextField titleField;

    @FXML
    protected TextField amountField;

    @FXML
    protected Label wrongData2;

    @FXML
    public void continuee1(ActionEvent event) throws IOException {
        String name = nameField.getText();
        find(name);
        changeMenu(customerGridPane, productGridPane, wrongData1, editIndex != -1);
    }

    @FXML
    public void continuee2(ActionEvent event) throws IOException {
        String title = titleField.getText();
        String amount = amountField.getText();
        String product = GeneralProductsController.find(title);
        String[] elements = product.split(" - ");
        if (!(!product.isEmpty() && InputCheck.amountCheck(amount))) {
            wrongData2.setVisible(true);
        } else {
            file.set(editIndex, file.get(editIndex) + "\n" + product.trim() + " - " + amount + " - " +
                    String.format(Locale.US, "%.2f", Double.parseDouble(elements[1]) * Integer.parseInt(amount)));
            writeInFile(event);
        }
    }
}
