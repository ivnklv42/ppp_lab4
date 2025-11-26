module com.example.ppp_lab4 {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.base;

    opens application to javafx.fxml;

    exports application;
    exports onlinestore;
    exports application.controllers.customersControllers;
    opens application.controllers.customersControllers to javafx.fxml;
    exports application.controllers.productsControllers;
    opens application.controllers.productsControllers to javafx.fxml;
    exports application.controllers;
    opens application.controllers to javafx.fxml;
}