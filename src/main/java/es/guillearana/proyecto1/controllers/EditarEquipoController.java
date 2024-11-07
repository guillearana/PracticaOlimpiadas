package es.guillearana.proyecto1.controllers;

import es.guillearana.proyecto1.dao.EquiposDao;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import es.guillearana.proyecto1.model.Equipo;

/**
 * Controlador para editar un equipo existente en la aplicación.
 * Esta clase maneja la lógica de la ventana emergente que permite modificar los detalles de un equipo.
 */
public class EditarEquipoController {
    private Equipo equipo;

    // Atributo de la ventana emergente
    private Stage ventanaEmergente;

    @FXML
    private TextField tfNombre;

    @FXML
    private TextField tfIniciales;

    @FXML
    private Button btnAceptar;

    @FXML
    private Button btnCancelar;

    /**
     * Método que abre la ventana emergente para editar un equipo.
     * Se inicializan los campos con los valores actuales del equipo.
     * @param equipo El equipo que se va a editar.
     */
    public void editarEquipo(Equipo equipo) {
        this.equipo = equipo;
        // Creamos una nueva instancia de la clase Stage para la ventana emergente
        ventanaEmergente = new Stage();

        // Le ponemos titulo a la ventana
        ventanaEmergente.setTitle("MODIFICAR EQUIPO");

        // Creamos un contenedor VBox como raíz de la ventana emergente
        VBox contenedorRaiz = new VBox();

        // Creamos contenedores HBox para cada campo de entrada (Nombre, Apellidos, Edad)
        HBox contenedorNombre = new HBox();
        HBox contenedorIniciales = new HBox();

        // Establecemos un espaciado entre elementos en los contenedores HBox
        contenedorNombre.setSpacing(10);
        contenedorIniciales.setSpacing(10);

        // le damos valor a los TextFields
        tfNombre = new TextField();
        tfNombre.setText(equipo.getNombre());

        tfIniciales = new TextField();
        tfIniciales.setText(equipo.getIniciales());

        // Si el campo de Nombre no existe, creamos uno nuevo (tfNombre)
        contenedorNombre.getChildren().addAll(new javafx.scene.control.Label("Nombre"), tfNombre);
        contenedorIniciales.getChildren().addAll(new javafx.scene.control.Label("Iniciales"), tfIniciales);

        // Creamos un contenedor HBox para los botones (Guardar y Cerrar)
        HBox contenedorBotones = new HBox();
        contenedorBotones.setSpacing(10);

        // Creamos un botón "Guardar"
        Button guardarBtn = new Button("Guardar");
        guardarBtn.setOnAction(e -> modificar(e));

        // Creamos un botón "Cerrar" y configuramos su evento para cerrar la ventana emergente
        Button cerrarBtn = new Button("Cerrar");
        cerrarBtn.setOnAction(e -> ventanaEmergente.close());

        // Agregamos los botones al contenedor de botones
        contenedorBotones.getChildren().addAll(guardarBtn, cerrarBtn);

        // Agregamos todos los contenedores al contenedor raíz
        contenedorRaiz.getChildren().addAll(contenedorNombre, contenedorIniciales, contenedorBotones);

        // Configuramos propiedades del contenedor raíz
        contenedorRaiz.setPadding(new Insets(20));
        contenedorRaiz.setSpacing(20);

        // Creamos una escena con el contenedor raíz
        Scene escena = new Scene(contenedorRaiz);
        // Establecemos la escena en la ventana emergente
        ventanaEmergente.setScene(escena);

        // Desactivamos la posibilidad de redimensionar la ventana emergente
        ventanaEmergente.setResizable(false);
        // Mostramos la ventana emergente
        ventanaEmergente.show();
    }

    /**
     * Método que maneja la acción de guardar los cambios del equipo editado.
     * Realiza la validación de los campos y actualiza el equipo en la base de datos si no hay errores.
     * @param event El evento generado por el botón "Guardar".
     */
    void modificar(ActionEvent event) {
        // Antes de modificar, validamos que los campos de entrada no contengan errores
        String errores = validarCampos();

        if (errores.isEmpty()) {
            try {
                EquiposDao equiposDao = new EquiposDao();
                // Le ponemos los datos nuevos al equipo
                equipo.setNombre(this.tfNombre.getText().toString());
                equipo.setIniciales(this.tfIniciales.getText().toString());

                // Actualizamos el equipo en la base de datos
                equiposDao.editarDeporte(equipo);

                // Cerramos la ventana emergente
                ventanaEmergente.close();
                // Mostramos un mensaje de éxito
                alertaInformacion("Se ha modificado el equipo seleccionado\nActualiza la tabla para ver los cambios");
            } catch (Exception e) {
                // Manejamos cualquier excepción que pueda ocurrir, aunque no se realiza ninguna acción específica en caso de error
                alertaError("Ocurrió un error al intentar modificar el equipo. Por favor, intente nuevamente.");
            }
        } else {
            // Mostramos una alerta de error con los mensajes de error
            alertaError(errores);
        }
    }

    /**
     * Método que valida los campos de entrada (Nombre e Iniciales).
     * @return Un String con los errores encontrados en los campos.
     */
    private String validarCampos() {
        String errores = "";

        // Validación de campo Nombre
        if(tfNombre.getText().isEmpty()) {
            errores += "Tienes que rellenar el campo Nombre\n";
        }

        // Validación de campo Iniciales
        if(tfIniciales.getText().isEmpty()) {
            errores += "Tienes que rellenar el campo Iniciales\n";
        } else if(tfIniciales.getText().length() > 3) {
            errores += "Las iniciales solo pueden tener un máximo de 3 carácteres\n";
        }

        return errores;
    }

    /**
     * Método que maneja la acción del botón Cancelar para cerrar la ventana emergente.
     * @param event El evento generado por el botón "Cancelar".
     */
    @FXML
    void accionCancelar(ActionEvent event) {
        // Cierra la ventana actual al hacer clic en el botón Cancelar
        Stage stage = (Stage) btnCancelar.getScene().getWindow();
        stage.close();
    }

    /**
     * Método que muestra una ventana emergente de alerta con el mensaje de error.
     * @param mensaje El mensaje que se mostrará en la ventana de error.
     */
    private void alertaError(String mensaje) {
        // Alerta de error con boton
        Alert ventanaEmergente = new Alert(AlertType.ERROR);
        ventanaEmergente.setTitle("info");
        ventanaEmergente.setContentText(mensaje);
        Button ocultarBtn = new Button("Aceptar");
        ocultarBtn.setOnAction(e -> {
            ventanaEmergente.hide();
        });
        ventanaEmergente.show();
    }

    /**
     * Método que muestra una ventana emergente de información con el mensaje proporcionado.
     * @param mensaje El mensaje que se mostrará en la ventana de información.
     */
    private void alertaInformacion(String mensaje) {
        // Alerta de informacion con boton
        Alert ventanaEmergente = new Alert(AlertType.INFORMATION);
        ventanaEmergente.setTitle("info");
        ventanaEmergente.setContentText(mensaje);
        Button ocultarBtn = new Button("Aceptar");
        ocultarBtn.setOnAction(e -> {
            ventanaEmergente.hide();
        });
        ventanaEmergente.show();
    }
}
