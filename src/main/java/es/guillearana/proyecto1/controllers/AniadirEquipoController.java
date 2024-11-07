package es.guillearana.proyecto1.controllers;

import es.guillearana.proyecto1.dao.EquiposDao;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import es.guillearana.proyecto1.model.Equipo;

/**
 * Controlador para la ventana de añadir un nuevo equipo.
 *
 * Este controlador gestiona la interfaz de usuario para añadir un nuevo equipo al sistema,
 * valida los datos introducidos, los guarda en la base de datos y muestra alertas informativas o de error.
 */
public class AniadirEquipoController {

    /** Botón para cancelar la acción y cerrar la ventana. */
    @FXML
    private Button btnCancelar;

    /** Botón para guardar el equipo ingresado. */
    @FXML
    private Button btnGuardar;

    /** Campo de texto para ingresar el nombre del equipo. */
    @FXML
    private TextField tfNombre;

    /** Campo de texto para ingresar las iniciales del equipo. */
    @FXML
    private TextField tfIniciales;

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
     * Guarda el equipo ingresado. Valida los datos y, si son correctos,
     * añade el equipo a la base de datos.
     *
     * @param event el evento de acción del botón "Guardar"
     */
    @FXML
    private void accionGuardar(ActionEvent event) {

        String errores = validar();

        if(errores.isEmpty()) {
            String nombre = tfNombre.getText();
            String iniciales = tfIniciales.getText();

            Equipo equipo = new Equipo(nombre, iniciales);
            // Añadir equipo a la base de datos
            EquiposDao dao = new EquiposDao();
            dao.aniadirEquipo(equipo);

            alertaInformacion("Se ha añadido correctamente el equipo\nActualiza la tabla para ver los cambios");

            vaciarCampos();
        } else {
            alertaError(errores);
        }
    }

    /**
     * Valida los campos del formulario. Verifica que los campos "Nombre"
     * e "Iniciales" sean correctos.
     *
     * @return un String con los errores de validación. Si no hay errores, devuelve una cadena vacía
     */
    private String validar() {
        String errores = "";

        // Validar que el campo Nombre no esté vacío
        if(tfNombre.getText().isEmpty()) {
            errores += "Tienes que rellenar el campo Nombre\n";
        }

        // Validar que el campo Iniciales no esté vacío y que tenga un máximo de 3 caracteres
        if(tfIniciales.getText().isEmpty()) {
            errores += "Tienes que rellenar el campo Iniciales\n";
        } else if(tfIniciales.getText().length() > 3) {
            errores += "Las iniciales solo pueden tener un máximo de 3 caracteres\n";
        }

        return errores;
    }

    /**
     * Limpia los campos de texto del formulario.
     */
    private void vaciarCampos() {
        tfNombre.setText("");
        tfIniciales.setText("");
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
