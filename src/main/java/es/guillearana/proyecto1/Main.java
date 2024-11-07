package es.guillearana.proyecto1;

import java.util.Locale;
import java.util.ResourceBundle;

import es.guillearana.proyecto1.conexion.Propiedades;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.fxml.FXMLLoader;

/**
 * Clase principal de la aplicación JavaFX que carga la interfaz de usuario y configura
 * la escena de la ventana principal con un fondo y estilos definidos.
 */
public class Main extends Application {

    /**
     * Método que se ejecuta al iniciar la aplicación, configurando el idioma,
     * cargando la interfaz y mostrando la ventana principal.
     *
     * @param primaryStage La ventana principal de la aplicación.
     */
    @Override
    public void start(Stage primaryStage) {
        try {

            // Obtener configuración de idioma y región desde un archivo de propiedades
            String idioma = Propiedades.getValor("idioma");
            String region = Propiedades.getValor("region");

            // Establecer la configuración regional por defecto
            Locale.setDefault(new Locale(idioma, region));

            // Cargar los recursos de idioma
            ResourceBundle bundle = ResourceBundle.getBundle("idioma/messages");

            // Cargar la interfaz de usuario desde un archivo FXML
            GridPane root = (GridPane)FXMLLoader.load(getClass().getResource("/es/guillearana/proyecto1/Listado.fxml"), bundle);

            // Crear un StackPane como contenedor principal
            StackPane stackPane = new StackPane();

            // Crear una imagen de fondo y un ImageView
            Image fondo = new Image(getClass().getResourceAsStream("/es/guillearana/proyecto1/imagenes/fondo.jpg"));
            ImageView imageView = new ImageView(fondo);
            // Ajustar la imagen al tamaño del StackPane
            imageView.fitWidthProperty().bind(stackPane.widthProperty());
            imageView.fitHeightProperty().bind(stackPane.heightProperty());
            imageView.setPreserveRatio(true);

            // Añadir la imagen como fondo al StackPane
            stackPane.getChildren().add(imageView);  // Añadir imagen como primer hijo (fondo)

            // Añadir el GridPane (contenido) encima del fondo
            stackPane.getChildren().add(root);  // Añadir GridPane como segundo hijo

            // Crear la escena y agregar hojas de estilo
            Scene scene = new Scene(stackPane);
            scene.getStylesheets().add(getClass().getResource("/css/estilos.css").toExternalForm());
            primaryStage.setMaxHeight(755);
            primaryStage.setMaxWidth(1150);
            primaryStage.setMinHeight(755);
            primaryStage.setMinWidth(1150);

            // Establecer el título de la ventana principal usando la configuración de idioma
            primaryStage.setTitle(bundle.getString("titulo"));

            // Establecer la escena en la ventana principal y mostrarla
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch(Exception e) {
            // Manejar excepciones imprimiendo la traza de errores
            e.printStackTrace();
        }
    }

    /**
     * Método principal que inicia la aplicación JavaFX.
     *
     * @param args Los argumentos de línea de comandos pasados a la aplicación.
     */
    public static void main(String[] args) {
        launch(args);
    }
}
