package application;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

public final class DataInitializer {

    private DataInitializer() { }

    public static void init() {
        copyIfNotExists(AppData.getCustomersFile(), "/data/customers.txt");
        copyIfNotExists(AppData.getProductsFile(), "/data/products.txt");
    }

    private static void copyIfNotExists(Path target, String resourcePath) {
        try {
            if (Files.exists(target)) {
                return;
            }

            try (InputStream in = DataInitializer.class.getResourceAsStream(resourcePath)) {
                if (in == null) {
                    Files.createFile(target);
                } else {
                    Files.copy(in, target);
                }
            }
        } catch (IOException e) {
            throw new IllegalStateException("Не удалось подготовить файл данных: " + target, e);
        }
    }
}

