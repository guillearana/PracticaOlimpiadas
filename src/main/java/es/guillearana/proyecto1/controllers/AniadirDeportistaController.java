package es.guillearana.proyecto1.controllers;

import es.guillearana.proyecto1.dao.DeportistasDao;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import es.guillearana.proyecto1.model.Deportista;

public class AniadirDeportistaController {

    @FXML
    private Button btnCancelar;

    @FXML
    private Button btnGuardar;

    @FXML
    private TextField tfAltura;

    @FXML
    private TextField tfNombre;

    @FXML
    private TextField tfPeso;

    @FXML
    private TextField tfSexo;

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
            int peso = Integer.parseInt(tfPeso.getText());
            int altura = Integer.parseInt(tfAltura.getText());
            String nombre = tfNombre.getText();
            String sexo = tfSexo.getText();

            Deportista deportista = new Deportista(peso, altura, nombre, "", sexo);
            // Añadir deportistas
            DeportistasDao dao = new DeportistasDao();
            dao.aniadirDeportista(deportista);

            alertaInformacion("Se ha añadido correctamente el deportista\nActualiza la tabla para ver los cambios");

            vaciarCampos();
        }else {
            alertaError(errores);
        }
    }

    private String validar() {
        String errores = "";

        if(tfPeso.getText().isEmpty()) {
            errores += "Tienes que rellenar el campo Peso\n";
        }else {
            try {
                Integer.parseInt(tfPeso.getText());
            } catch (NumberFormatException e) {
                errores += "El campo de peso tiene que ser numerico\n";
            }
        }

        if(tfAltura.getText().isEmpty()) {
            errores += "Tienes que rellenar el campo Altura\n";
        }else {
            try {
                Integer.parseInt(tfAltura.getText());
            } catch (NumberFormatException e) {
                errores += "El campo de altura tiene que ser numerico\n";
            }
        }

        if(tfNombre.getText().isEmpty()) {
            errores += "Tienes que rellenar el campo Nombre\n";
        }

        if(tfSexo.getText().isEmpty()) {
            errores += "Tienes que rellenar el campo Sexo\n";
        }else if(!tfSexo.getText().equals("M") && !tfSexo.getText().equals("F")){
            errores += "El campo de sexo tiene que ser 'M' o 'F'";
        }

        return errores;
    }

    private void vaciarCampos() {
        tfPeso.setText("");
        tfAltura.setText("");
        tfNombre.setText("");
        tfSexo.setText("");
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
