module com.example.designpatterns {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.sql.rowset;
    opens com.example.designpatterns to javafx.fxml;
    exports com.example.designpatterns;
}