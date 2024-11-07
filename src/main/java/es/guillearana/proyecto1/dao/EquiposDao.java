package es.guillearana.proyecto1.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import es.guillearana.proyecto1.conexion.ConexionBD;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import es.guillearana.proyecto1.model.Equipo;

/**
 * Clase que gestiona las operaciones relacionadas con los equipos en la base de datos.
 */
public class EquiposDao {
    private ConexionBD conexion;

    /**
     * Añade un nuevo equipo a la base de datos.
     *
     * @param equipo El objeto Equipo que se va a añadir.
     */
    public void aniadirEquipo(Equipo equipo) {
        try {
            conexion = new ConexionBD();

            // añadir en la tabla de Equipos
            String consulta = "insert into Equipo (nombre, iniciales) VALUES ('"+equipo.getNombre()+"', '"+equipo.getIniciales()+"')";
            PreparedStatement pstmt = conexion.getConexion().prepareStatement(consulta);
            pstmt.executeUpdate();

            conexion.closeConexion();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Edita los detalles de un equipo en la base de datos.
     *
     * @param equipo El objeto Equipo con los nuevos datos.
     */
    public void editarDeporte(Equipo equipo) {
        try {
            conexion = new ConexionBD();

            // editamos la tabla Equipo
            String consulta = "UPDATE Equipo "
                    + "SET nombre = '"+equipo.getNombre()+"', iniciales = '"+equipo.getIniciales()+"' "
                    + "WHERE id_equipo = "+equipo.getId_equipo();
            PreparedStatement pstmt = conexion.getConexion().prepareStatement(consulta);
            pstmt.executeUpdate();

            conexion.closeConexion();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Carga los equipos de la base de datos que coinciden con la cadena de búsqueda.
     *
     * @param cadena Cadena que se utiliza para filtrar los equipos por nombre.
     * @return Una lista observable de equipos que coinciden con la búsqueda.
     */
    public ObservableList<Equipo> cargarEquipos(String cadena)  {
        ObservableList<Equipo> listaEquipos = FXCollections.observableArrayList();
        try {
            conexion = new ConexionBD();
            String consulta = "select * "
                    + "from Equipo "
                    + "WHERE nombre LIKE '%"+cadena+"%'";

            PreparedStatement pstmt = conexion.getConexion().prepareStatement(consulta);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                // Guardamos todos los datos
                int id = rs.getInt("id_equipo");
                String nombre = rs.getString("nombre");
                String iniciales = rs.getString("iniciales");

                // Creamos el Equipo
                Equipo equipo = new Equipo(id, nombre, iniciales);

                listaEquipos.add(equipo);
            }
            rs.close();
            conexion.closeConexion();

            return listaEquipos;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listaEquipos;
    }

    /**
     * Borra un equipo de la base de datos, eliminando también sus participaciones.
     *
     * @param a El objeto Equipo que se va a eliminar.
     */
    public void borrarEquipo(Equipo a) {
        try {
            conexion = new ConexionBD();

            // borrar de la tabla Participacion
            String consulta = "DELETE FROM Participacion WHERE id_equipo = "+a.getId_equipo();
            PreparedStatement pstmt = conexion.getConexion().prepareStatement(consulta);
            pstmt.executeUpdate();

            // borrar de la tabla Equipo
            consulta = "DELETE FROM Equipo WHERE id_equipo = "+a.getId_equipo();
            pstmt = conexion.getConexion().prepareStatement(consulta);
            pstmt.executeUpdate();

            conexion.closeConexion();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Obtiene el último ID de equipo registrado en la base de datos.
     *
     * @return El siguiente ID disponible para un nuevo equipo.
     */
    public int ultimoId() {
        try {
            conexion = new ConexionBD();
            String consulta = "select MAX(id_equipo) as ID from Equipo";
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
