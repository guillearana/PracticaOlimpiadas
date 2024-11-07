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

/**
 * Controlador para la ventana de añadir un nuevo deportista.
 *
 * Este controlador gestiona la interfaz de usuario para añadir un nuevo deportista al sistema,
 * valida los datos introducidos, los guarda en la base de datos y muestra alertas informativas o de error.
 */
public class AniadirDeportistaController {

    /** Botón para cancelar la acción y cerrar la ventana. */
    @FXML
    private Button btnCancelar;

    /** Botón para guardar el deportista ingresado. */
    @FXML
    private Button btnGuardar;

    /** Campo de texto para ingresar la altura del deportista. */
    @FXML
    private TextField tfAltura;

    /** Campo de texto para ingresar el nombre del deportista. */
    @FXML
    private TextField tfNombre;

    /** Campo de texto para ingresar el peso del deportista. */
    @FXML
    private TextField tfPeso;

    /** Campo de texto para ingresar el sexo del deportista. */
    @FXML
    private TextField tfSexo;

    /**
     * Cancela la operación y cierra la ventana cuando se hace clic en el botón "Cancelar".
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
     * Guarda el deportista ingresado. Valida los datos y, si son correctos,
     * añade el deportista a la base de datos.
     *
     * @param event el evento de acción del botón "Guardar"
     */
    @FXML
    private void accionGuardar(ActionEvent event) {

        String errores = validar();

        if(errores.isEmpty()) {
            int peso = Integer.parseInt(tfPeso.getText());
            int altura = Integer.parseInt(tfAltura.getText());
            String nombre = tfNombre.getText();
            String sexo = tfSexo.getText();

            Deportista deportista = new Deportista(peso, altura, nombre, "", sexo);
            // Añadir deportistas a la base de datos
            DeportistasDao dao = new DeportistasDao();
            dao.aniadirDeportista(deportista);

            alertaInformacion("Se ha añadido correctamente el deportista\nActualiza la tabla para ver los cambios");

            vaciarCampos();
        } else {
            alertaError(errores);
        }
    }

    /**
     * Valida los campos del formulario. Verifica que los campos "Peso", "Altura",
     * "Nombre" y "Sexo" sean correctos.
     *
     * @return un String con los errores de validación. Si no hay errores, devuelve una cadena vacía
     */
    private String validar() {
        String errores = "";

        // Validar que el campo Peso no esté vacío y que sea un número
        if(tfPeso.getText().isEmpty()) {
            errores += "Tienes que rellenar el campo Peso\n";
        } else {
            try {
                Integer.parseInt(tfPeso.getText());
            } catch (NumberFormatException e) {
                errores += "El campo de peso tiene que ser numérico\n";
            }
        }

        // Validar que el campo Altura no esté vacío y que sea un número
        if(tfAltura.getText().isEmpty()) {
            errores += "Tienes que rellenar el campo Altura\n";
        } else {
            try {
                Integer.parseInt(tfAltura.getText());
            } catch (NumberFormatException e) {
                errores += "El campo de altura tiene que ser numérico\n";
            }
        }

        // Validar que el campo Nombre no esté vacío
        if(tfNombre.getText().isEmpty()) {
            errores += "Tienes que rellenar el campo Nombre\n";
        }

        // Validar que el campo Sexo no esté vacío y que tenga un valor válido
        if(tfSexo.getText().isEmpty()) {
            errores += "Tienes que rellenar el campo Sexo\n";
        } else if(!tfSexo.getText().equals("M") && !tfSexo.getText().equals("F")) {
            errores += "El campo de sexo tiene que ser 'M' o 'F'";
        }

        return errores;
    }

    /**
     * Limpia los campos de texto del formulario.
     */
    private void vaciarCampos() {
        tfPeso.setText("");
        tfAltura.setText("");
        tfNombre.setText("");
        tfSexo.setText("");
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
