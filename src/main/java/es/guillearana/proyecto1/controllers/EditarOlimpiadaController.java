package es.guillearana.proyecto1.controllers;

import es.guillearana.proyecto1.dao.OlimpiadasDao;
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
import es.guillearana.proyecto1.model.Olimpiada;

public class EditarOlimpiadaController {
    private Olimpiada olimpiada;

    // Atributo de la ventana emergente
    private Stage ventanaEmergente;

    @FXML
    private TextField tfNombre;

    @FXML
    private TextField tfAnio;

    @FXML
    private TextField tfTemporada;

    @FXML
    private TextField tfCiudad;

    @FXML
    private Button btnAceptar;

    @FXML
    private Button btnCancelar;

    /**
     * Método que configura la ventana emergente para editar los detalles de una olimpiada.
     *
     * @param olimpiada La olimpiada que se va a modificar.
     */
    public void editarOlimpiada(Olimpiada olimpiada) {
        this.olimpiada = olimpiada;
        // Creamos una nueva instancia de la clase Stage para la ventana emergente
        ventanaEmergente = new Stage();

        // Le ponemos titulo a la ventana
        ventanaEmergente.setTitle("MODIFICAR OLIMPIADA");

        // Creamos un contenedor VBox como raíz de la ventana emergente
        VBox contenedorRaiz = new VBox();

        // Creamos contenedores HBox para cada campo de entrada
        HBox contenedorNombre = new HBox();
        HBox contenedorAnio = new HBox();
        HBox contenedorTemporada = new HBox();
        HBox contenedorCiudad = new HBox();

        // Establecemos un espaciado entre elementos en los contenedores HBox
        contenedorNombre.setSpacing(10);
        contenedorAnio.setSpacing(10);
        contenedorTemporada.setSpacing(10);
        contenedorCiudad.setSpacing(10);

        // le damos valor a los TextFields
        tfNombre = new TextField();
        tfNombre.setText(olimpiada.getNombre());

        tfAnio = new TextField();
        tfAnio.setText(olimpiada.getAnio()+"");

        tfTemporada = new TextField();
        tfTemporada.setText(olimpiada.getTemporada());

        tfCiudad = new TextField();
        tfCiudad.setText(olimpiada.getCiudad());

        // Añadimos los labels al contenedor
        contenedorNombre.getChildren().addAll(new javafx.scene.control.Label("Nombre"), tfNombre);
        contenedorAnio.getChildren().addAll(new javafx.scene.control.Label("Año"), tfAnio);
        contenedorTemporada.getChildren().addAll(new javafx.scene.control.Label("Temporada"), tfTemporada);
        contenedorCiudad.getChildren().addAll(new javafx.scene.control.Label("Ciudad"), tfCiudad);

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
        contenedorRaiz.getChildren().addAll(contenedorNombre, contenedorAnio, contenedorTemporada, contenedorCiudad, contenedorBotones);

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
     * Método que se ejecuta al hacer clic en el botón "Guardar". Modifica los datos de la olimpiada.
     *
     * @param event El evento de acción.
     */
    void modificar(ActionEvent event) {
        // Antes de modificar, validamos que los campos de entrada no contengan errores
        String errores = validarCampos();

        if (errores.isEmpty()) {
            try {
                OlimpiadasDao olimpiadasDao = new OlimpiadasDao();
                // Le ponemos los datos nuevos al deportista
                olimpiada.setNombre(this.tfNombre.getText().toString());
                olimpiada.setAnio(Integer.parseInt(this.tfAnio.getText().toString()));
                olimpiada.setTemporada(this.tfTemporada.getText().toString());
                olimpiada.setCiudad(this.tfCiudad.getText().toString());

                olimpiadasDao.editarOlimpiada(olimpiada);

                ventanaEmergente.close();
                alertaInformacion("Se ha modificado la olimpiada seleccionada\nActualiza la tabla para ver los cambios");
            } catch (Exception e) {
                // Manejamos cualquier excepción que pueda ocurrir, aunque no se realiza ninguna acción específica en caso de error
            }
        } else {
            // Mostramos una alerta de error con los mensajes de error
            alertaError(errores);
        }
    }

    /**
     * Valida los campos de entrada y retorna una cadena con los errores encontrados.
     *
     * @return Los errores de validación como una cadena de texto.
     */
    private String validarCampos() {
        String errores = "";

        if(tfNombre.getText().isEmpty()) {
            errores += "Tienes que rellenar el campo Nombre\n";
        }else if(tfNombre.getText().length()>11){
            errores += "El nombre tiene que tener un maximo de 11 caracteres\n";
        }
        if(tfAnio.getText().isEmpty()) {
            errores += "Tienes que rellenar el campo Año\n";
        }else {
            try {
                Integer.parseInt(tfAnio.getText());
            } catch (NumberFormatException e) {
                errores += "El campo del año tiene que ser numerico\n";
            }
        }
        if(tfTemporada.getText().isEmpty()) {
            errores += "Tienes que rellenar el campo temporada\n";
        }else if(!tfTemporada.getText().equals("Summer") && !tfTemporada.getText().equals("Winter")){
            errores += "La temporada solo puede ser 'Summer' o 'Winter'\n";
        }
        if(tfCiudad.getText().isEmpty()) {
            errores += "Tienes que rellenar el campo Ciudad\n";
        }

        return errores;
    }

    /**
     * Método que cierra la ventana emergente cuando se hace clic en el botón "Cancelar".
     *
     * @param event El evento de acción.
     */
    @FXML
    void accionCancelar(ActionEvent event) {
        // Cierra la ventana actual al hacer clic en el botón Cancelar
        Stage stage = (Stage) btnCancelar.getScene().getWindow();
        stage.close();
    }

    /**
     * Muestra una alerta de tipo error con el mensaje especificado.
     *
     * @param mensaje El mensaje a mostrar en la alerta de error.
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
     * Muestra una alerta de tipo información con el mensaje especificado.
     *
     * @param mensaje El mensaje a mostrar en la alerta de información.
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
