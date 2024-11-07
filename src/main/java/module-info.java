module es.guillearana.proyecto1 {
    requires javafx.controls;
    requires java.desktop;
    requires javafx.web;
    requires javafx.fxml;
    requires javafx.swing;
    requires javafx.media;
    requires javafx.graphics;
    requires javafx.base;
    requires java.sql;
    requires org.kordamp.ikonli.core;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.ikonli.fontawesome5;


    opens application to javafx.graphics, javafx.fxml;
    opens controllers to javafx.graphics, javafx.fxml;
    opens model to javafx.base;
    opens dao to javafx.base;
}