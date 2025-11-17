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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UpdateProductsController extends GeneralProductsController {
    @FXML
    protected TextField pathTextField;

    @FXML
    protected Label wrongFileLabel;

    @FXML
    public void continuee(ActionEvent event) throws IOException {
        HashMap<String, String> fileContent = new HashMap<>();
        try (BufferedReader reader = Files.newBufferedReader(path)) {
            String line;
            while ((line = reader.readLine()) != null) {
                fileContent.put(line.substring(0, line.indexOf(" - ") + 3),
                        line.substring(line.indexOf(" - ") + 3) + "\n");
            }
        } catch (IOException e) {
            throw new IOException("Не удалось считать файл");
        }

        String filepath = pathTextField.getText();
        ArrayList<String> productTypes = new ArrayList<>(
                List.of(new String[]{"Электроника", "Одежда", "Обувь", "Книги", "Красота"}));
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

                fileContent.put(elements[0] + " - ", elements[1] + " - " + elements[2] + "\n");
            }
        } catch (Exception e) {
            flag = false;
        }

        if (flag) {
            for (Map.Entry<String, String> entry : fileContent.entrySet()) {
                file.add(entry.getKey() + entry.getValue());
            }

            writeInFile(event);
        } else {
            wrongFileLabel.setVisible(true);
        }
    }
}
