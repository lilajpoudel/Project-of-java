module com.example.inventorymanagementsystem {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires mysql.connector.j;


    opens com.example.inventorymanagementsystem to javafx.fxml;
    exports com.example.inventorymanagementsystem;
}