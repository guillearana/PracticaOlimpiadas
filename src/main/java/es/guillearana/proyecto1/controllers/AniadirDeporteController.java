package es.guillearana.proyecto1.controllers;

import es.guillearana.proyecto1.dao.DeportesDao;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import es.guillearana.proyecto1.model.Deporte;

/**
 * Controlador para la ventana de añadir un nuevo deporte.
 *
 * Este controlador gestiona la interfaz de usuario que permite añadir un deporte al sistema.
 * Proporciona la validación de datos, la interacción con la base de datos y la visualización
 * de alertas informativas o de error.
 */
public class AniadirDeporteController {

    /** Botón para cancelar la acción y cerrar la ventana. */
    @FXML
    private Button btnCancelar;

    /** Botón para guardar el deporte ingresado. */
    @FXML
    private Button btnGuardar;

    /** Campo de texto para ingresar el nombre del deporte. */
    @FXML
    private TextField tfNombre;

    /**
     * Cancela la operación y cierra la ventana cuando se hace clic en el botón "Cancelar".
     *
     * @param event el evento de acción del botón "Cancelar"
     */
    @FXML
    void accionCancelar(ActionEvent event) {
        // Cierra la ventana actual al hacer clic en el botón Cancelar
        Stage stage = (Stage) btnCancelar.getScene().getWindow();
        stage.close();
    }

    /**
     * Guarda el deporte ingresado. Valida los datos y, si son correctos,
     * añade el deporte a la base de datos.
     *
     * @param event el evento de acción del botón "Guardar"
     */
    @FXML
    private void accionGuardar(ActionEvent event) {

        String errores = validar();

        if(errores.isEmpty()) {
            String nombre = tfNombre.getText();

            Deporte deporte = new Deporte(nombre);
            // Añadir deportistas a la base de datos
            DeportesDao dao = new DeportesDao();
            dao.aniadirDeporte(deporte);

            alertaInformacion("Se ha añadido correctamente el deporte\nActualiza la tabla para ver los cambios");

            vaciarCampos();
        } else {
            alertaError(errores);
        }
    }

    /**
     * Valida los campos del formulario. En este caso, se asegura de que el campo
     * "Nombre" no esté vacío.
     *
     * @return un String con los errores de validación. Si no hay errores, devuelve una cadena vacía
     */
    private String validar() {
        String errores = "";

        // Validar que el campo Nombre no esté vacío
        if(tfNombre.getText().isEmpty()) {
            errores += "Tienes que rellenar el campo Nombre\n";
        }

        return errores;
    }

    /**
     * Limpia el campo de texto donde se ingresa el nombre del deporte.
     */
    private void vaciarCampos() {
        tfNombre.setText("");
    }

    /**
     * Muestra una alerta de tipo error con un mensaje proporcionado.
     *
     * @param mensaje el mensaje a mostrar en la alerta
     */
    private void alertaError(String mensaje) {
        // Alerta de error con botón
        Alert ventanaEmergente = new Alert(AlertType.ERROR);
        ventanaEmergente.setTitle("Error");
        ventanaEmergente.setContentText(mensaje);
        Button ocultarBtn = new Button("Aceptar");
        ocultarBtn.setOnAction(e -> {
            ventanaEmergente.hide();
        });
        ventanaEmergente.show();
    }

    /**
     * Muestra una alerta de tipo información con un mensaje proporcionado.
     *
     * @param mensaje el mensaje a mostrar en la alerta
     */
    private void alertaInformacion(String mensaje) {
        // Alerta de información con botón
        Alert ventanaEmergente = new Alert(AlertType.INFORMATION);
        ventanaEmergente.setTitle("Información");
        ventanaEmergente.setContentText(mensaje);
        Button ocultarBtn = new Button("Aceptar");
        ocultarBtn.setOnAction(e -> {
            ventanaEmergente.hide();
        });
        ventanaEmergente.show();
    }
}
