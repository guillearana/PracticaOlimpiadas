package es.guillearana.proyecto1.controllers;

import es.guillearana.proyecto1.dao.DeportistasDao;
import es.guillearana.proyecto1.dao.EquiposDao;
import es.guillearana.proyecto1.dao.EventosDao;
import es.guillearana.proyecto1.dao.ParticipacionesDao;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import es.guillearana.proyecto1.model.Deportista;
import es.guillearana.proyecto1.model.Equipo;
import es.guillearana.proyecto1.model.Evento;
import es.guillearana.proyecto1.model.Participacion;

public class AniadirParticipacionController {

    @FXML
    private Button btnCancelar;

    @FXML
    private Button btnGuardar;

    @FXML
    private TextField tfMedalla;

    @FXML
    private ComboBox<Equipo> cbEquipos;

    @FXML
    private ComboBox<Deportista> cbDeportistas;

    @FXML
    private ComboBox<Evento> cbEventos;

    @FXML
    private TextField tfEdad;

    @FXML
    void generarOlimpiadas(ActionEvent event) {

    }

    @FXML
    void initialize() {
        // cargamos el combo con los equipos
        EquiposDao equiposDao = new EquiposDao();
        ObservableList<Equipo> listaEquipos =  equiposDao.cargarEquipos("");
        cbEquipos.setItems(listaEquipos);

        // cargamos el combo con los deportistas
        DeportistasDao deportistasDao = new DeportistasDao();
        ObservableList<Deportista> listaDeportistas =  deportistasDao.cargarDeportista("");
        cbDeportistas.setItems(listaDeportistas);

        // cargamos el combo con los eventos
        EventosDao eventosDao = new EventosDao();
        ObservableList<Evento> listaEventos =  eventosDao.cargarEventos("");
        cbEventos.setItems(listaEventos);
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
            // si no hay errores creamos la participacion
            Participacion participacion = new Participacion(this.cbDeportistas.getSelectionModel().getSelectedItem().getId_deportista(),
                    this.cbEventos.getSelectionModel().getSelectedItem().getId_evento(),
                    this.cbEquipos.getSelectionModel().getSelectedItem().getId_equipo(),
                    Integer.parseInt(tfEdad.getText()),
                    tfMedalla.getText());
            // Añadir participacion
            ParticipacionesDao dao = new ParticipacionesDao();
            dao.aniadirParticipacion(participacion);

            alertaInformacion("Se ha añadido correctamente la Participacion\nActualiza la tabla para ver los cambios");

            vaciarCampos();
        }else {
            alertaError(errores);
        }
    }

    private String validar() {
        String errores = "";

        if(tfMedalla.getText().isEmpty()) {
            errores += "Tienes que rellenar el campo Medalla\n";
        }

        if(tfEdad.getText().isEmpty()) {
            errores += "Tienes que rellenar el campo Edad\n";
        }

        if(this.cbEquipos.getSelectionModel().getSelectedItem() == null) {
            errores += "Tienes que seleccionar un Equipo\n";
        }

        return errores;
    }

    private void vaciarCampos() {
        tfMedalla.setText("");
        tfEdad.setText("");
        cbEquipos.getSelectionModel().select(0);
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
