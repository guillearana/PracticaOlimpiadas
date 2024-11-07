package es.guillearana.proyecto1.controllers;

import es.guillearana.proyecto1.dao.DeportistasDao;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import es.guillearana.proyecto1.model.Deportista;

public class EditarDeportistaController {
    private Deportista deportista;

    // Atributo de la ventana emergente
    private Stage ventanaEmergente;

    @FXML
    private TextField tfNombre;

    @FXML
    private TextField tfSexo;

    @FXML
    private TextField tfPeso;

    @FXML
    private TextField tfAltura;

    @FXML
    private Button btnAceptar;

    @FXML
    private Button btnCancelar;

    /**
     * Método que inicializa la ventana emergente para editar los detalles de un deportista.
     * Asigna los valores actuales del deportista a los campos de texto correspondientes.
     *
     * @param d Deportista que se desea editar.
     */
    public void editarDeportista(Deportista d) {
        this.deportista = d;
        // Creamos una nueva instancia de la clase Stage para la ventana emergente
        ventanaEmergente = new Stage();

        // Le ponemos titulo a la ventana
        ventanaEmergente.setTitle("MODIFICAR DEPORTISTA");

        // Creamos un contenedor VBox como raíz de la ventana emergente
        VBox contenedorRaiz = new VBox();

        // Creamos contenedores HBox para cada campo de entrada (Nombre, Sexo, Peso, Altura)
        HBox contenedorNombre = new HBox();
        HBox contenedorSexo = new HBox();
        HBox contenedorPeso = new HBox();
        HBox contenedorAltura = new HBox();

        // Establecemos un espaciado entre elementos en los contenedores HBox
        contenedorNombre.setSpacing(10);
        contenedorSexo.setSpacing(10);
        contenedorPeso.setSpacing(10);
        contenedorAltura.setSpacing(10);

        // Le damos valor a los TextFields
        tfNombre = new TextField();
        tfNombre.setText(d.getNombre());

        tfSexo = new TextField();
        tfSexo.setText(d.getSexo());

        tfPeso = new TextField();
        tfPeso.setText(d.getPeso() + "");

        tfAltura = new TextField();
        tfAltura.setText(d.getAltura() + "");

        // Si el campo de Nombre no existe, creamos uno nuevo (tfNombre)
        contenedorNombre.getChildren().addAll(new javafx.scene.control.Label("Nombre"), tfNombre);
        contenedorSexo.getChildren().addAll(new javafx.scene.control.Label("Sexo"), tfSexo);
        contenedorPeso.getChildren().addAll(new javafx.scene.control.Label("Peso"), tfPeso);
        contenedorAltura.getChildren().addAll(new javafx.scene.control.Label("Altura"), tfAltura);

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
        contenedorRaiz.getChildren().addAll(contenedorNombre, contenedorSexo, contenedorPeso, contenedorAltura, contenedorBotones);

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
     * Método que se ejecuta al hacer clic en el botón "Guardar".
     * Valida los campos y guarda los cambios realizados en el deportista.
     *
     * @param event Evento generado al hacer clic en el botón "Guardar".
     */
    void modificar(ActionEvent event) {
        // Antes de modificar, validamos que los campos de entrada no contengan errores
        String errores = validarCampos();

        if (errores.isEmpty()) {
            try {
                DeportistasDao deportistaDao = new DeportistasDao();
                // Le ponemos los datos nuevos al deportista
                deportista.setNombre(this.tfNombre.getText().toString());
                deportista.setSexo(this.tfSexo.getText().toString());
                deportista.setPeso(Integer.parseInt(this.tfPeso.getText()));
                deportista.setAltura(Integer.parseInt(this.tfAltura.getText()));

                deportistaDao.editarDeportista(deportista);

                ventanaEmergente.close();
                alertaInformacion("Se ha modificado el deportista seleccionado\nActualiza la tabla para ver los cambios");
            } catch (Exception e) {
                // Manejamos cualquier excepción que pueda ocurrir, aunque no se realiza ninguna acción específica en caso de error
            }
        } else {
            // Mostramos una alerta de error con los mensajes de error
            alertaError(errores);
        }
    }

    /**
     * Método que valida los campos de entrada.
     * Retorna una cadena con los errores encontrados durante la validación.
     *
     * @return Cadena con los errores de validación (si existen).
     */
    private String validarCampos() {
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

    /**
     * Método que se ejecuta al hacer clic en el botón "Cancelar".
     * Cierra la ventana emergente sin guardar los cambios.
     *
     * @param event Evento generado al hacer clic en el botón "Cancelar".
     */
    @FXML
    void accionCancelar(ActionEvent event) {
        // Cierra la ventana actual al hacer clic en el botón Cancelar
        Stage stage = (Stage) btnCancelar.getScene().getWindow();
        stage.close();
    }

    /**
     * Muestra una ventana emergente de alerta con el mensaje de error.
     *
     * @param mensaje Mensaje de error a mostrar en la alerta.
     */
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

    /**
     * Muestra una ventana emergente de información con el mensaje proporcionado.
     *
     * @param mensaje Mensaje de información a mostrar en la alerta.
     */
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
