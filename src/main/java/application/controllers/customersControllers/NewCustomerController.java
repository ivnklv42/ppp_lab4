package application.controllers.customersControllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import onlinestore.InputCheck;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;

public class NewCustomerController extends GeneralCustomersController {
    @FXML
    protected TextField nameField;

    @FXML
    protected Label wrongData;

    @FXML
    public void continuee(ActionEvent event) throws IOException {
        String name = nameField.getText();
        if (!InputCheck.customerCheck(name)) {
            wrongData.setVisible(true);
            return;
        }

        find(name);
        if (editIndex == -1) {
            try (BufferedWriter writer = Files.newBufferedWriter(
                    path,
                    StandardOpenOption.APPEND)) {
                writer.write(name + "\n" + "Заказы:\n" + "\n");
            } catch (IOException e) {
                throw new IOException("Не удалось добавить нового покупателя");
            }
        } else {
            file.set(editIndex, name + "\n" + "Заказы:\n" + "\n");
            writeInFile(event);
        }

        toPreviousMenu(event);
    }
}
