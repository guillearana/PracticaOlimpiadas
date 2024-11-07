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

public class AniadirDeporteController {

    @FXML
    private Button btnCancelar;

    @FXML
    private Button btnGuardar;

    @FXML
    private TextField tfNombre;

    @FXML
    void accionCancelar(ActionEvent event) {
        // Cierra la ventana actual al hacer clic en el botón Cancelar
        Stage stage = (Stage) btnCancelar.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void accionGuardar(ActionEvent event) {

        String errores = validar();

        if(errores.isEmpty()) {
            String nombre = tfNombre.getText();

            Deporte deporte = new Deporte(nombre);
            // Añadir deportistas
            DeportesDao dao = new DeportesDao();
            dao.aniadirDeporte(deporte);

            alertaInformacion("Se ha añadido correctamente el deporte\nActualiza la tabla para ver los cambios");

            vaciarCampos();
        }else {
            alertaError(errores);
        }
    }

    private String validar() {
        String errores = "";

        if(tfNombre.getText().isEmpty()) {
            errores += "Tienes que rellenar el campo Nombre\n";
        }

        return errores;
    }

    private void vaciarCampos() {
        tfNombre.setText("");
    }

    // Metodos de diferentes ventanas emergentes
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