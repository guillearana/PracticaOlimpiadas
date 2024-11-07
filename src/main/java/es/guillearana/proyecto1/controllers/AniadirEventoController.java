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

/**
 * Controlador para la ventana de añadir un nuevo evento.
 *
 * Este controlador gestiona la interfaz de usuario para añadir un nuevo evento a la base de datos.
 * Se encarga de cargar las olimpiadas y deportes disponibles, validar los datos ingresados y mostrar alertas de éxito o error.
 */
public class AniadirEventoController {

    /** Botón para cancelar la acción y cerrar la ventana. */
    @FXML
    private Button btnCancelar;

    /** Botón para guardar el evento ingresado. */
    @FXML
    private Button btnGuardar;

    /** ComboBox para seleccionar un deporte. */
    @FXML
    private ComboBox<Deporte> cbDeportes;

    /** ComboBox para seleccionar una olimpiada. */
    @FXML
    private ComboBox<Olimpiada> cbOlimpiadas;

    /** Campo de texto para ingresar el nombre del evento. */
    @FXML
    private TextField tfNombre;

    /**
     * Método vacío para generar olimpiadas si es necesario (por ahora no implementado).
     *
     * @param event el evento de acción del ComboBox (no usado actualmente)
     */
    @FXML
    void generarOlimpiadas(ActionEvent event) {
        // Implementación pendiente para generar olimpiadas
    }

    /**
     * Inicializa el controlador cargando las olimpiadas y deportes disponibles en los ComboBox.
     *
     * Este método se ejecuta automáticamente después de que los elementos de la interfaz gráfica
     * han sido cargados, rellenando los ComboBox con los datos correspondientes.
     */
    @FXML
    void initialize() {
        // Cargar olimpiadas en el ComboBox
        OlimpiadasDao olimpiadasDao = new OlimpiadasDao();
        ObservableList<Olimpiada> listaOlimpiadas = olimpiadasDao.cargarOlimpiadas("");
        cbOlimpiadas.setItems(listaOlimpiadas);

        // Cargar deportes en el ComboBox
        DeportesDao deportesDao = new DeportesDao();
        ObservableList<Deporte> listaDeportes = deportesDao.cargarDeportes("");
        cbDeportes.setItems(listaDeportes);
    }

    /**
     * Cancela la operación y cierra la ventana cuando el usuario hace clic en el botón "Cancelar".
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
     * Guarda el evento ingresado. Valida los datos y, si son correctos,
     * añade el evento a la base de datos.
     *
     * @param event el evento de acción del botón "Guardar"
     */
    @FXML
    private void accionGuardar(ActionEvent event) {

        String errores = validar();

        if(errores.isEmpty()) {
            String nombre = tfNombre.getText();

            Evento evento = new Evento(
                    nombre,
                    this.cbOlimpiadas.getSelectionModel().getSelectedItem().getId_olimpiada(),
                    this.cbDeportes.getSelectionModel().getSelectedItem().getId_deporte()
            );
            // Añadir evento a la base de datos
            EventosDao dao = new EventosDao();
            dao.aniadirEvento(evento);

            alertaInformacion("Se ha añadido correctamente el Evento\nActualiza la tabla para ver los cambios");

            vaciarCampos();
        } else {
            alertaError(errores);
        }
    }

    /**
     * Valida los campos del formulario. Verifica que los campos "Nombre",
     * "Olimpiada" y "Deporte" sean correctos.
     *
     * @return un String con los errores de validación. Si no hay errores, devuelve una cadena vacía
     */
    private String validar() {
        String errores = "";

        // Validar que el campo Nombre no esté vacío
        if(tfNombre.getText().isEmpty()) {
            errores += "Tienes que rellenar el campo Nombre\n";
        }

        // Validar que se haya seleccionado una Olimpiada
        if(this.cbOlimpiadas.getSelectionModel().getSelectedItem() == null) {
            errores += "Tienes que seleccionar una Olimpiada\n";
        }

        // Validar que se haya seleccionado un Deporte
        if(this.cbDeportes.getSelectionModel().getSelectedItem() == null) {
            errores += "Tienes que seleccionar un Deporte\n";
        }

        return errores;
    }

    /**
     * Limpia los campos de texto del formulario y restablece las selecciones de los ComboBox.
     */
    private void vaciarCampos() {
        tfNombre.setText("");
        cbOlimpiadas.getSelectionModel().select(0);
        cbDeportes.getSelectionModel().select(0);
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
