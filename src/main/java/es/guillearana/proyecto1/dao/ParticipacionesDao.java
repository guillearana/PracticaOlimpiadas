package es.guillearana.proyecto1.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import es.guillearana.proyecto1.conexion.ConexionBD;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import es.guillearana.proyecto1.model.Participacion;

/**
 * Clase encargada de realizar las operaciones de acceso a datos para la entidad Participacion.
 * Contiene métodos para cargar, añadir, editar y eliminar participaciones en la base de datos.
 */
public class ParticipacionesDao {
    private ConexionBD conexion;

    /**
     * Carga las participaciones que coinciden con la cadena de búsqueda proporcionada.
     *
     * @param cadena La cadena utilizada para filtrar las participaciones a cargar.
     * @return Una lista observable de participaciones que coinciden con la búsqueda.
     */
    public ObservableList<Participacion> cargarParticipaciones(String cadena)  {
        ObservableList<Participacion> listaParticipaciones = FXCollections.observableArrayList();
        try {
            conexion = new ConexionBD();
            String consulta = "select Deportista.nombre, Participacion.id_deportista ,Participacion.id_evento, Evento.nombre, Equipo.nombre, Participacion.edad, Participacion.medalla "
                    + "from Evento, Deportista, Equipo, Participacion "
                    + "WHERE Deportista.nombre LIKE '%"+cadena+"%' "
                    + "AND Deportista.id_deportista = Participacion.id_deportista "
                    + "AND Evento.id_evento = Participacion.id_evento "
                    + "AND Equipo.id_equipo = Participacion.id_equipo";

            PreparedStatement pstmt = conexion.getConexion().prepareStatement(consulta);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                // Guardamos todos los datos
                String nombre_deportista = rs.getString("Deportista.nombre");
                int id_deportista = rs.getInt("Participacion.id_deportista");
                int id_evento = rs.getInt("Participacion.id_evento");
                String nombre_evento = rs.getString("Evento.nombre");
                String nombre_equipo = rs.getString("Equipo.nombre");
                int edad = rs.getInt("Participacion.edad");
                String medalla = rs.getString("Participacion.medalla");

                // Creamos la Participacion
                Participacion p = new Participacion(nombre_deportista, id_deportista, id_evento, nombre_evento, nombre_equipo, edad, medalla);
                listaParticipaciones.add(p);
            }
            rs.close();
            conexion.closeConexion();

            return listaParticipaciones;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listaParticipaciones;
    }

    /**
     * Edita los datos de una participación en la base de datos.
     *
     * @param participacion La participación con los nuevos datos a actualizar.
     */
    public void editarParticipacion(Participacion participacion) {
        try{
            conexion = new ConexionBD();
            String consulta = "UPDATE Participacion SET id_equipo = ?, edad = ?, medalla = ? WHERE id_deportista = ? and id_evento = ?";

            PreparedStatement pstmt = conexion.getConexion().prepareStatement(consulta);
            // Establecer los parámetros
            pstmt.setInt(1, participacion.getId_equipo());
            pstmt.setInt(2, participacion.getEdad());
            pstmt.setString(3, participacion.getMedalla());
            pstmt.setInt(4, participacion.getId_deportista());
            pstmt.setInt(5, participacion.getId_evento());

            // Ejecutar la actualización
            pstmt.executeUpdate();
        } catch (SQLException e) {
            // Manejar excepciones de SQL
            e.printStackTrace();
        }
    }

    /**
     * Añade una nueva participación en la base de datos.
     *
     * @param participacion La participación a añadir.
     */
    public void aniadirParticipacion(Participacion participacion) {
        try {
            conexion = new ConexionBD();

            // Añadir en la tabla de Participacion utilizando PreparedStatement
            String consulta = "INSERT INTO Participacion (id_deportista, id_evento, id_equipo, edad, medalla) VALUES (?, ? ,?, ?, ?)";
            try (PreparedStatement pstmt = conexion.getConexion().prepareStatement(consulta)) {
                // Establecer los parámetros
                pstmt.setInt(1, participacion.getId_deportista());
                pstmt.setInt(2, participacion.getId_evento());
                pstmt.setInt(3, participacion.getId_equipo());
                pstmt.setInt(4, participacion.getEdad());
                pstmt.setString(5, participacion.getMedalla());

                // Ejecutar la inserción
                pstmt.executeUpdate();
            }

            conexion.closeConexion();
        } catch (SQLException e) {
            // Manejar excepciones de SQL
            e.printStackTrace();
        }
    }

    /**
     * Elimina una participación de la base de datos.
     *
     * @param a La participación a eliminar.
     */
    public void borrarParticipacion(Participacion a) {
        try {
            conexion = new ConexionBD();

            // Sacamos el id del deportista
            String consulta = "select id_deportista from Deportista where nombre = '"+a.getDeportista().trim()+"'";
            PreparedStatement pstmt = conexion.getConexion().prepareStatement(consulta);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                int id_deportista = rs.getInt("id_deportista");

                // Sacamos el id del equipo de la tabla Equipo
                consulta = "select id_equipo from Equipo where nombre = '"+a.getEquipo()+"'";
                pstmt = conexion.getConexion().prepareStatement(consulta);
                rs = pstmt.executeQuery();
                if (rs.next()) {
                    int id_equipo = rs.getInt("id_equipo");
                    // Borramos la participación
                    consulta = "DELETE FROM Participacion WHERE id_deportista = "+id_deportista+" AND id_evento = "+a.getId_evento()+" AND id_equipo = "+id_equipo;
                    pstmt = conexion.getConexion().prepareStatement(consulta);
                    pstmt.executeUpdate();
                }
            }

            conexion.closeConexion();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
