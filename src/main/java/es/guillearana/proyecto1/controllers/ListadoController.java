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

    @FXML
    void initialize() {
        // ponemos evento al radioButon de Deportistas
        rbDeportistas.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ocultarTablas();
                tablaDeportistas.setVisible(true);

                // cargamos los datos de la tabla
                colIdDeportista.setCellValueFactory(new PropertyValueFactory<Deportista,Integer>("id_deportista") );
                colNombreDeportista.setCellValueFactory(new PropertyValueFactory<Deportista,String>("nombre") );
                colSexoDeportista.setCellValueFactory(new PropertyValueFactory<Deportista,String>("sexo") );
                colPesoDeportista.setCellValueFactory(new PropertyValueFactory<Deportista,Integer>("peso") );
                colAlturaDeportista.setCellValueFactory(new PropertyValueFactory<Deportista,Integer>("altura") );
                colFotoDeportista.setCellValueFactory(new PropertyValueFactory<Deportista,String>("foto") );
                cargarTablaDeportistas("");

                // Creamos un menú contextual para la tabla
                ContextMenu contextMenu = new ContextMenu();
                MenuItem itemModificar = new MenuItem("Modificar");
                MenuItem itemBorrar = new MenuItem("Eliminar");

                // Manejamos eventos de clic para las opciones del menú contextual
                itemModificar.setOnAction(e -> {
                    Deportista deportistaSeleccionado = tablaDeportistas.getSelectionModel().getSelectedItem();
                    if(deportistaSeleccionado != null) {
                        EditarDeportistaController contr = new EditarDeportistaController();
                        // Modificamos el deportista
                        contr.editarDeportista(deportistaSeleccionado);
                        // mensaje una vez de haya modificado
                        cargarTablaDeportistas("");
                    }
                });

                itemBorrar.setOnAction(e -> {
                    Deportista deportistaSeleccionado = tablaDeportistas.getSelectionModel().getSelectedItem();
                    if(deportistaSeleccionado!=null) {
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

        // ponemos evento al radioButon de Deporte
        rbDeportes.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ocultarTablas();
                tablaDeporte.setVisible(true);

                // cargamos los datos de la tabla
                colIdDeporte.setCellValueFactory(new PropertyValueFactory<Deporte,Integer>("id_deporte") );
                colNombreDeporte.setCellValueFactory(new PropertyValueFactory<Deporte,String>("nombre") );
                cargarTablaDeportes("");

                // Creamos un menú contextual para la tabla
                ContextMenu contextMenu = new ContextMenu();
                MenuItem itemModificar= new MenuItem("Modificar");
                MenuItem itemBorrar = new MenuItem("Eliminar");

                // Manejamos eventos de clic para las opciones del menú contextual
                itemModificar.setOnAction(e -> {
                    Deporte deporteSeleccionado = tablaDeporte.getSelectionModel().getSelectedItem();
                    if(deporteSeleccionado != null) {
                        EditarDeporteController contr = new EditarDeporteController();
                        // Modificamos el Deporte
                        contr.editarDeporte(deporteSeleccionado);
                        // mensaje una vez de haya modificado
                        cargarTablaDeportes("");
                    }
                });

                itemBorrar.setOnAction(e -> {
                    Deporte deporteSeleccionado = tablaDeporte.getSelectionModel().getSelectedItem();
                    if(deporteSeleccionado!=null) {
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

        // ponemos evento al radioButon de Equipo
        rbEquipos.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ocultarTablas();
                tablaEquipos.setVisible(true);

                // cargamos los datos de la tabla Equipo
                colIdEquipo.setCellValueFactory(new PropertyValueFactory<Equipo,Integer>("id_equipo") );
                colNombreEquipo.setCellValueFactory(new PropertyValueFactory<Equipo,String>("nombre") );
                colInicialesEquipo.setCellValueFactory(new PropertyValueFactory<Equipo,String>("iniciales") );
                cargarTablaEquipos("");

                // Creamos un menú contextual para la tabla
                ContextMenu contextMenu = new ContextMenu();
                MenuItem itemModificar = new MenuItem("Modificar");
                MenuItem itemBorrar = new MenuItem("Eliminar");

                // Manejamos eventos de clic para las opciones del menú contextual
                itemModificar.setOnAction(e -> {
                    Equipo equipoSeleccionado = tablaEquipos.getSelectionModel().getSelectedItem();
                    if(equipoSeleccionado != null) {
                        EditarEquipoController contr = new EditarEquipoController();
                        // Modificamos el Equipo
                        contr.editarEquipo(equipoSeleccionado);
                        // mensaje una vez de haya modificado
                        cargarTablaEquipos("");
                    }
                });

                itemBorrar.setOnAction(e -> {
                    Equipo equipoSeleccionado = tablaEquipos.getSelectionModel().getSelectedItem();
                    if(equipoSeleccionado!=null) {
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

        // ponemos evento al radioButon de Evento
        rbEventos.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ocultarTablas();
                tablaEventos.setVisible(true);

                // cargamos los datos de la tabla Evento
                colNombreEvento.setCellValueFactory(new PropertyValueFactory<Evento, String>("nombre") );
                colIdEvento.setCellValueFactory(new PropertyValueFactory<Evento, Integer>("id_evento") );
                colDeporteEvento.setCellValueFactory(new PropertyValueFactory<Evento, String>("nombre_deporte") );
                colOlimpiadaEvento.setCellValueFactory(new PropertyValueFactory<Evento, String>("nombre_olimpiada") );
                cargarTablaEventos("");

                // Creamos un menú contextual para la tabla
                ContextMenu contextMenu = new ContextMenu();
                MenuItem itemModificar = new MenuItem("Modificar");
                MenuItem itemBorrar = new MenuItem("Eliminar");

                // Manejamos eventos de clic para las opciones del menú contextual
                itemModificar.setOnAction(e -> {
                    Evento eventoSeleccionado = tablaEventos.getSelectionModel().getSelectedItem();
                    if(eventoSeleccionado != null) {
                        EditarEventoController contr = new EditarEventoController();
                        // Modificamos el Evento
                        contr.editarEvento(eventoSeleccionado);
                        // mensaje una vez de haya modificado
                        cargarTablaEquipos("");
                    }
                });

                itemBorrar.setOnAction(e -> {
                    Evento eventoSeleccionado = tablaEventos.getSelectionModel().getSelectedItem();
                    if(eventoSeleccionado!=null) {
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

        // ponemos evento al radioButon de Olimpiada
        rbOlimpiadas.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ocultarTablas();
                tablaOlimpiadas.setVisible(true);
                // cargamos los datos de la tabla Evento
                colNombreOlimpiada.setCellValueFactory(new PropertyValueFactory<Olimpiada, String>("nombre") );
                colAnioOlimpiada.setCellValueFactory(new PropertyValueFactory<Olimpiada, Integer>("anio") );
                colIdOlimpiada.setCellValueFactory(new PropertyValueFactory<Olimpiada, Integer>("id_olimpiada") );
                colCiudadOlimpiada.setCellValueFactory(new PropertyValueFactory<Olimpiada, String>("ciudad") );
                colTemporada.setCellValueFactory(new PropertyValueFactory<Olimpiada, String>("temporada") );
                cargarTablaOlimpiadas("");

                // Creamos un menú contextual para la tabla
                ContextMenu contextMenu = new ContextMenu();
                MenuItem itemModificar = new MenuItem("Modificar");
                MenuItem itemBorrar = new MenuItem("Eliminar");

                // Manejamos eventos de clic para las opciones del menú contextual
                itemModificar.setOnAction(e -> {
                    Olimpiada olimpiadaSeleccionada = tablaOlimpiadas.getSelectionModel().getSelectedItem();
                    if(olimpiadaSeleccionada != null) {
                        EditarOlimpiadaController contr = new EditarOlimpiadaController();
                        // Modificamos la Olimpiada
                        contr.editarOlimpiada(olimpiadaSeleccionada);
                        // mensaje una vez de haya modificado
                        cargarTablaEquipos("");
                    }
                });

                itemBorrar.setOnAction(e -> {
                    Olimpiada olimpiadaSeleccionada = tablaOlimpiadas.getSelectionModel().getSelectedItem();
                    if(olimpiadaSeleccionada!=null) {
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

        // ponemos evento al radioButon de Participacion
        rbParticipaciones.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ocultarTablas();
                tablaParticipaciones.setVisible(true);
                // cargamos los datos de la tabla Participacion
                colDeportistaParticipacion.setCellValueFactory(new PropertyValueFactory<Participacion, String>("deportista") );
                colEdadParticipacion.setCellValueFactory(new PropertyValueFactory<Participacion, Integer>("edad") );
                colEquipoParticipacion.setCellValueFactory(new PropertyValueFactory<Participacion, String>("equipo") );
                colEventoParticipacion.setCellValueFactory(new PropertyValueFactory<Participacion, String>("evento") );
                colMedallaParticipacion.setCellValueFactory(new PropertyValueFactory<Participacion, String>("medalla") );
                // en caso de que existan personas en la base de datos, las cargamos en la tabla
                cargarTablaParticipaciones("");



                // Creamos un menú contextual para la tabla
                ContextMenu contextMenu = new ContextMenu();
                MenuItem itemModificar= new MenuItem("Modificar");
                MenuItem itemBorrar = new MenuItem("Eliminar");

                // Manejamos eventos de clic para las opciones del menú contextual
                itemModificar.setOnAction(e -> {
                    Participacion participacionSeleccionada = tablaParticipaciones.getSelectionModel().getSelectedItem();
                    if(participacionSeleccionada != null) {
                        EditarParticipacionController contr = new EditarParticipacionController();
                        // Modificamos la Participacion
                        contr.editarParticipacion(participacionSeleccionada);
                        // mensaje una vez de haya modificado
                        cargarTablaEquipos("");
                    }
                });

                itemBorrar.setOnAction(e -> {
                    Participacion participacionSeleccionada = tablaParticipaciones.getSelectionModel().getSelectedItem();
                    if(participacionSeleccionada!=null) {
                        ParticipacionesDao dao = new ParticipacionesDao();
                        // borramos la Participacion
                        dao.borrarParticipacion(participacionSeleccionada);
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

    private void ocultarTablas(){
        tablaDeportistas.setVisible(false);
        tablaDeporte.setVisible(false);
        tablaEquipos.setVisible(false);
        tablaEventos.setVisible(false);
        tablaOlimpiadas.setVisible(false);
        tablaParticipaciones.setVisible(false);
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


    /* 	**************************************************************************************************************************************************
     **************************************************************************************************************************************************
     *								DEPORTISTAS
     **************************************************************************************************************************************************
     **************************************************************************************************************************************************
     */

    @FXML
    private TableColumn<Deportista,Integer> colAlturaDeportista;
    @FXML
    private TableColumn<Deportista,String> colNombreDeportista;
    @FXML
    private TableColumn<Deportista,Integer> colIdDeportista;
    @FXML
    private TableColumn<Deportista,Integer> colPesoDeportista;
    @FXML
    private TableColumn<Deportista,String> colSexoDeportista;
    @FXML
    private TableColumn<Deportista,String> colFotoDeportista;
    @FXML
    private TableView<Deportista> tablaDeportistas;

    @FXML
    void accionAniadirDeportista(ActionEvent event) {
        try {
            // Abre la ventana para añadir un nuevo Deportista
            Stage primaryStage = new Stage();
            GridPane root = (GridPane)FXMLLoader.load(getClass().getResource("/fxml/aniadirDeportista.fxml"));
            Scene scene = new Scene(root);
            primaryStage.setTitle("AÑADIR DEPORTISTA");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch(Exception e) {
            // Maneja cualquier excepción que pueda ocurrir al abrir la ventana
            e.printStackTrace();
        }
    }

    private void cargarTablaDeportistas(String cadena) {
        try {
            // Carga la tabla de Deportes con la cadena de búsqueda
            DeportistasDao deportistaDao = new DeportistasDao();
            ObservableList<Deportista> listaDeportista =  deportistaDao.cargarDeportista(cadena);
            tablaDeportistas.setItems(listaDeportista);
            tablaDeportistas.refresh();
        } catch(Exception e) {
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
    @FXML
    private TableView<Deporte> tablaDeporte;
    @FXML
    private TableColumn<Deporte, String> colNombreDeporte;
    @FXML
    private TableColumn<Deporte, Integer> colIdDeporte;

    @FXML
    void accionAniadirDeporte(ActionEvent event) {
        try {
            // Abre la ventana para añadir un nuevo Deporte
            Stage primaryStage = new Stage();
            GridPane root = (GridPane)FXMLLoader.load(getClass().getResource("/fxml/aniadirDeporte.fxml"));
            Scene scene = new Scene(root);
            primaryStage.setTitle("AÑADIR DEPORTE");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch(Exception e) {
            // Maneja cualquier excepción que pueda ocurrir al abrir la ventana
            e.printStackTrace();
        }
    }

    void cargarTablaDeportes(String cadena) {
        try {
            // Carga la tabla de Deportes con la cadena de búsqueda
            DeportesDao deportesDao = new DeportesDao();
            ObservableList<Deporte> listaDeportes =  deportesDao.cargarDeportes(cadena);
            tablaDeporte.setItems(listaDeportes);
            tablaDeporte.refresh();
        } catch(Exception e) {
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
    @FXML
    private TableColumn<Evento, String> colNombreEvento;
    @FXML
    private TableColumn<Evento, String> colDeporteEvento;
    @FXML
    private TableColumn<Evento, Integer> colIdEvento;
    @FXML
    private TableView<Evento> tablaEventos;
    @FXML
    private TableColumn<Evento, String> colOlimpiadaEvento;

    @FXML
    void accionAniadirEvento(ActionEvent event) {
        try {
            // Abre la ventana para añadir un nuevo Evento
            Stage primaryStage = new Stage();
            GridPane root = (GridPane)FXMLLoader.load(getClass().getResource("/fxml/aniadirEvento.fxml"));
            Scene scene = new Scene(root);
            primaryStage.setTitle("AÑADIR EVENTO");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch(Exception e) {
            // Maneja cualquier excepción que pueda ocurrir al abrir la ventana
            e.printStackTrace();
        }
    }

    void cargarTablaEventos(String cadena) {
        try {
            // Carga la tabla de Eventos con la cadena de búsqueda
            EventosDao eventosDao = new EventosDao();
            ObservableList<Evento> listaEventos =  eventosDao.cargarEventos(cadena);
            tablaEventos.setItems(listaEventos);
            tablaEventos.refresh();
        } catch(Exception e) {
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
    @FXML
    private TableView<Equipo> tablaEquipos;
    @FXML
    private TableColumn<Equipo, Integer> colIdEquipo;
    @FXML
    private TableColumn<Equipo, String> colInicialesEquipo;
    @FXML
    private TableColumn<Equipo, String> colNombreEquipo;

    @FXML
    void accionAniadirEquipo(ActionEvent event) {
        try {
            // Abre la ventana para añadir un nuevo Equipo
            Stage primaryStage = new Stage();
            GridPane root = (GridPane)FXMLLoader.load(getClass().getResource("/fxml/aniadirEquipo.fxml"));
            Scene scene = new Scene(root);
            primaryStage.setTitle("AÑADIR EQUIPO");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch(Exception e) {
            // Maneja cualquier excepción que pueda ocurrir al abrir la ventana
            e.printStackTrace();
        }
    }

    void cargarTablaEquipos(String cadena) {
        try {
            // Carga la tabla de Equipos con la cadena de búsqueda
            EquiposDao equiposDao = new EquiposDao();
            ObservableList<Equipo> listaEquipos =  equiposDao.cargarEquipos(cadena);
            tablaEquipos.setItems(listaEquipos);
            tablaDeportistas.refresh();
        } catch(Exception e) {
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
    @FXML
    private TableColumn<Olimpiada, String> colNombreOlimpiada;
    @FXML
    private TableColumn<Olimpiada, Integer> colAnioOlimpiada;
    @FXML
    private TableColumn<Olimpiada, Integer> colIdOlimpiada;
    @FXML
    private TableColumn<Olimpiada, String> colTemporada;
    @FXML
    private TableView<Olimpiada> tablaOlimpiadas;
    @FXML
    private TableColumn<Olimpiada, String> colCiudadOlimpiada;

    void cargarTablaOlimpiadas(String cadena) {
        try {
            // Carga la tabla de Olimpiadas con la cadena de búsqueda
            OlimpiadasDao olimpiadasDao = new OlimpiadasDao();
            ObservableList<Olimpiada> listaOlimpiadas =  olimpiadasDao.cargarOlimpiadas(cadena);
            tablaOlimpiadas.setItems(listaOlimpiadas);
            tablaOlimpiadas.refresh();
        } catch(Exception e) {
            // Maneja cualquier excepción que pueda ocurrir durante la carga
            e.printStackTrace();
        }
    }

    @FXML
    void accionAniadirOlimpiada(ActionEvent event) {
        try {
            // Abre la ventana para añadir una Olimpiada
            Stage primaryStage = new Stage();
            GridPane root = (GridPane)FXMLLoader.load(getClass().getResource("/fxml/aniadirOlimpiada.fxml"));
            Scene scene = new Scene(root);
            primaryStage.setTitle("AÑADIR OLIMPIADA");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch(Exception e) {
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
    @FXML
    private TableView<Participacion> tablaParticipaciones;
    @FXML
    private TableColumn<Participacion, String> colDeportistaParticipacion;
    @FXML
    private TableColumn<Participacion, Integer> colEdadParticipacion;
    @FXML
    private TableColumn<Participacion, String> colEquipoParticipacion;
    @FXML
    private TableColumn<Participacion, String> colEventoParticipacion;
    @FXML
    private TableColumn<Participacion, String> colMedallaParticipacion;

    void cargarTablaParticipaciones(String cadena) {
        try {
            // Carga la tabla de Participaciones con la cadena de búsqueda
            ParticipacionesDao participacionesDao = new ParticipacionesDao();
            ObservableList<Participacion> listaParticipaciones =  participacionesDao.cargarParticipaciones(cadena);
            tablaParticipaciones.setItems(listaParticipaciones);
            tablaParticipaciones.refresh();
        } catch(Exception e) {
            // Maneja cualquier excepción que pueda ocurrir durante la carga
            e.printStackTrace();
        }
    }

    @FXML
    void accionAniadirParticipacion(ActionEvent event) {
        try {
            // Abre la ventana para añadir una Participacion
            Stage primaryStage = new Stage();
            GridPane root = (GridPane)FXMLLoader.load(getClass().getResource("/fxml/aniadirParticipacion.fxml"));
            Scene scene = new Scene(root);
            primaryStage.setTitle("AÑADIR PARTICIPACION");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch(Exception e) {
            // Maneja cualquier excepción que pueda ocurrir al abrir la ventana
            e.printStackTrace();
        }
    }
}
