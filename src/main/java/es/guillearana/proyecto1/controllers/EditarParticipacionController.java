package es.guillearana.proyecto1.controllers;

import es.guillearana.proyecto1.dao.EquiposDao;
import es.guillearana.proyecto1.dao.ParticipacionesDao;
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
import es.guillearana.proyecto1.model.Equipo;
import es.guillearana.proyecto1.model.Participacion;

public class EditarParticipacionController {
    private Participacion participacion;

    // Atributo de la ventana emergente
    private Stage ventanaEmergente;

    ComboBox<Equipo> cbEquipos;

    @FXML
    private TextField tfEdad;

    @FXML
    private TextField tfMedalla;

    @FXML
    private Button btnAceptar;

    @FXML
    private Button btnCancelar;


    public void editarParticipacion(Participacion participacion) {
        this.participacion = participacion;
        // Creamos una nueva instancia de la clase Stage para la ventana emergente
        ventanaEmergente = new Stage();

        // Le ponemos titulo a la ventana
        ventanaEmergente.setTitle("MODIFICAR PARTICIPACION");

        // Creamos un contenedor VBox como raíz de la ventana emergente
        VBox contenedorRaiz = new VBox();

        // Creamos contenedores HBox para cada campo de entrada
        HBox contenedorEquipo = new HBox();
        HBox contenedorEdad = new HBox();
        HBox contenedorMedalla = new HBox();

        // Establecemos un espaciado entre elementos en los contenedores HBox
        contenedorEquipo.setSpacing(10);
        contenedorEdad.setSpacing(10);
        contenedorMedalla.setSpacing(10);

        // hacemos dos combos para Olimpiadas y Deportes
        EquiposDao equiposDao = new EquiposDao();
        ObservableList<Equipo> listaEquipos =  equiposDao.cargarEquipos("");
        cbEquipos = new ComboBox<>();
        cbEquipos.setItems(listaEquipos);

        // le damos valor a los TextFields
        tfEdad = new TextField();
        tfEdad.setText(participacion.getEdad()+"");

        tfMedalla = new TextField();
        tfMedalla.setText(participacion.getMedalla());

        // Añadimos los elementos al contenedor
        contenedorEquipo.getChildren().addAll(new javafx.scene.control.Label("Equipos"), cbEquipos);
        contenedorEdad.getChildren().addAll(new javafx.scene.control.Label("Edad"), tfEdad);
        contenedorMedalla.getChildren().addAll(new javafx.scene.control.Label("Medalla"), tfMedalla);

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
        contenedorRaiz.getChildren().addAll(contenedorEquipo, contenedorEdad, contenedorMedalla, contenedorBotones);

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
                ParticipacionesDao participacionesDao = new ParticipacionesDao();
                // Le ponemos los datos nuevos al deportista
                participacion.setId_equipo(this.cbEquipos.getSelectionModel().getSelectedItem().getId_equipo());
                participacion.setEdad(Integer.parseInt(this.tfEdad.getText().toString()));
                participacion.setMedalla(this.tfMedalla.getText().toString());

                participacionesDao.editarParticipacion(participacion);

                ventanaEmergente.close();
                alertaInformacion("Se ha modificado la participacion\nActualiza la tabla para ver los cambios");
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
