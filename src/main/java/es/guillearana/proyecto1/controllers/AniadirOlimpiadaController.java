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

    @FXML
    void accionCancelar(ActionEvent event) {
        // Cierra la ventana actual al hacer clic en el botón Cancelar
        Stage stage = (Stage) btnCancelar.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void accionGuardar(ActionEvent event) {

        String errores = validarCampos();

        if(errores.isEmpty()) {
            String nombre = tfNombre.getText();
            String anio = tfAnio.getText();
            String ciudad = tfCiudad.getText();
            String temporada = tfTemporada.getText();

            Olimpiada olimpiada = new Olimpiada(Integer.parseInt(anio), nombre, ciudad, temporada);
            // Añadir deportistas
            OlimpiadasDao dao = new OlimpiadasDao();
            dao.aniadirOlimpiada(olimpiada);

            alertaInformacion("Se ha añadido correctamente la Olimpiada\nActualiza la tabla para ver los cambios");

            vaciarCampos();
        }else {
            alertaError(errores);
        }
    }

    private String validarCampos() {
        String errores = "";

        if(tfNombre.getText().isEmpty()) {
            errores += "Tienes que rellenar el campo Nombre\n";
        }else if(tfNombre.getText().length()>11){
            errores += "El nombre tiene que tener un maximo de 11 caracteres\n";
        }
        if(tfAnio.getText().isEmpty()) {
            errores += "Tienes que rellenar el campo Año\n";
        }else {
            try {
                Integer.parseInt(tfAnio.getText());
            } catch (NumberFormatException e) {
                errores += "El campo del año tiene que ser numerico\n";
            }
        }
        if(tfTemporada.getText().isEmpty()) {
            errores += "Tienes que rellenar el campo temporada\n";
        }else if(!tfTemporada.getText().equals("Summer") && !tfTemporada.getText().equals("Winter")){
            errores += "La temporada solo puede ser 'Summer' o 'Winter'\n";
        }
        if(tfCiudad.getText().isEmpty()) {
            errores += "Tienes que rellenar el campo Ciudad\n";
        }

        return errores;
    }

    private void vaciarCampos() {
        tfNombre.setText("");
        tfAnio.setText("");
        tfTemporada.setText("");
        tfCiudad.setText("");
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
