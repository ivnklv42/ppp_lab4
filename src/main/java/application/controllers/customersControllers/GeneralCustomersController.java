package application.controllers.customersControllers;

import application.AppData;
import application.controllers.GeneralController;
import javafx.event.ActionEvent;
import onlinestore.InputCheck;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class GeneralCustomersController extends GeneralController {
    protected final Path path = AppData.getCustomersFile();
    protected final ArrayList<String> file = new ArrayList<>();

    protected static int editIndex = -1;

    protected void find(String title) throws IOException {
        editIndex = -1;
        file.clear();

        int index = -1;
        String content = "";
        try (BufferedReader reader = Files.newBufferedReader(path)) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (InputCheck.customerCheck(line)) {
                    index++;
                    if (!content.isEmpty()) {
                        file.add(content);
                    }

                    if (line.startsWith(title)) {
                        editIndex = index;
                    }

                    content = line;
                } else {
                    if (line.isEmpty()) {
                        continue;
                    }

                    content += "\n" + line;
                }
            }

            if (!content.isEmpty()) {
                file.add(content);
            }
        } catch (IOException e) {
            throw new IOException("Не удалось считать файл");
        }
    }

    @Override
    public void toPreviousMenu(ActionEvent event) throws IOException {
        changeScene(event, "/application/customer/customersmenu.fxml");
    }

    @Override
    public void writeInFile(ActionEvent event) throws IOException {
        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            for (String customer : file) {
                writer.write(customer + "\n\n");
            }
        } catch (IOException e) {
            throw new IOException("Не удалось внести изменения в файл");
        }

        file.clear();
        toPreviousMenu(event);
    }
}
