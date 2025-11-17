package application.controllers.customersControllers;

import application.OnlineStoreApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import onlinestore.InputCheck;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Comparator;


public class CustomersMenuController extends GeneralCustomersController {
    @FXML
    protected TextArea readCustomersTextArea;

    @FXML
    public void addCustomer(ActionEvent event) throws IOException {
        changeScene(event, "/application/customer/newcustomer.fxml");
    }

    @FXML
    public void addOrder(ActionEvent event) throws IOException {
        changeScene(event, "/application/customer/neworder.fxml");
    }

    @FXML
    public void deleteCustomer(ActionEvent event) throws IOException {
        changeScene(event, "/application/customer/deletecustomer.fxml");
    }

    @FXML
    public void showAllCustomers(ActionEvent event) throws IOException {
        ArrayList<String> lines = new ArrayList<String>();
        try (BufferedReader reader = Files.newBufferedReader(path)) {
            String name = "";
            String line;
            while ((line = reader.readLine()) != null) {
                if (InputCheck.customerCheck(line)) {
                    if (!name.isEmpty()) {
                        lines.add(name + "\n");
                    }

                    name = line;
                } else {
                    if (line.isEmpty()) {
                        continue;
                    }

                    name += "\n" + line;
                }
            }

            if (!name.isEmpty()) {
                lines.add(name + "\n");
            }
        } catch (IOException e) {
            throw new IOException("Не удалось считать файл");
        }

        lines.sort(Comparator.comparing(String::toString)
                .thenComparing(s -> {
                    return (s.length() - s.replace("\n", "").length())
                            / "\n".length();
                }));
        String content = "";
        for (String line : lines) {
            content += line + "\n";
        }

        FXMLLoader loader = new FXMLLoader(
                OnlineStoreApplication.class.getResource("/application/customer/readcustomersfile.fxml"));
        Pane root = loader.load();

        CustomersMenuController ctrl = loader.getController();
        ctrl.readCustomersTextArea.setText(content);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene old = stage.getScene();
        stage.setScene(new Scene(root, old.getWidth(), old.getHeight()));
        stage.show();
    }

    @FXML
    public void replaceCustomers(ActionEvent event) throws IOException {
        changeScene(event, "/application/customer/replacecustomers.fxml");
    }

    @FXML
    public void updateCustomers(ActionEvent event) throws IOException {
        changeScene(event, "/application/customer/updatecustomers.fxml");
    }
}

