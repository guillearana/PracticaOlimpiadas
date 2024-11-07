package es.guillearana.proyecto1.controllers;

import es.guillearana.proyecto1.dao.DeportesDao;
import es.guillearana.proyecto1.dao.EventosDao;
import es.guillearana.proyecto1.dao.OlimpiadasDao;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import es.guillearana.proyecto1.model.Deporte;
import es.guillearana.proyecto1.model.Evento;
import es.guillearana.proyecto1.model.Olimpiada;

/**
 * Controlador para la edición de eventos en la aplicación.
 * Esta clase maneja la lógica de la ventana emergente que permite modificar los detalles de un evento.
 */
public class EditarEventoController {
    private Evento evento;

    // Atributo de la ventana emergente
    private Stage ventanaEmergente;

    @FXML
    private TextField tfNombre;

    @FXML
    private TextField tfOlimpiada;

    @FXML
    private TextField tfDeporte;

    @FXML
    private Button btnAceptar;

    @FXML
    private Button btnCancelar;

    // ComboBoxes para seleccionar la Olimpiada y el Deporte
    ComboBox<Olimpiada> cbOlimpiadas;

    ComboBox<Deporte> cbDeportes;

    /**
     * Método para inicializar la ventana emergente para editar un evento.
     * Se configura la ventana con los datos actuales del evento y se preparan los ComboBoxes con las opciones disponibles.
     * @param evento El evento que se va a editar.
     */
    public void editarEvento(Evento evento) {
        this.evento = evento;
        // Creamos una nueva instancia de la clase Stage para la ventana emergente
        ventanaEmergente = new Stage();

        // Le ponemos título a la ventana
        ventanaEmergente.setTitle("MODIFICAR EVENTO");

        // Creamos un contenedor VBox como raíz de la ventana emergente
        VBox contenedorRaiz = new VBox();

        // Creamos contenedores HBox para cada campo de entrada (Nombre, Olimpiada, Deporte)
        HBox contenedorNombre = new HBox();
        HBox contenedorOlimpiada = new HBox();
        HBox contenedorDeporte = new HBox();

        // Establecemos un espaciado entre elementos en los contenedores HBox
        contenedorNombre.setSpacing(10);
        contenedorOlimpiada.setSpacing(10);
        contenedorDeporte.setSpacing(10);

        // Asignamos los valores actuales del evento a los campos de texto
        tfNombre = new TextField();
        tfNombre.setText(evento.getNombre());

        // Cargamos las Olimpiadas y Deportes desde la base de datos
        OlimpiadasDao olimpiadasDao = new OlimpiadasDao();
        ObservableList<Olimpiada> listaOlimpiadas =  olimpiadasDao.cargarOlimpiadas("");
        cbOlimpiadas = new ComboBox<>();
        cbOlimpiadas.setItems(listaOlimpiadas);

        DeportesDao deportesDao = new DeportesDao();
        ObservableList<Deporte> listaDeportes =  deportesDao.cargarDeportes("");
        cbDeportes = new ComboBox<>();
        cbDeportes.setItems(listaDeportes);

        // Añadimos los labels y campos a sus contenedores
        contenedorNombre.getChildren().addAll(new javafx.scene.control.Label("Nombre"), tfNombre);
        contenedorOlimpiada.getChildren().addAll(new javafx.scene.control.Label("Olimpiada"), cbOlimpiadas);
        contenedorDeporte.getChildren().addAll(new javafx.scene.control.Label("Deporte"), cbDeportes);

        // Creamos un contenedor HBox para los botones (Guardar y Cerrar)
        HBox contenedorBotones = new HBox();
        contenedorBotones.setSpacing(10);

        // Creamos un botón "Guardar"
        Button guardarBtn = new Button("Guardar");
        guardarBtn.setOnAction(e -> modificar(e));

        // Creamos un botón "Cerrar" y configuramos su evento para cerrar la ventana emergente
        Button cerrarBtn = new Button("Cerrar");
        cerrarBtn.setOnAction(e -> ventanaEmergente.close());

        // Agregamos los botones al contenedor de botones
        contenedorBotones.getChildren().addAll(guardarBtn, cerrarBtn);

        // Agregamos todos los contenedores al contenedor raíz
        contenedorRaiz.getChildren().addAll(contenedorNombre, contenedorOlimpiada, contenedorDeporte, contenedorBotones);

        // Configuramos propiedades del contenedor raíz
        contenedorRaiz.setPadding(new Insets(20));
        contenedorRaiz.setSpacing(20);

        // Creamos una escena con el contenedor raíz
        Scene escena = new Scene(contenedorRaiz);
        // Establecemos la escena en la ventana emergente
        ventanaEmergente.setScene(escena);

        // Desactivamos la posibilidad de redimensionar la ventana emergente
        ventanaEmergente.setResizable(false);
        // Mostramos la ventana emergente
        ventanaEmergente.show();
    }

    /**
     * Método que maneja la acción de guardar los cambios del evento editado.
     * Realiza la validación de los campos y actualiza el evento en la base de datos si no hay errores.
     * @param event El evento generado por el botón "Guardar".
     */
    void modificar(ActionEvent event) {
        // Antes de modificar, validamos que los campos de entrada no contengan errores
        String errores = validarCampos();

        if (errores.isEmpty()) {
            try {
                EventosDao eventosDao = new EventosDao();
                // Le asignamos los nuevos valores al evento
                evento.setNombre(this.tfNombre.getText().toString());
                evento.setId_olimpiada(this.cbOlimpiadas.getSelectionModel().getSelectedItem().getId_olimpiada());
                evento.setId_deporte(this.cbDeportes.getSelectionModel().getSelectedItem().getId_deporte());

                // Actualizamos el evento en la base de datos
                eventosDao.editarEvento(evento);

                // Cerramos la ventana emergente
                ventanaEmergente.close();
                // Mostramos un mensaje de éxito
                alertaInformacion("Se ha modificado el Evento seleccionado\nActualiza la tabla para ver los cambios");
            } catch (Exception e) {
                // Manejamos cualquier excepción que pueda ocurrir
            }
        } else {
            // Mostramos una alerta de error con los mensajes de error
            alertaError(errores);
        }
    }

    /**
     * Método que valida los campos de entrada (Nombre, Olimpiada y Deporte).
     * @return Un String con los errores encontrados en los campos.
     */
    private String validarCampos() {
        String errores = "";

        // Validación de campo Nombre
        if(tfNombre.getText().isEmpty()) {
            errores += "Tienes que rellenar el campo Nombre\n";
        }

        // Validación de Olimpiada seleccionada
        if(this.cbOlimpiadas.getSelectionModel().getSelectedItem() == null) {
            errores += "Tienes que seleccionar una Olimpiada\n";
        }

        // Validación de Deporte seleccionado
        if(this.cbDeportes.getSelectionModel().getSelectedItem() == null) {
            errores += "Tienes que seleccionar un Deporte\n";
        }

        return errores;
    }

    /**
     * Método que maneja la acción del botón Cancelar para cerrar la ventana emergente.
     * @param event El evento generado por el botón "Cancelar".
     */
    @FXML
    void accionCancelar(ActionEvent event) {
        // Cierra la ventana actual al hacer clic en el botón Cancelar
        Stage stage = (Stage) btnCancelar.getScene().getWindow();
        stage.close();
    }

    /**
     * Método que muestra una ventana emergente de alerta con el mensaje de error.
     * @param mensaje El mensaje que se mostrará en la ventana de error.
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
     * Método que muestra una ventana emergente de información con el mensaje proporcionado.
     * @param mensaje El mensaje que se mostrará en la ventana de información.
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
