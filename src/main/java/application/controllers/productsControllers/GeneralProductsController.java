package application.controllers.productsControllers;

import application.AppData;
import application.controllers.GeneralController;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import onlinestore.ProductType;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;


public class GeneralProductsController extends GeneralController {
    protected static final Path path = AppData.getProductsFile();

    protected String newTitle;
    protected String newPrice;

    protected static int editIndex = -1;

    public static final ArrayList<String> productTypes = new ArrayList<>(
            List.of(new String[]{"Электроника", "Одежда", "Обувь", "Книги", "Красота"}));

    protected ProductType processType(Button btn) {
        String text = btn.getText();
        switch (text) {
            case "Электроника" -> {
                return ProductType.Electronics;
            }
            case "Одежда" -> {
                return ProductType.Clothes;
            }
            case "Обувь" -> {
                return ProductType.Shoes;
            }
            case "Книги" -> {
                return ProductType.Books;
            }
            case "Красота" -> {
                return ProductType.Beauty;
            }
            default -> {
                return null;
            }
        }
    }

    @Override
    public void toPreviousMenu(ActionEvent event) throws IOException {
        changeScene(event, "/application/product/productsmenu.fxml");
    }

    public static String find(String title) throws IOException {
        editIndex = -1;
        file.clear();

        int index = -1;
        try (BufferedReader reader = Files.newBufferedReader(path)) {
            while (reader.ready()) {
                index++;
                String line = reader.readLine() + "\n";
                if (line.startsWith(title)) {
                    editIndex = index;
                }

                file.add(line);
            }
        } catch (IOException e) {
            throw new IOException("Не удалось считать файл");
        }

        return editIndex == -1 ? "" : file.get(editIndex);
    }

    @Override
    public void writeInFile(ActionEvent event) throws IOException {
        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            for (String product : file) {
                writer.write(product);
            }
        } catch (IOException e) {
            throw new IOException("Не удалось внести изменения в файл");
        }

        file.clear();
        toPreviousMenu(event);
    }
}
