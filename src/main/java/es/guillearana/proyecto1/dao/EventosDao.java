package es.guillearana.proyecto1.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import es.guillearana.proyecto1.conexion.ConexionBD;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import es.guillearana.proyecto1.model.Evento;

public class EventosDao {
    private ConexionBD conexion;

    public ObservableList<Evento> cargarEventos(String cadena)  {
        ObservableList<Evento> listaEquipos = FXCollections.observableArrayList();
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
                listaEquipos.add(e);
            }
            rs.close();
            conexion.closeConexion();

            return listaEquipos;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listaEquipos;
    }

    public void editarEvento(Evento evento) {
        try {
            conexion = new ConexionBD();

            // editamos la tabla Deportista
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

    public void aniadirEvento(Evento evento) {
        try {
            conexion = new ConexionBD();

            // añadir en la tabla de Deportistas
            String consulta = "insert into Evento (nombre, id_olimpiada, id_deporte) VALUES ('"+evento.getNombre()+"',"+evento.getId_olimpiada()+","+evento.getId_deporte()+")";
            PreparedStatement pstmt = conexion.getConexion().prepareStatement(consulta);
            pstmt.executeUpdate();

            conexion.closeConexion();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void borrarEvento(Evento a) {
        try {
            conexion = new ConexionBD();

            // borrar de la tabla Participacion
            String consulta = "DELETE FROM Participacion WHERE id_evento = "+a.getId_evento();
            PreparedStatement pstmt = conexion.getConexion().prepareStatement(consulta);
            pstmt.executeUpdate();

            // borrar de la tabla Evento
            consulta = "DELETE FROM Evento WHERE id_evento = "+a.getId_evento();
            pstmt = conexion.getConexion().prepareStatement(consulta);
            pstmt.executeUpdate();

            conexion.closeConexion();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

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
