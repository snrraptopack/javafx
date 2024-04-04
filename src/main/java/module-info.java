module com.example.hello {
    requires javafx.controls;
    requires javafx.fxml;
    requires fontawesomefx;
    requires java.sql;
    requires sqlite.jdbc;


    opens com.example.hello to javafx.fxml;
    exports com.example.hello;
}