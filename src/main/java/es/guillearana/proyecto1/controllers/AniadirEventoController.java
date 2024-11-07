package es.guillearana.proyecto1.controllers;

import es.guillearana.proyecto1.dao.DeportesDao;
import es.guillearana.proyecto1.dao.EventosDao;
import es.guillearana.proyecto1.dao.OlimpiadasDao;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import es.guillearana.proyecto1.model.Deporte;
import es.guillearana.proyecto1.model.Evento;
import es.guillearana.proyecto1.model.Olimpiada;

public class AniadirEventoController {

    @FXML
    private Button btnCancelar;

    @FXML
    private Button btnGuardar;

    @FXML
    private ComboBox<Deporte> cbDeportes;

    @FXML
    private ComboBox<Olimpiada> cbOlimpiadas;

    @FXML
    private TextField tfNombre;

    @FXML
    void generarOlimpiadas(ActionEvent event) {

    }

    @FXML
    void initialize() {
        OlimpiadasDao olimpiadasDao = new OlimpiadasDao();
        ObservableList<Olimpiada> listaOlimpiadas =  olimpiadasDao.cargarOlimpiadas("");
        cbOlimpiadas.setItems(listaOlimpiadas);

        DeportesDao deportesDao = new DeportesDao();
        ObservableList<Deporte> listaDeportes =  deportesDao.cargarDeportes("");
        cbDeportes.setItems(listaDeportes);
    }

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

            Evento evento = new Evento(nombre, this.cbOlimpiadas.getSelectionModel().getSelectedItem().getId_olimpiada(), this.cbDeportes.getSelectionModel().getSelectedItem().getId_deporte());
            // Añadir deportistas
            EventosDao dao = new EventosDao();
            dao.aniadirEvento(evento);

            alertaInformacion("Se ha añadido correctamente el Evento\nActualiza la tabla para ver los cambios");

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

        if(this.cbOlimpiadas.getSelectionModel().getSelectedItem() == null) {
            errores += "Tienes que seleccionar una Olimpiada\n";
        }

        if(this.cbDeportes.getSelectionModel().getSelectedItem() == null) {
            errores += "Tienes que seleccionar un Deporte\n";
        }

        return errores;
    }

    private void vaciarCampos() {
        tfNombre.setText("");
        cbOlimpiadas.getSelectionModel().select(0);
        cbDeportes.getSelectionModel().select(0);
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
