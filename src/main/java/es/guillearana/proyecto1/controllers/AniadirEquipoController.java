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

public class AniadirEquipoController {

    @FXML
    private Button btnCancelar;

    @FXML
    private Button btnGuardar;

    @FXML
    private TextField tfNombre;

    @FXML
    private TextField tfIniciales;

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
            String iniciales = tfIniciales.getText();

            Equipo equipo = new Equipo(nombre, iniciales);
            // Añadir deportistas
            EquiposDao dao = new EquiposDao();
            dao.aniadirEquipo(equipo);

            alertaInformacion("Se ha añadido correctamente el equipo\nActualiza la tabla para ver los cambios");

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
        if(tfIniciales.getText().isEmpty()) {
            errores += "Tienes que rellenar el campo Iniciales\n";
        }else if(tfIniciales.getText().length()>3){
            errores += "Las iniciales solo pueden tener un máximo de 3 carácteres\n";
        }

        return errores;
    }

    private void vaciarCampos() {
        tfNombre.setText("");
        tfIniciales.setText("");
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
