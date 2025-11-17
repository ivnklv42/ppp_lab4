package application.controllers.customersControllers;

import application.controllers.productsControllers.GeneralProductsController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import onlinestore.InputCheck;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Locale;

public class ReplaceCustomersController extends GeneralCustomersController {
    @FXML
    protected TextField pathTextField;

    @FXML
    protected Label wrongFileLabel;

    @FXML
    public void continuee(ActionEvent event) throws IOException {
        String filepath = pathTextField.getText();
        String content = "";
        int step = 0;
        try (BufferedReader reader = Files.newBufferedReader(Path.of(filepath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (InputCheck.customerCheck(line)) {
                    if (step != 0) {
                        throw new IOException("Файл не соответствует формату");
                    }

                    step++;

                    if (!content.isEmpty()) {
                        file.add(content);
                    }

                    content = line;
                } else if (line.equals("Заказы:")) {
                    if (step != 1) {
                        throw new IOException("Файл не соответствует формату");
                    }

                    step++;
                    content += "\n" + line;
                } else if (line.matches(".+? - .+? - .+? - .+? - .+?")) {
                    if (step != 2) {
                        throw new IOException("Заказы не соответствуют формату");
                    }

                    String[] elements = line.split(" - ");
                    if (!(InputCheck.productTitleCheck(elements[0]) &&
                            InputCheck.productPriceCheck(elements[1]) &&
                            GeneralProductsController.productTypes.contains(elements[2]) &&
                            InputCheck.amountCheck(elements[3]) &&
                            InputCheck.orderPriceCheck(elements[4]) &&
                            String.format(Locale.US, "%.2f",
                            Double.parseDouble(elements[3]) * Double.parseDouble(elements[1]))
                             .equals(elements[4]))) {
                        throw new IOException("Заказы не соответствуют формату");
                    }

                    content += "\n" + line;
                } else if (line.isEmpty()) {
                    if (step != 2 && step != 1) {
                        throw new IOException("Заказы не соответствуют формату");
                    }

                    step = 0;
                } else {
                    throw new IOException("Заказы не соответствуют формату");
                }
            }

            if (!content.isEmpty()) {
                file.add(content);
            }
        } catch (Exception e) {
            file.clear();
        }

        if (!file.isEmpty()) {
            writeInFile(event);
        } else {
            wrongFileLabel.setVisible(true);
        }
    }
}
