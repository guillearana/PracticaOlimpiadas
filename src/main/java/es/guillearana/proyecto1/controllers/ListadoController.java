package es.guillearana.proyecto1.controllers;

import es.guillearana.proyecto1.dao.DeportesDao;
import es.guillearana.proyecto1.dao.DeportistasDao;
import es.guillearana.proyecto1.dao.EquiposDao;
import es.guillearana.proyecto1.dao.EventosDao;
import es.guillearana.proyecto1.dao.OlimpiadasDao;
import es.guillearana.proyecto1.dao.ParticipacionesDao;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import es.guillearana.proyecto1.model.Deporte;
import es.guillearana.proyecto1.model.Deportista;
import es.guillearana.proyecto1.model.Equipo;
import es.guillearana.proyecto1.model.Evento;
import es.guillearana.proyecto1.model.Olimpiada;
import es.guillearana.proyecto1.model.Participacion;

public class ListadoController {

    //
    @FXML
    private ToggleGroup groupRb;
    @FXML
    private TextField tfBuscarPorNombre;
    @FXML
    private Label lblListado;
    @FXML
    private RadioButton rbDeportistas, rbEventos, rbEquipos, rbParticipaciones, rbDeportes, rbOlimpiadas;

    /**
     * Método que se ejecuta al inicializar el controlador.
     * Este método gestiona los eventos y la visibilidad de las tablas asociadas a cada opción del RadioButton (Deportistas, Deportes, Equipos, Eventos, Olimpiadas).
     * También maneja la carga de datos de las tablas, la implementación de un menú contextual para las opciones de modificar y eliminar,
     * y la funcionalidad de filtrado en las tablas a través de los campos de búsqueda.
     */
    @FXML
    void initialize() {
        /**
         * Evento que se ejecuta cuando se selecciona el radioButton de Deportistas.
         * Este evento oculta otras tablas y muestra la tabla de Deportistas.
         * Además, carga los datos en la tabla de Deportistas, creando un menú contextual con las opciones
         * de "Modificar" y "Eliminar" para los Deportistas seleccionados, y permite realizar búsquedas filtradas.
         */
        rbDeportistas.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ocultarTablas();
                tablaDeportistas.setVisible(true);

                // cargamos los datos de la tabla
                colIdDeportista.setCellValueFactory(new PropertyValueFactory<Deportista, Integer>("id_deportista"));
                colNombreDeportista.setCellValueFactory(new PropertyValueFactory<Deportista, String>("nombre"));
                colSexoDeportista.setCellValueFactory(new PropertyValueFactory<Deportista, String>("sexo"));
                colPesoDeportista.setCellValueFactory(new PropertyValueFactory<Deportista, Integer>("peso"));
                colAlturaDeportista.setCellValueFactory(new PropertyValueFactory<Deportista, Integer>("altura"));
                colFotoDeportista.setCellValueFactory(new PropertyValueFactory<Deportista, String>("foto"));
                cargarTablaDeportistas("");

                // Creamos un menú contextual para la tabla
                ContextMenu contextMenu = new ContextMenu();
                MenuItem itemModificar = new MenuItem("Modificar");
                MenuItem itemBorrar = new MenuItem("Eliminar");

                // Manejamos eventos de clic para las opciones del menú contextual
                itemModificar.setOnAction(e -> {
                    Deportista deportistaSeleccionado = tablaDeportistas.getSelectionModel().getSelectedItem();
                    if (deportistaSeleccionado != null) {
                        EditarDeportistaController contr = new EditarDeportistaController();
                        // Modificamos el deportista
                        contr.editarDeportista(deportistaSeleccionado);
                        // mensaje una vez de haya modificado
                        cargarTablaDeportistas("");
                    }
                });

                itemBorrar.setOnAction(e -> {
                    Deportista deportistaSeleccionado = tablaDeportistas.getSelectionModel().getSelectedItem();
                    if (deportistaSeleccionado != null) {
                        DeportistasDao dao = new DeportistasDao();
                        // borramos el deportista
                        dao.borrarDeportista(deportistaSeleccionado);
                        // mensaje una vez de haya modificado
                        alertaInformacion("Se ha borrado el deportista seleccionado");
                        cargarTablaDeportistas("");
                    }
                });
                // agregamos los items al menu
                contextMenu.getItems().addAll(itemModificar, itemBorrar);
                tablaDeportistas.setContextMenu(contextMenu);

                // ponemos evento al TextField del filtrado por nombre
                tfBuscarPorNombre.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        // Este código se ejecutará cuando se presione "Enter" en el TextField.
                        String cadena = tfBuscarPorNombre.getText();
                        cargarTablaDeportistas(cadena);
                    }
                });
            }
        });


        /**
         * Evento que se ejecuta cuando se selecciona el radioButton de Deportes.
         * Este evento oculta otras tablas y muestra la tabla de Deportes.
         * Además, carga los datos en la tabla de Deportes, creando un menú contextual con las opciones
         * de "Modificar" y "Eliminar" para los Deportes seleccionados, y permite realizar búsquedas filtradas.
         */
        rbDeportes.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ocultarTablas();
                tablaDeporte.setVisible(true);

                // cargamos los datos de la tabla
                colIdDeporte.setCellValueFactory(new PropertyValueFactory<Deporte, Integer>("id_deporte"));
                colNombreDeporte.setCellValueFactory(new PropertyValueFactory<Deporte, String>("nombre"));
                cargarTablaDeportes("");

                // Creamos un menú contextual para la tabla
                ContextMenu contextMenu = new ContextMenu();
                MenuItem itemModificar = new MenuItem("Modificar");
                MenuItem itemBorrar = new MenuItem("Eliminar");

                // Manejamos eventos de clic para las opciones del menú contextual
                itemModificar.setOnAction(e -> {
                    Deporte deporteSeleccionado = tablaDeporte.getSelectionModel().getSelectedItem();
                    if (deporteSeleccionado != null) {
                        EditarDeporteController contr = new EditarDeporteController();
                        // Modificamos el Deporte
                        contr.editarDeporte(deporteSeleccionado);
                        // mensaje una vez de haya modificado
                        cargarTablaDeportes("");
                    }
                });

                itemBorrar.setOnAction(e -> {
                    Deporte deporteSeleccionado = tablaDeporte.getSelectionModel().getSelectedItem();
                    if (deporteSeleccionado != null) {
                        DeportesDao dao = new DeportesDao();
                        // borramos el Deporte
                        dao.borrarDeporte(deporteSeleccionado);
                        alertaInformacion("Se ha borrado el deporte seleccionado");
                        cargarTablaDeportes("");
                    }
                });
                // agregamos los items al menu
                contextMenu.getItems().addAll(itemModificar, itemBorrar);
                tablaDeporte.setContextMenu(contextMenu);

                // ponemos evento al TextField del filtrado por nombre
                tfBuscarPorNombre.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        // Este código se ejecutará cuando se presione "Enter" en el TextField.
                        String cadena = tfBuscarPorNombre.getText();
                        cargarTablaDeportes(cadena);
                    }
                });
            }
        });


        /**
         * Evento que se ejecuta cuando se selecciona el radioButton de Equipos.
         * Este evento oculta otras tablas y muestra la tabla de Equipos.
         * Además, carga los datos en la tabla de Equipos, creando un menú contextual con las opciones
         * de "Modificar" y "Eliminar" para los Equipos seleccionados, y permite realizar búsquedas filtradas.
         */
        rbEquipos.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ocultarTablas();
                tablaEquipos.setVisible(true);

                // cargamos los datos de la tabla Equipo
                colIdEquipo.setCellValueFactory(new PropertyValueFactory<Equipo, Integer>("id_equipo"));
                colNombreEquipo.setCellValueFactory(new PropertyValueFactory<Equipo, String>("nombre"));
                colInicialesEquipo.setCellValueFactory(new PropertyValueFactory<Equipo, String>("iniciales"));
                cargarTablaEquipos("");

                // Creamos un menú contextual para la tabla
                ContextMenu contextMenu = new ContextMenu();
                MenuItem itemModificar = new MenuItem("Modificar");
                MenuItem itemBorrar = new MenuItem("Eliminar");

                // Manejamos eventos de clic para las opciones del menú contextual
                itemModificar.setOnAction(e -> {
                    Equipo equipoSeleccionado = tablaEquipos.getSelectionModel().getSelectedItem();
                    if (equipoSeleccionado != null) {
                        EditarEquipoController contr = new EditarEquipoController();
                        // Modificamos el Equipo
                        contr.editarEquipo(equipoSeleccionado);
                        // mensaje una vez de haya modificado
                        cargarTablaEquipos("");
                    }
                });

                itemBorrar.setOnAction(e -> {
                    Equipo equipoSeleccionado = tablaEquipos.getSelectionModel().getSelectedItem();
                    if (equipoSeleccionado != null) {
                        EquiposDao dao = new EquiposDao();
                        // borramos el Equipo
                        dao.borrarEquipo(equipoSeleccionado);
                        alertaInformacion("Se ha borrado el Equipo seleccionado");
                        cargarTablaEquipos("");
                    }
                });
                // agregamos los items al menu
                contextMenu.getItems().addAll(itemModificar, itemBorrar);
                tablaEquipos.setContextMenu(contextMenu);

                // ponemos evento al TextField del filtrado por nombre
                tfBuscarPorNombre.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        // Este código se ejecutará cuando se presione "Enter" en el TextField.
                        String cadena = tfBuscarPorNombre.getText();
                        cargarTablaEquipos(cadena);
                    }
                });
            }
        });


        /**
         * Evento que se ejecuta cuando se selecciona el radioButton de Eventos.
         * Este evento oculta otras tablas y muestra la tabla de Eventos.
         * Además, carga los datos en la tabla de Eventos, creando un menú contextual con las opciones
         * de "Modificar" y "Eliminar" para los Eventos seleccionados, y permite realizar búsquedas filtradas.
         */
        rbEventos.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ocultarTablas();
                tablaEventos.setVisible(true);

                // cargamos los datos de la tabla Evento
                colNombreEvento.setCellValueFactory(new PropertyValueFactory<Evento, String>("nombre"));
                colIdEvento.setCellValueFactory(new PropertyValueFactory<Evento, Integer>("id_evento"));
                colDeporteEvento.setCellValueFactory(new PropertyValueFactory<Evento, String>("nombre_deporte"));
                colOlimpiadaEvento.setCellValueFactory(new PropertyValueFactory<Evento, String>("nombre_olimpiada"));
                cargarTablaEventos("");

                // Creamos un menú contextual para la tabla
                ContextMenu contextMenu = new ContextMenu();
                MenuItem itemModificar = new MenuItem("Modificar");
                MenuItem itemBorrar = new MenuItem("Eliminar");

                // Manejamos eventos de clic para las opciones del menú contextual
                itemModificar.setOnAction(e -> {
                    Evento eventoSeleccionado = tablaEventos.getSelectionModel().getSelectedItem();
                    if (eventoSeleccionado != null) {
                        EditarEventoController contr = new EditarEventoController();
                        // Modificamos el Evento
                        contr.editarEvento(eventoSeleccionado);
                        // mensaje una vez de haya modificado
                        cargarTablaEquipos("");
                    }
                });

                itemBorrar.setOnAction(e -> {
                    Evento eventoSeleccionado = tablaEventos.getSelectionModel().getSelectedItem();
                    if (eventoSeleccionado != null) {
                        EventosDao dao = new EventosDao();
                        // borramos el Evento
                        dao.borrarEvento(eventoSeleccionado);
                        alertaInformacion("Se ha borrado el Evento seleccionado");
                        cargarTablaEventos("");
                    }
                });
                // agregamos los items al menu
                contextMenu.getItems().addAll(itemModificar, itemBorrar);
                tablaEventos.setContextMenu(contextMenu);

                // ponemos evento al TextField del filtrado por nombre
                tfBuscarPorNombre.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        // Este código se ejecutará cuando se presione "Enter" en el TextField.
                        String cadena = tfBuscarPorNombre.getText();
                        cargarTablaEventos(cadena);
                    }
                });
            }
        });


        /**
         * Evento que se ejecuta cuando se selecciona el radioButton de Olimpiadas.
         * Este evento oculta otras tablas y muestra la tabla de Olimpiadas.
         * Además, carga los datos en la tabla de Olimpiadas, creando un menú contextual con las opciones
         * de "Modificar" y "Eliminar" para las Olimpiadas seleccionadas, y permite realizar búsquedas filtradas.
         */
        rbOlimpiadas.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ocultarTablas();
                tablaOlimpiadas.setVisible(true);

                // cargamos los datos de la tabla Evento
                colNombreOlimpiada.setCellValueFactory(new PropertyValueFactory<Olimpiada, String>("nombre"));
                colAnioOlimpiada.setCellValueFactory(new PropertyValueFactory<Olimpiada, Integer>("anio"));
                colIdOlimpiada.setCellValueFactory(new PropertyValueFactory<Olimpiada, Integer>("id_olimpiada"));
                colCiudadOlimpiada.setCellValueFactory(new PropertyValueFactory<Olimpiada, String>("ciudad"));
                colTemporada.setCellValueFactory(new PropertyValueFactory<Olimpiada, String>("temporada"));
                cargarTablaOlimpiadas("");

                // Creamos un menú contextual para la tabla
                ContextMenu contextMenu = new ContextMenu();
                MenuItem itemModificar = new MenuItem("Modificar");
                MenuItem itemBorrar = new MenuItem("Eliminar");

                // Manejamos eventos de clic para las opciones del menú contextual
                itemModificar.setOnAction(e -> {
                    Olimpiada olimpiadaSeleccionada = tablaOlimpiadas.getSelectionModel().getSelectedItem();
                    if (olimpiadaSeleccionada != null) {
                        EditarOlimpiadaController contr = new EditarOlimpiadaController();
                        // Modificamos la Olimpiada
                        contr.editarOlimpiada(olimpiadaSeleccionada);
                        // mensaje una vez de haya modificado
                        cargarTablaEquipos("");
                    }
                });

                itemBorrar.setOnAction(e -> {
                    Olimpiada olimpiadaSeleccionada = tablaOlimpiadas.getSelectionModel().getSelectedItem();
                    if (olimpiadaSeleccionada != null) {
                        OlimpiadasDao dao = new OlimpiadasDao();
                        // borramos la Olimpiada
                        dao.borrarOlimpiada(olimpiadaSeleccionada);
                        alertaInformacion("Se ha borrado la olimpiada seleccionado");
                        cargarTablaOlimpiadas("");
                    }
                });
                // agregamos los items al menu
                contextMenu.getItems().addAll(itemModificar, itemBorrar);
                tablaOlimpiadas.setContextMenu(contextMenu);

                // ponemos evento al TextField del filtrado por nombre
                tfBuscarPorNombre.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        // Este código se ejecutará cuando se presione "Enter" en el TextField.
                        String cadena = tfBuscarPorNombre.getText();
                        cargarTablaOlimpiadas(cadena);
                    }
                });
            }
        });


        /**
         * Evento que se ejecuta cuando se selecciona el radioButton de Participaciones.
         * Este evento oculta otras tablas y muestra la tabla de Participaciones.
         * Además, carga los datos en la tabla de Participaciones, creando un menú contextual con las opciones
         * de "Modificar" y "Eliminar" para las participaciones seleccionadas, y permite realizar búsquedas filtradas.
         */
        rbParticipaciones.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ocultarTablas();
                tablaParticipaciones.setVisible(true);
                // cargamos los datos de la tabla Participacion
                colDeportistaParticipacion.setCellValueFactory(new PropertyValueFactory<Participacion, String>("deportista"));
                colEdadParticipacion.setCellValueFactory(new PropertyValueFactory<Participacion, Integer>("edad"));
                colEquipoParticipacion.setCellValueFactory(new PropertyValueFactory<Participacion, String>("equipo"));
                colEventoParticipacion.setCellValueFactory(new PropertyValueFactory<Participacion, String>("evento"));
                colMedallaParticipacion.setCellValueFactory(new PropertyValueFactory<Participacion, String>("medalla"));
                // en caso de que existan personas en la base de datos, las cargamos en la tabla
                cargarTablaParticipaciones("");

                // Creamos un menú contextual para la tabla
                ContextMenu contextMenu = new ContextMenu();
                MenuItem itemModificar = new MenuItem("Modificar");
                MenuItem itemBorrar = new MenuItem("Eliminar");

                // Manejamos eventos de clic para las opciones del menú contextual
                itemModificar.setOnAction(e -> {
                    Participacion participacionSeleccionada = tablaParticipaciones.getSelectionModel().getSelectedItem();
                    if (participacionSeleccionada != null) {
                        EditarParticipacionController contr = new EditarParticipacionController();
                        // Modificamos la Participacion
                        contr.editarParticipacion(participacionSeleccionada);
                        // mensaje una vez de haya modificado
                        cargarTablaEquipos("");
                    }
                });

                itemBorrar.setOnAction(e -> {
                    Participacion participacionSeleccionada = tablaParticipaciones.getSelectionModel().getSelectedItem();
                    if (participacionSeleccionada != null) {
                        ParticipacionesDao dao = new ParticipacionesDao();
                        // borramos la Participacion
                        dao.borrarParticipacion(participacionSeleccionada);
                        // mensaje una vez de haya modificado
                        alertaInformacion("Se ha borrado la participacion seleccionada");
                        cargarTablaParticipaciones("");
                    }
                });
                // agregamos los items al menu
                contextMenu.getItems().addAll(itemModificar, itemBorrar);
                tablaParticipaciones.setContextMenu(contextMenu);

                // ponemos evento al TextField del filtrado por nombre
                tfBuscarPorNombre.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        // Este código se ejecutará cuando se presione "Enter" en el TextField.
                        String cadena = tfBuscarPorNombre.getText();
                        cargarTablaParticipaciones(cadena);
                    }
                });
            }
        });
    }

    /**
     * Método que oculta todas las tablas del sistema.
     * Este método establece la visibilidad de todas las tablas relacionadas con los diferentes módulos
     * (Deportistas, Deporte, Equipos, Eventos, Olimpiadas, Participaciones) en "false",
     * lo que hace que no se muestren en la interfaz gráfica.
     */
    private void ocultarTablas() {
        tablaDeportistas.setVisible(false);
        tablaDeporte.setVisible(false);
        tablaEquipos.setVisible(false);
        tablaEventos.setVisible(false);
        tablaOlimpiadas.setVisible(false);
        tablaParticipaciones.setVisible(false);
    }

    /**
     * Método que muestra una alerta de información con un botón para cerrarla.
     * Este método crea y muestra una ventana emergente de tipo información con un mensaje especificado,
     * y un botón para ocultar la ventana cuando el usuario haga clic en él.
     *
     * @param mensaje El mensaje que se mostrará en la ventana emergente de información.
     */
    private void alertaInformacion(String mensaje) {
        // Alerta de informacion con boton
        Alert ventanaEmergente = new Alert(AlertType.INFORMATION);
        ventanaEmergente.setTitle("info");
        ventanaEmergente.setContentText(mensaje);
        // Creamos el botón para ocultar la alerta
        Button ocultarBtn = new Button("Aceptar");
        ocultarBtn.setOnAction(e -> {
            // Acción al hacer clic en el botón: cerrar la ventana emergente
            ventanaEmergente.hide();
        });
        ventanaEmergente.show();
    }



    /* 	**************************************************************************************************************************************************
     **************************************************************************************************************************************************
     *								DEPORTISTAS
     **************************************************************************************************************************************************
     **************************************************************************************************************************************************
     */


    /**
     * Columna de la tabla para mostrar la altura de un deportista.
     * Esta columna está asociada con la propiedad 'altura' de la clase Deportista, y muestra un valor entero.
     */
    @FXML
    private TableColumn<Deportista, Integer> colAlturaDeportista;

    /**
     * Columna de la tabla para mostrar el nombre de un deportista.
     * Esta columna está asociada con la propiedad 'nombre' de la clase Deportista, y muestra un valor de tipo String.
     */
    @FXML
    private TableColumn<Deportista, String> colNombreDeportista;

    /**
     * Columna de la tabla para mostrar el ID de un deportista.
     * Esta columna está asociada con la propiedad 'id_deportista' de la clase Deportista, y muestra un valor entero.
     */
    @FXML
    private TableColumn<Deportista, Integer> colIdDeportista;

    /**
     * Columna de la tabla para mostrar el peso de un deportista.
     * Esta columna está asociada con la propiedad 'peso' de la clase Deportista, y muestra un valor entero.
     */
    @FXML
    private TableColumn<Deportista, Integer> colPesoDeportista;

    /**
     * Columna de la tabla para mostrar el sexo de un deportista.
     * Esta columna está asociada con la propiedad 'sexo' de la clase Deportista, y muestra un valor de tipo String.
     */
    @FXML
    private TableColumn<Deportista, String> colSexoDeportista;

    /**
     * Columna de la tabla para mostrar la foto de un deportista.
     * Esta columna está asociada con la propiedad 'foto' de la clase Deportista, y muestra un valor de tipo String.
     */
    @FXML
    private TableColumn<Deportista, String> colFotoDeportista;

    /**
     * Tabla de deportistas que se visualiza en la interfaz.
     * Esta tabla muestra la lista de deportistas y permite interactuar con los datos asociados.
     */
    @FXML
    private TableView<Deportista> tablaDeportistas;

    /**
     * Acción que se ejecuta al hacer clic en el botón para añadir un nuevo deportista.
     * Este método abre una nueva ventana (stage) para permitir la entrada de datos de un nuevo deportista.
     *
     * @param event El evento que desencadena la acción, en este caso un clic en el botón.
     */
    @FXML
    void accionAniadirDeportista(ActionEvent event) {
        try {
            // Abre la ventana para añadir un nuevo Deportista
            Stage primaryStage = new Stage();
            GridPane root = (GridPane) FXMLLoader.load(getClass().getResource("/es/guillearana/proyecto1/aniadirDeportista.fxml"));
            Scene scene = new Scene(root);
            primaryStage.setTitle("AÑADIR DEPORTISTA");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            // Maneja cualquier excepción que pueda ocurrir al abrir la ventana
            e.printStackTrace();
        }
    }

    /**
     * Carga los deportistas en la tabla según la cadena de búsqueda.
     * Este método consulta la base de datos o la fuente de datos de deportistas
     * y actualiza la tabla con los resultados que coinciden con la cadena de búsqueda.
     *
     * @param cadena La cadena que se utilizará para filtrar los deportistas a cargar.
     */
    private void cargarTablaDeportistas(String cadena) {
        try {
            // Carga la tabla de Deportistas con la cadena de búsqueda
            DeportistasDao deportistaDao = new DeportistasDao();
            ObservableList<Deportista> listaDeportista = deportistaDao.cargarDeportista(cadena);
            tablaDeportistas.setItems(listaDeportista);
            tablaDeportistas.refresh();
        } catch (Exception e) {
            // Maneja cualquier excepción que pueda ocurrir durante la carga
            e.printStackTrace();
        }
    }


    /* 	**************************************************************************************************************************************************
     **************************************************************************************************************************************************
     *								DEPORTES
     **************************************************************************************************************************************************
     **************************************************************************************************************************************************
     */


    /**
     * Tabla de deportes que se visualiza en la interfaz.
     * Esta tabla muestra la lista de deportes y permite interactuar con los datos asociados.
     */
    @FXML
    private TableView<Deporte> tablaDeporte;

    /**
     * Columna de la tabla para mostrar el nombre de un deporte.
     * Esta columna está asociada con la propiedad 'nombre' de la clase Deporte, y muestra un valor de tipo String.
     */
    @FXML
    private TableColumn<Deporte, String> colNombreDeporte;

    /**
     * Columna de la tabla para mostrar el ID de un deporte.
     * Esta columna está asociada con la propiedad 'id_deporte' de la clase Deporte, y muestra un valor entero.
     */
    @FXML
    private TableColumn<Deporte, Integer> colIdDeporte;

    /**
     * Acción que se ejecuta al hacer clic en el botón para añadir un nuevo deporte.
     * Este método abre una nueva ventana (stage) para permitir la entrada de datos de un nuevo deporte.
     *
     * @param event El evento que desencadena la acción, en este caso un clic en el botón.
     */
    @FXML
    void accionAniadirDeporte(ActionEvent event) {
        try {
            // Abre la ventana para añadir un nuevo Deporte
            Stage primaryStage = new Stage();
            GridPane root = (GridPane) FXMLLoader.load(getClass().getResource("/es/guillearana/proyecto1/aniadirDeporte.fxml"));
            Scene scene = new Scene(root);
            primaryStage.setTitle("AÑADIR DEPORTE");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            // Maneja cualquier excepción que pueda ocurrir al abrir la ventana
            e.printStackTrace();
        }
    }

    /**
     * Carga los deportes en la tabla según la cadena de búsqueda.
     * Este método consulta la base de datos o la fuente de datos de deportes
     * y actualiza la tabla con los resultados que coinciden con la cadena de búsqueda.
     *
     * @param cadena La cadena que se utilizará para filtrar los deportes a cargar.
     */
    void cargarTablaDeportes(String cadena) {
        try {
            // Carga la tabla de Deportes con la cadena de búsqueda
            DeportesDao deportesDao = new DeportesDao();
            ObservableList<Deporte> listaDeportes = deportesDao.cargarDeportes(cadena);
            tablaDeporte.setItems(listaDeportes);
            tablaDeporte.refresh();
        } catch (Exception e) {
            // Maneja cualquier excepción que pueda ocurrir durante la carga
            e.printStackTrace();
        }
    }


    /* 	**************************************************************************************************************************************************
     **************************************************************************************************************************************************
     *								EVENTOS
     **************************************************************************************************************************************************
     **************************************************************************************************************************************************
     */


    /**
     * Columna de la tabla para mostrar el nombre de un evento.
     * Esta columna está asociada con la propiedad 'nombre' de la clase Evento, y muestra un valor de tipo String.
     */
    @FXML
    private TableColumn<Evento, String> colNombreEvento;

    /**
     * Columna de la tabla para mostrar el deporte relacionado con un evento.
     * Esta columna está asociada con la propiedad 'deporte' de la clase Evento, y muestra un valor de tipo String.
     */
    @FXML
    private TableColumn<Evento, String> colDeporteEvento;

    /**
     * Columna de la tabla para mostrar el ID de un evento.
     * Esta columna está asociada con la propiedad 'id_evento' de la clase Evento, y muestra un valor entero.
     */
    @FXML
    private TableColumn<Evento, Integer> colIdEvento;

    /**
     * Tabla de eventos que se visualiza en la interfaz.
     * Esta tabla muestra la lista de eventos y permite interactuar con los datos asociados.
     */
    @FXML
    private TableView<Evento> tablaEventos;

    /**
     * Columna de la tabla para mostrar la Olimpiada relacionada con un evento.
     * Esta columna está asociada con la propiedad 'olimpiada' de la clase Evento, y muestra un valor de tipo String.
     */
    @FXML
    private TableColumn<Evento, String> colOlimpiadaEvento;

    /**
     * Acción que se ejecuta al hacer clic en el botón para añadir un nuevo evento.
     * Este método abre una nueva ventana (stage) para permitir la entrada de datos de un nuevo evento.
     *
     * @param event El evento que desencadena la acción, en este caso un clic en el botón.
     */
    @FXML
    void accionAniadirEvento(ActionEvent event) {
        try {
            // Abre la ventana para añadir un nuevo Evento
            Stage primaryStage = new Stage();
            GridPane root = (GridPane) FXMLLoader.load(getClass().getResource("/es/guillearana/proyecto1/aniadirEvento.fxml"));
            Scene scene = new Scene(root);
            primaryStage.setTitle("AÑADIR EVENTO");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            // Maneja cualquier excepción que pueda ocurrir al abrir la ventana
            e.printStackTrace();
        }
    }

    /**
     * Carga los eventos en la tabla según la cadena de búsqueda.
     * Este método consulta la base de datos o la fuente de datos de eventos
     * y actualiza la tabla con los resultados que coinciden con la cadena de búsqueda.
     *
     * @param cadena La cadena que se utilizará para filtrar los eventos a cargar.
     */
    void cargarTablaEventos(String cadena) {
        try {
            // Carga la tabla de Eventos con la cadena de búsqueda
            EventosDao eventosDao = new EventosDao();
            ObservableList<Evento> listaEventos = eventosDao.cargarEventos(cadena);
            tablaEventos.setItems(listaEventos);
            tablaEventos.refresh();
        } catch (Exception e) {
            // Maneja cualquier excepción que pueda ocurrir durante la carga
            e.printStackTrace();
        }
    }


    /* 	**************************************************************************************************************************************************
     **************************************************************************************************************************************************
     *								EQUIPOS
     **************************************************************************************************************************************************
     **************************************************************************************************************************************************
     */


    /**
     * Tabla de equipos que se visualiza en la interfaz.
     * Esta tabla muestra la lista de equipos y permite interactuar con los datos asociados.
     */
    @FXML
    private TableView<Equipo> tablaEquipos;

    /**
     * Columna de la tabla para mostrar el ID de un equipo.
     * Esta columna está asociada con la propiedad 'id_equipo' de la clase Equipo, y muestra un valor entero.
     */
    @FXML
    private TableColumn<Equipo, Integer> colIdEquipo;

    /**
     * Columna de la tabla para mostrar las iniciales de un equipo.
     * Esta columna está asociada con la propiedad 'iniciales' de la clase Equipo, y muestra un valor de tipo String.
     */
    @FXML
    private TableColumn<Equipo, String> colInicialesEquipo;

    /**
     * Columna de la tabla para mostrar el nombre de un equipo.
     * Esta columna está asociada con la propiedad 'nombre' de la clase Equipo, y muestra un valor de tipo String.
     */
    @FXML
    private TableColumn<Equipo, String> colNombreEquipo;

    /**
     * Acción que se ejecuta al hacer clic en el botón para añadir un nuevo equipo.
     * Este método abre una nueva ventana (stage) para permitir la entrada de datos de un nuevo equipo.
     *
     * @param event El evento que desencadena la acción, en este caso un clic en el botón.
     */
    @FXML
    void accionAniadirEquipo(ActionEvent event) {
        try {
            // Abre la ventana para añadir un nuevo Equipo
            Stage primaryStage = new Stage();
            GridPane root = (GridPane) FXMLLoader.load(getClass().getResource("/es/guillearana/proyecto1/aniadirEquipo.fxml"));
            Scene scene = new Scene(root);
            primaryStage.setTitle("AÑADIR EQUIPO");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            // Maneja cualquier excepción que pueda ocurrir al abrir la ventana
            e.printStackTrace();
        }
    }

    /**
     * Carga los equipos en la tabla según la cadena de búsqueda.
     * Este método consulta la base de datos o la fuente de datos de equipos
     * y actualiza la tabla con los resultados que coinciden con la cadena de búsqueda.
     *
     * @param cadena La cadena que se utilizará para filtrar los equipos a cargar.
     */
    void cargarTablaEquipos(String cadena) {
        try {
            // Carga la tabla de Equipos con la cadena de búsqueda
            EquiposDao equiposDao = new EquiposDao();
            ObservableList<Equipo> listaEquipos = equiposDao.cargarEquipos(cadena);
            tablaEquipos.setItems(listaEquipos);
            tablaDeportistas.refresh(); // Refresca la tabla de deportistas, aunque esto parece un error
        } catch (Exception e) {
            // Maneja cualquier excepción que pueda ocurrir durante la carga
            e.printStackTrace();
        }
    }


    /* 	**************************************************************************************************************************************************
     **************************************************************************************************************************************************
     *								OLIMPIADAS
     **************************************************************************************************************************************************
     **************************************************************************************************************************************************
     */


    /**
     * Columna de la tabla para mostrar el nombre de la Olimpiada.
     * Esta columna está asociada con la propiedad 'nombre' de la clase Olimpiada, y muestra un valor de tipo String.
     */
    @FXML
    private TableColumn<Olimpiada, String> colNombreOlimpiada;

    /**
     * Columna de la tabla para mostrar el año de la Olimpiada.
     * Esta columna está asociada con la propiedad 'anio' de la clase Olimpiada, y muestra un valor de tipo Integer.
     */
    @FXML
    private TableColumn<Olimpiada, Integer> colAnioOlimpiada;

    /**
     * Columna de la tabla para mostrar el ID de la Olimpiada.
     * Esta columna está asociada con la propiedad 'id_olimpiada' de la clase Olimpiada, y muestra un valor de tipo Integer.
     */
    @FXML
    private TableColumn<Olimpiada, Integer> colIdOlimpiada;

    /**
     * Columna de la tabla para mostrar la temporada de la Olimpiada.
     * Esta columna está asociada con la propiedad 'temporada' de la clase Olimpiada, y muestra un valor de tipo String.
     */
    @FXML
    private TableColumn<Olimpiada, String> colTemporada;

    /**
     * Tabla de Olimpiadas que se visualiza en la interfaz.
     * Esta tabla muestra la lista de olimpiadas y permite interactuar con los datos asociados.
     */
    @FXML
    private TableView<Olimpiada> tablaOlimpiadas;

    /**
     * Columna de la tabla para mostrar la ciudad anfitriona de la Olimpiada.
     * Esta columna está asociada con la propiedad 'ciudad' de la clase Olimpiada, y muestra un valor de tipo String.
     */
    @FXML
    private TableColumn<Olimpiada, String> colCiudadOlimpiada;

    /**
     * Carga las olimpiadas en la tabla según la cadena de búsqueda.
     * Este método consulta la base de datos o la fuente de datos de olimpiadas
     * y actualiza la tabla con los resultados que coinciden con la cadena de búsqueda.
     *
     * @param cadena La cadena que se utilizará para filtrar las olimpiadas a cargar.
     */
    void cargarTablaOlimpiadas(String cadena) {
        try {
            // Carga la tabla de Olimpiadas con la cadena de búsqueda
            OlimpiadasDao olimpiadasDao = new OlimpiadasDao();
            ObservableList<Olimpiada> listaOlimpiadas = olimpiadasDao.cargarOlimpiadas(cadena);
            tablaOlimpiadas.setItems(listaOlimpiadas);
            tablaOlimpiadas.refresh();
        } catch (Exception e) {
            // Maneja cualquier excepción que pueda ocurrir durante la carga
            e.printStackTrace();
        }
    }

    /**
     * Acción que se ejecuta al hacer clic en el botón para añadir una nueva Olimpiada.
     * Este método abre una nueva ventana (stage) para permitir la entrada de datos de una nueva olimpiada.
     *
     * @param event El evento que desencadena la acción, en este caso un clic en el botón.
     */
    @FXML
    void accionAniadirOlimpiada(ActionEvent event) {
        try {
            // Abre la ventana para añadir una Olimpiada
            Stage primaryStage = new Stage();
            GridPane root = (GridPane) FXMLLoader.load(getClass().getResource("/es/guillearana/proyecto1/aniadirOlimpiada.fxml"));
            Scene scene = new Scene(root);
            primaryStage.setTitle("AÑADIR OLIMPIADA");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            // Maneja cualquier excepción que pueda ocurrir al abrir la ventana
            e.printStackTrace();
        }
    }


    /* 	**************************************************************************************************************************************************
     **************************************************************************************************************************************************
     *								PARTICIPACION
     **************************************************************************************************************************************************
     **************************************************************************************************************************************************
     */


    /**
     * Tabla de Participaciones que se visualiza en la interfaz.
     * Esta tabla muestra la lista de participaciones y permite interactuar con los datos asociados.
     */

    @FXML
    private TableView<Participacion> tablaParticipaciones;

    /**
     * Columna de la tabla para mostrar el nombre del deportista en la participación.
     * Esta columna está asociada con la propiedad 'deportista' de la clase Participacion, y muestra un valor de tipo String.
     */

    @FXML
    private TableColumn<Participacion, String> colDeportistaParticipacion;

    /**
     * Columna de la tabla para mostrar la edad del deportista en la participación.
     * Esta columna está asociada con la propiedad 'edad' de la clase Participacion, y muestra un valor de tipo Integer.
     */

    @FXML
    private TableColumn<Participacion, Integer> colEdadParticipacion;

    /**
     * Columna de la tabla para mostrar el nombre del equipo en la participación.
     * Esta columna está asociada con la propiedad 'equipo' de la clase Participacion, y muestra un valor de tipo String.
     */

    @FXML
    private TableColumn<Participacion, String> colEquipoParticipacion;

    /**
     * Columna de la tabla para mostrar el evento en la participación.
     * Esta columna está asociada con la propiedad 'evento' de la clase Participacion, y muestra un valor de tipo String.
     */
    @FXML
    private TableColumn<Participacion, String> colEventoParticipacion;

    /**
     * Columna de la tabla para mostrar la medalla obtenida por el deportista en la participación.
     * Esta columna está asociada con la propiedad 'medalla' de la clase Participacion, y muestra un valor de tipo String.
     */

    @FXML
    private TableColumn<Participacion, String> colMedallaParticipacion;

    /**
     * Carga las participaciones en la tabla según la cadena de búsqueda.
     * Este método consulta la base de datos o la fuente de datos de participaciones
     * y actualiza la tabla con los resultados que coinciden con la cadena de búsqueda.
     *
     * @param cadena La cadena que se utilizará para filtrar las participaciones a cargar.
     */
    void cargarTablaParticipaciones(String cadena) {
        try {
            // Carga la tabla de Participaciones con la cadena de búsqueda
            ParticipacionesDao participacionesDao = new ParticipacionesDao();
            ObservableList<Participacion> listaParticipaciones = participacionesDao.cargarParticipaciones(cadena);
            tablaParticipaciones.setItems(listaParticipaciones);
            tablaParticipaciones.refresh();
        } catch (Exception e) {
            // Maneja cualquier excepción que pueda ocurrir durante la carga
            e.printStackTrace();
        }
    }

    /**
     * Acción que se ejecuta al hacer clic en el botón para añadir una nueva Participación.
     * Este método abre una nueva ventana (stage) para permitir la entrada de datos de una nueva participación.
     *
     * @param event El evento que desencadena la acción, en este caso un clic en el botón.
     */
    @FXML
    void accionAniadirParticipacion(ActionEvent event) {
        try {
            // Abre la ventana para añadir una Participacion
            Stage primaryStage = new Stage();
            GridPane root = (GridPane) FXMLLoader.load(getClass().getResource("/es/guillearana/proyecto1/aniadirParticipacion.fxml"));
            Scene scene = new Scene(root);
            primaryStage.setTitle("AÑADIR PARTICIPACION");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            // Maneja cualquier excepción que pueda ocurrir al abrir la ventana
            e.printStackTrace();
        }
    }
}
