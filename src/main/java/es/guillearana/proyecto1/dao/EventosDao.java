package es.guillearana.proyecto1.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import es.guillearana.proyecto1.conexion.ConexionBD;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import es.guillearana.proyecto1.model.Evento;

/**
 * Clase que gestiona las operaciones relacionadas con los eventos en la base de datos.
 */
public class EventosDao {
    private ConexionBD conexion;

    /**
     * Carga los eventos de la base de datos que coinciden con la cadena de búsqueda.
     *
     * @param cadena Cadena que se utiliza para filtrar los eventos por nombre.
     * @return Una lista observable de eventos que coinciden con la búsqueda.
     */
    public ObservableList<Evento> cargarEventos(String cadena)  {
        ObservableList<Evento> listaEventos = FXCollections.observableArrayList();
        try {
            conexion = new ConexionBD();
            String consulta = "select Evento.id_evento as id_evento, Evento.nombre as nombre, Olimpiada.nombre as olimpiada, Deporte.nombre as deporte "
                    + "from Evento, Olimpiada, Deporte "
                    + "WHERE Evento.nombre LIKE '%"+cadena+"%' "
                    + "AND Deporte.id_deporte = Evento.id_deporte and Evento.id_olimpiada = Olimpiada.id_olimpiada";

            PreparedStatement pstmt = conexion.getConexion().prepareStatement(consulta);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                // Guardamos todos los datos
                String nombre = rs.getString("nombre");
                int id_evento = rs.getInt("id_evento");
                String olimpiada = rs.getString("olimpiada");
                String deporte = rs.getString("deporte");

                // Creamos el Evento
                Evento e = new Evento(nombre, id_evento, olimpiada, deporte);
                listaEventos.add(e);
            }
            rs.close();
            conexion.closeConexion();

            return listaEventos;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listaEventos;
    }

    /**
     * Edita los detalles de un evento en la base de datos.
     *
     * @param evento El objeto Evento con los nuevos datos.
     */
    public void editarEvento(Evento evento) {
        try {
            conexion = new ConexionBD();

            // editamos la tabla Evento
            String consulta = "UPDATE Evento "
                    + "SET nombre = '"+evento.getNombre()+"', id_olimpiada = '"+evento.getId_olimpiada()+"', id_deporte = "+evento.getId_deporte()+" "
                    + "WHERE id_evento = "+evento.getId_evento();
            PreparedStatement pstmt = conexion.getConexion().prepareStatement(consulta);
            pstmt.executeUpdate();

            conexion.closeConexion();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Añade un nuevo evento a la base de datos.
     *
     * @param evento El objeto Evento que se va a añadir.
     */
    public void aniadirEvento(Evento evento) {
        try {
            conexion = new ConexionBD();

            // añadir en la tabla Evento
            String consulta = "insert into Evento (nombre, id_olimpiada, id_deporte) VALUES ('"+evento.getNombre()+"',"+evento.getId_olimpiada()+","+evento.getId_deporte()+")";
            PreparedStatement pstmt = conexion.getConexion().prepareStatement(consulta);
            pstmt.executeUpdate();

            conexion.closeConexion();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Borra un evento de la base de datos, eliminando también sus participaciones.
     *
     * @param evento El objeto Evento que se va a eliminar.
     */
    public void borrarEvento(Evento evento) {
        try {
            conexion = new ConexionBD();

            // borrar de la tabla Participacion
            String consulta = "DELETE FROM Participacion WHERE id_evento = "+evento.getId_evento();
            PreparedStatement pstmt = conexion.getConexion().prepareStatement(consulta);
            pstmt.executeUpdate();

            // borrar de la tabla Evento
            consulta = "DELETE FROM Evento WHERE id_evento = "+evento.getId_evento();
            pstmt = conexion.getConexion().prepareStatement(consulta);
            pstmt.executeUpdate();

            conexion.closeConexion();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Obtiene el último ID de evento registrado en la base de datos.
     *
     * @return El siguiente ID disponible para un nuevo evento.
     */
    public int ultimoId() {
        try {
            conexion = new ConexionBD();
            String consulta = "select MAX(id_evento) as ID from Evento";
            PreparedStatement pstmt = conexion.getConexion().prepareStatement(consulta);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                int ultimoId = rs.getInt("ID");

                return ultimoId+1;
            }
            rs.close();
            conexion.closeConexion();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
