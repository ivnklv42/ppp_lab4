package application;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public final class AppData {
    private static final String APP_NAME = "OnlineStore";

    private AppData() { }

    public static Path getAppDataDir() {
        String appData = System.getenv("APPDATA");

        Path dir = Paths.get(appData, APP_NAME);
        try {
            Files.createDirectories(dir);
        } catch (IOException e) {
            throw new IllegalStateException("Не удалось создать каталог данных: " + dir, e);
        }
        return dir;
    }

    public static Path getCustomersFile() {
        return getAppDataDir().resolve("customers.txt");
    }

    public static Path getProductsFile() {
        return getAppDataDir().resolve("products.txt");
    }
}

