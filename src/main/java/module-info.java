module com.example.inventorymanagementsystem {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires javafx.graphics;
    requires servlet.api;
    requires org.json;
    requires javafx.base;

    opens com.example.inventorymanagementsystem to javafx.fxml;
    exports com.example.inventorymanagementsystem;


}