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

    ComboBox<Olimpiada> cbOlimpiadas;

    ComboBox<Deporte> cbDeportes;


    public void editarEvento(Evento evento) {
        this.evento = evento;
        // Creamos una nueva instancia de la clase Stage para la ventana emergente
        ventanaEmergente = new Stage();

        // Le ponemos titulo a la ventana
        ventanaEmergente.setTitle("MODIFICAR EVENTO");

        // Creamos un contenedor VBox como raíz de la ventana emergente
        VBox contenedorRaiz = new VBox();

        // Creamos contenedores HBox para cada campo de entrada (Nombre, Apellidos, Edad)
        HBox contenedorNombre = new HBox();
        HBox contenedorOlimpiada = new HBox();
        HBox contenedorDeporte = new HBox();

        // Establecemos un espaciado entre elementos en los contenedores HBox
        contenedorNombre.setSpacing(10);
        contenedorOlimpiada.setSpacing(10);
        contenedorDeporte.setSpacing(10);

        // le damos valor a los TextFields
        tfNombre = new TextField();
        tfNombre.setText(evento.getNombre());

        // hacemos dos combos para Olimpiadas y Deportes
        OlimpiadasDao olimpiadasDao = new OlimpiadasDao();
        ObservableList<Olimpiada> listaOlimpiadas =  olimpiadasDao.cargarOlimpiadas("");
        cbOlimpiadas = new ComboBox<>();
        cbOlimpiadas.setItems(listaOlimpiadas);

        DeportesDao deportesDao = new DeportesDao();
        ObservableList<Deporte> listaDeportes =  deportesDao.cargarDeportes("");
        cbDeportes = new ComboBox<>();
        cbDeportes.setItems(listaDeportes);

        // Añadimos los labels al contenedor
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

    void modificar(ActionEvent event) {
        // Antes de modificar, validamos que los campos de entrada no contengan errores
        String errores = validarCampos();

        if (errores.isEmpty()) {
            try {
                EventosDao eventosDao = new EventosDao();
                // Le ponemos los datos nuevos al deportista
                evento.setNombre(this.tfNombre.getText().toString());
                evento.setId_olimpiada(this.cbOlimpiadas.getSelectionModel().getSelectedItem().getId_olimpiada());
                evento.setId_deporte(this.cbDeportes.getSelectionModel().getSelectedItem().getId_deporte());

                eventosDao.editarEvento(evento);

                ventanaEmergente.close();
                alertaInformacion("Se ha modificado el Evento seleccionado\nActualiza la tabla para ver los cambios");
            } catch (Exception e) {
                // Manejamos cualquier excepción que pueda ocurrir, aunque no se realiza ninguna acción específica en caso de error
            }
        } else {
            // Mostramos una alerta de error con los mensajes de error
            alertaError(errores);
        }
    }

    // Este método valida los campos de entrada y retorna los errores como una cadena
    private String validarCampos() {
        String errores = "";

        if(tfNombre.getText().isEmpty()) {
            errores += "Tienes que rellenar el campo Peso\n";
        }

        if(this.cbOlimpiadas.getSelectionModel().getSelectedItem() == null) {
            errores += "Tienes que seleccionar una Olimpiada\n";
        }

        if(this.cbDeportes.getSelectionModel().getSelectedItem() == null) {
            errores += "Tienes que seleccionar un Deporte\n";
        }

        return errores;
    }

    @FXML
    void accionCancelar(ActionEvent event) {
        // Cierra la ventana actual al hacer clic en el botón Cancelar
        Stage stage = (Stage) btnCancelar.getScene().getWindow();
        stage.close();
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
