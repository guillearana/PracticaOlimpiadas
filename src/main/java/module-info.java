/**
 * Módulo que define las dependencias, la apertura de paquetes y la exportación
 * de los componentes necesarios para la aplicación JavaFX.
 */
module es.guillearana.proyecto1 {

    // Requiere los módulos necesarios de JavaFX para crear la aplicación de escritorio
    requires javafx.controls;  // Necesario para controles gráficos
    requires java.desktop;  // Para funcionalidades de escritorio (como iconos)
    requires javafx.web;  // Para el soporte de web, si se necesita en la aplicación
    requires javafx.fxml;  // Necesario para cargar archivos FXML
    requires javafx.swing;  // Si se requiere integrar Swing con JavaFX
    requires javafx.media;  // Para capacidades multimedia
    requires javafx.graphics;  // Necesario para gráficos en la aplicación JavaFX
    requires javafx.base;  // Requiere funcionalidades básicas de JavaFX
    requires java.sql;  // Para la interacción con bases de datos SQL

    // Abre los paquetes para permitir el acceso desde otros módulos de JavaFX
    opens es.guillearana.proyecto1 to javafx.graphics, javafx.fxml;  // Paquete principal
    opens es.guillearana.proyecto1.controllers to javafx.graphics, javafx.fxml;  // Controladores de la aplicación
    opens es.guillearana.proyecto1.model to javafx.base;  // Modelos de datos
    opens es.guillearana.proyecto1.dao to javafx.base;  // Acceso a datos (DAO)

    // Exporta los paquetes para ser utilizados en otros módulos
    exports es.guillearana.proyecto1;  // Exporta el paquete principal
    exports es.guillearana.proyecto1.controllers;  // Exporta los controladores
    exports es.guillearana.proyecto1.model;  // Exporta los modelos de datos
    exports es.guillearana.proyecto1.dao;  // Exporta los DAOs para acceso a base de datos
}
