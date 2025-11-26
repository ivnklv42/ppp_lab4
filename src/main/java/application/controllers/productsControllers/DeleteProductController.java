package application.controllers.productsControllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;

public class DeleteProductController extends GeneralProductsController {
    @FXML
    protected Label productNotFoundLabel;

    @FXML
    protected TextField deleteTextField;

    @FXML
    public void continuee(ActionEvent event) throws IOException {
        find(deleteTextField.getText());
        if (editIndex != -1) {
            file.remove(editIndex);
            writeInFile(event);
        } else {
            productNotFoundLabel.setVisible(true);
        }
    }
}
