module com.example.se201projektni_zadatakaleksandarrozkov6020 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.se201projektni_zadatakaleksandarrozkov6020 to javafx.fxml, javafx.base;
    exports com.example.se201projektni_zadatakaleksandarrozkov6020;
}