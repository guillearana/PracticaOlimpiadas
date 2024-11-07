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

    opens es.guillearana.proyecto1 to javafx.graphics, javafx.fxml;
    opens es.guillearana.proyecto1.controllers to javafx.graphics, javafx.fxml;
    opens es.guillearana.proyecto1.model to javafx.base;
    opens es.guillearana.proyecto1.dao to javafx.base;

    exports css.idioma;
    exports es.guillearana.proyecto1;
    exports es.guillearana.proyecto1.controllers;
    exports es.guillearana.proyecto1.model;
    exports es.guillearana.proyecto1.dao;

}
