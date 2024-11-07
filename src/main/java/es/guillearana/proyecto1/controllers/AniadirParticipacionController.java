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

/**
 * Controlador para la ventana de añadir una nueva participación en un evento.
 * Gestiona las interacciones del usuario para agregar una participación,
 * validando los datos de entrada y proporcionando alertas en caso de errores.
 */
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

    /**
     * Método llamado al hacer clic en el botón de cancelar. Cierra la ventana
     * actual sin realizar ninguna acción.
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
     * Método de inicialización que carga los datos necesarios en los ComboBoxes
     * cuando la ventana se inicializa. Carga los equipos, deportistas y eventos
     * desde la base de datos.
     */
    @FXML
    void initialize() {
        // Cargar equipos
        EquiposDao equiposDao = new EquiposDao();
        ObservableList<Equipo> listaEquipos = equiposDao.cargarEquipos("");
        cbEquipos.setItems(listaEquipos);

        // Cargar deportistas
        DeportistasDao deportistasDao = new DeportistasDao();
        ObservableList<Deportista> listaDeportistas = deportistasDao.cargarDeportista("");
        cbDeportistas.setItems(listaDeportistas);

        // Cargar eventos
        EventosDao eventosDao = new EventosDao();
        ObservableList<Evento> listaEventos = eventosDao.cargarEventos("");
        cbEventos.setItems(listaEventos);
    }

    /**
     * Método llamado al hacer clic en el botón de guardar. Valida los datos de
     * entrada y, si son correctos, crea una nueva participación y la guarda en
     * la base de datos. En caso de errores, muestra una alerta con el mensaje de
     * error.
     *
     * @param event El evento de acción generado por el botón guardar.
     */
    @FXML
    private void accionGuardar(ActionEvent event) {

        String errores = validar();

        if (errores.isEmpty()) {
            // Si no hay errores, creamos la participación
            Participacion participacion = new Participacion(
                    this.cbDeportistas.getSelectionModel().getSelectedItem().getId_deportista(),
                    this.cbEventos.getSelectionModel().getSelectedItem().getId_evento(),
                    this.cbEquipos.getSelectionModel().getSelectedItem().getId_equipo(),
                    Integer.parseInt(tfEdad.getText()),
                    tfMedalla.getText());

            // Añadir participación
            ParticipacionesDao dao = new ParticipacionesDao();
            dao.aniadirParticipacion(participacion);

            alertaInformacion("Se ha añadido correctamente la Participación\nActualiza la tabla para ver los cambios");

            vaciarCampos();
        } else {
            alertaError(errores);
        }
    }

    /**
     * Valida los campos de entrada. Verifica que todos los campos estén
     * completos y que la edad sea un valor numérico.
     *
     * @return Una cadena con los errores de validación. Si no hay errores,
     *         la cadena estará vacía.
     */
    private String validar() {
        String errores = "";

        if (tfMedalla.getText().isEmpty()) {
            errores += "Tienes que rellenar el campo Medalla\n";
        }

        if (tfEdad.getText().isEmpty()) {
            errores += "Tienes que rellenar el campo Edad\n";
        }

        if (this.cbEquipos.getSelectionModel().getSelectedItem() == null) {
            errores += "Tienes que seleccionar un Equipo\n";
        }

        return errores;
    }

    /**
     * Limpia todos los campos de la ventana, dejando los ComboBoxes con el
     * primer valor por defecto y los TextFields vacíos.
     */
    private void vaciarCampos() {
        tfMedalla.setText("");
        tfEdad.setText("");
        cbEquipos.getSelectionModel().select(0);
    }

    /**
     * Muestra una ventana emergente de alerta con el mensaje de error proporcionado.
     *
     * @param mensaje El mensaje de error que se mostrará en la ventana emergente.
     */
    private void alertaError(String mensaje) {
        // Alerta de error con botón
        Alert ventanaEmergente = new Alert(AlertType.ERROR);
        ventanaEmergente.setTitle("Info");
        ventanaEmergente.setContentText(mensaje);
        Button ocultarBtn = new Button("Aceptar");
        ocultarBtn.setOnAction(e -> {
            ventanaEmergente.hide();
        });
        ventanaEmergente.show();
    }

    /**
     * Muestra una ventana emergente de alerta con el mensaje de información
     * proporcionado.
     *
     * @param mensaje El mensaje de información que se mostrará en la ventana
     *                emergente.
     */
    private void alertaInformacion(String mensaje) {
        // Alerta de información con botón
        Alert ventanaEmergente = new Alert(AlertType.INFORMATION);
        ventanaEmergente.setTitle("Info");
        ventanaEmergente.setContentText(mensaje);
        Button ocultarBtn = new Button("Aceptar");
        ocultarBtn.setOnAction(e -> {
            ventanaEmergente.hide();
        });
        ventanaEmergente.show();
    }
}
