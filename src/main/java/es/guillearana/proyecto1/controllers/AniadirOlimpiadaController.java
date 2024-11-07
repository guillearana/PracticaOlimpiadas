package es.guillearana.proyecto1.controllers;

import es.guillearana.proyecto1.dao.OlimpiadasDao;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import es.guillearana.proyecto1.model.Olimpiada;

/**
 * Controlador para la ventana de añadir una nueva Olimpiada.
 * Proporciona métodos para gestionar la interacción del usuario con los campos
 * de la ventana y validaciones de entrada.
 */
public class AniadirOlimpiadaController {

    @FXML
    private Button btnCancelar;

    @FXML
    private Button btnGuardar;

    @FXML
    private TextField tfNombre;

    @FXML
    private TextField tfCiudad;

    @FXML
    private TextField tfAnio;

    @FXML
    private TextField tfTemporada;

    /**
     * Cancela la operación y cierra la ventana actual cuando el usuario hace clic
     * en el botón de cancelar.
     *
     * @param event El evento de acción generado por el botón cancelar.
     */
    @FXML
    void accionCancelar(ActionEvent event) {
        // Cierra la ventana actual al hacer clic en el botón Cancelar
        Stage stage = (Stage) btnCancelar.getScene().getWindow();
        stage.close();
    }

    /**
     * Guarda los datos introducidos por el usuario en los campos de texto y
     * crea una nueva instancia de Olimpiada. Si los datos son válidos, se
     * añade la Olimpiada al sistema, si no, se muestra un mensaje de error.
     *
     * @param event El evento de acción generado por el botón guardar.
     */
    @FXML
    private void accionGuardar(ActionEvent event) {

        String errores = validarCampos();

        if(errores.isEmpty()) {
            String nombre = tfNombre.getText();
            String anio = tfAnio.getText();
            String ciudad = tfCiudad.getText();
            String temporada = tfTemporada.getText();

            Olimpiada olimpiada = new Olimpiada(Integer.parseInt(anio), nombre, ciudad, temporada);
            // Añadir la Olimpiada a la base de datos
            OlimpiadasDao dao = new OlimpiadasDao();
            dao.aniadirOlimpiada(olimpiada);

            alertaInformacion("Se ha añadido correctamente la Olimpiada\nActualiza la tabla para ver los cambios");

            vaciarCampos();
        } else {
            alertaError(errores);
        }
    }

    /**
     * Valida los campos de entrada del usuario. Verifica que los campos no
     * estén vacíos, que el año sea un número válido y que la temporada sea
     * "Summer" o "Winter".
     *
     * @return Una cadena con los errores de validación. Si no hay errores,
     *         la cadena está vacía.
     */
    private String validarCampos() {
        String errores = "";

        if(tfNombre.getText().isEmpty()) {
            errores += "Tienes que rellenar el campo Nombre\n";
        } else if(tfNombre.getText().length() > 11) {
            errores += "El nombre tiene que tener un máximo de 11 caracteres\n";
        }
        if(tfAnio.getText().isEmpty()) {
            errores += "Tienes que rellenar el campo Año\n";
        } else {
            try {
                Integer.parseInt(tfAnio.getText());
            } catch (NumberFormatException e) {
                errores += "El campo del año tiene que ser numérico\n";
            }
        }
        if(tfTemporada.getText().isEmpty()) {
            errores += "Tienes que rellenar el campo temporada\n";
        } else if(!tfTemporada.getText().equals("Summer") && !tfTemporada.getText().equals("Winter")) {
            errores += "La temporada solo puede ser 'Summer' o 'Winter'\n";
        }
        if(tfCiudad.getText().isEmpty()) {
            errores += "Tienes que rellenar el campo Ciudad\n";
        }

        return errores;
    }

    /**
     * Limpia todos los campos de entrada en la ventana.
     */
    private void vaciarCampos() {
        tfNombre.setText("");
        tfAnio.setText("");
        tfTemporada.setText("");
        tfCiudad.setText("");
    }

    /**
     * Muestra una ventana emergente de alerta de error con el mensaje especificado.
     *
     * @param mensaje El mensaje de error que se mostrará en la ventana emergente.
     */
    private void alertaError(String mensaje) {
        // Alerta de error con botón
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
     * Muestra una ventana emergente de alerta de información con el mensaje especificado.
     *
     * @param mensaje El mensaje de información que se mostrará en la ventana emergente.
     */
    private void alertaInformacion(String mensaje) {
        // Alerta de información con botón
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
