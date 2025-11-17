package application.controllers.productsControllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import onlinestore.InputCheck;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class ReplaceProductsController extends GeneralProductsController {
    @FXML
    protected TextField pathTextField;

    @FXML
    protected Label wrongFileLabel;

    @FXML
    public void continuee(ActionEvent event) throws IOException {
        String filepath = pathTextField.getText();
        boolean flag = true;
        try (BufferedReader reader = Files.newBufferedReader(Path.of(filepath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.matches(".+? - .+? - .+?")) {
                    throw new IOException("Товар не соответствует формату");
                }

                String[] elements = line.split(" - ");
                if (!(InputCheck.productTitleCheck(elements[0]) &&
                        InputCheck.productPriceCheck(elements[1]) &&
                        productTypes.contains(elements[2]))) {
                    throw new IOException("Товар не соответствует формату");
                }

                file.add(line + "\n");
            }
        } catch (Exception e) {
            file.clear();
            flag = false;
        }

        if (flag) {
            writeInFile(event);
        } else {
            wrongFileLabel.setVisible(true);
        }
    }
}
