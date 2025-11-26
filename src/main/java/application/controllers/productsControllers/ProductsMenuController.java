package application.controllers.productsControllers;

import application.OnlineStoreApplication;
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

public class ProductsMenuController extends GeneralProductsController {
    @FXML
    protected TextArea readProductsTextArea;

    @FXML
    protected Label wrongFileLabel;

    @FXML
    public void addProduct(ActionEvent event) throws IOException {
        changeScene(event, "/application/product/newproduct.fxml");
    }

    @FXML
    public void deleteProduct(ActionEvent event) throws IOException {
        changeScene(event, "/application/product/deleteproduct.fxml");
    }

    @FXML
    public void showAllProducts(ActionEvent event) throws IOException {
        ArrayList<String> lines = (ArrayList<String>) Files.readAllLines(path);
        lines.sort(Comparator.comparing(String::toString)
                .thenComparing(s -> {
                    return Double.parseDouble(s.split(" - ")[1].replace(",", "."));
                }));
        String content = "";
        for (String line : lines) {
            content += line + "\n";
        }

        FXMLLoader loader = new FXMLLoader(
                OnlineStoreApplication.class.getResource("/application/product/readproductsfile.fxml"));
        Pane root = loader.load();

        ProductsMenuController ctrl = loader.getController();
        ctrl.readProductsTextArea.setText(content);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene old = stage.getScene();
        stage.setScene(new Scene(root, old.getWidth(), old.getHeight()));
        stage.show();
        changeScene(event, "/application/product/readproductsfile.fxml");
    }

    @FXML
    public void editProduct(ActionEvent event) throws IOException {
        changeScene(event, "/application/product/editproduct.fxml");
    }

    @FXML
    public void replaceProducts(ActionEvent event) throws IOException {
        FileChooser fileChooser = new FileChooser();
        File newfile = fileChooser.showOpenDialog(((Node) event.getSource()).getScene().getWindow());
        boolean flag = true;
        try (BufferedReader reader = new BufferedReader(new FileReader(newfile))) {
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

    @FXML
    public void updateProducts(ActionEvent event) throws IOException {
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

        FileChooser fileChooser = new FileChooser();
        File newfile = fileChooser.showOpenDialog(((Node) event.getSource()).getScene().getWindow());
        ArrayList<String> productTypes = new ArrayList<>(
                List.of(new String[]{"Электроника", "Одежда", "Обувь", "Книги", "Красота"}));
        boolean flag = true;
        try (BufferedReader reader = new BufferedReader(new FileReader(newfile))) {
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
