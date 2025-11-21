package application.controllers.customersControllers;

import application.OnlineStoreApplication;
import application.controllers.productsControllers.GeneralProductsController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import onlinestore.InputCheck;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;


public class CustomersMenuController extends GeneralCustomersController {
    @FXML
    protected TextArea readCustomersTextArea;

    @FXML
    protected Label wrongFileLabel;

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
        FileChooser fileChooser = new FileChooser();
        File newfile = fileChooser.showOpenDialog(((Node) event.getSource()).getScene().getWindow());
        String content = "";
        int step = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(newfile))) {
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

    @FXML
    public void updateCustomers(ActionEvent event) throws IOException {
        HashMap<String, String> fileContent = new HashMap<>();
        try (BufferedReader reader = Files.newBufferedReader(path)) {
            String name = "";
            String line;
            while ((line = reader.readLine()) != null) {
                if (InputCheck.customerCheck(line)) {
                    name = line;
                    fileContent.put(line, "");
                } else {
                    fileContent.put(name, fileContent.get(name) + line + "\n");
                }
            }
        } catch (IOException e) {
            throw new IOException("Не удалось считать файл");
        }

        FileChooser fileChooser = new FileChooser();
        File newfile = fileChooser.showOpenDialog(((Node) event.getSource()).getScene().getWindow());
        boolean flag = true;
        int step = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(newfile))) {
            String content = "";
            String name = "";
            String line;
            while ((line = reader.readLine()) != null) {
                if (InputCheck.customerCheck(line)) {
                    if (step != 0) {
                        throw new IOException("Файл не соответствует формату");
                    }

                    step++;
                    if (!name.isEmpty()) {
                        fileContent.put(name, content + "\n");
                    }

                    content = "";
                    name = line;
                } else if (line.equals("Заказы:")) {
                    if (step != 1) {
                        throw new IOException("Файл не соответствует формату");
                    }

                    step++;
                    content += line;
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
                fileContent.put(name, content + "\n");
            }
        } catch (Exception e) {
            flag = false;
        }

        if (flag) {
            for (Map.Entry<String, String> entry : fileContent.entrySet()) {
                file.add(entry.getKey() + "\n" + entry.getValue().trim());
            }

            writeInFile(event);
        } else {
            wrongFileLabel.setVisible(true);
        }
    }
}

