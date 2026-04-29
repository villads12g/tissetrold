module com.example.ProjectGroupApps {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.xerial.sqlitejdbc;

    opens com.example.ProjectGroupApps to javafx.fxml;
    exports com.example.ProjectGroupApps;
}