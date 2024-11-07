package es.guillearana.proyecto1.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import es.guillearana.proyecto1.conexion.ConexionBD;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import es.guillearana.proyecto1.model.Equipo;

public class EquiposDao {
    private ConexionBD conexion;

    public void aniadirEquipo(Equipo equipo) {
        try {
            conexion = new ConexionBD();

            // añadir en la tabla de Deportistas
            String consulta = "insert into Equipo (nombre, iniciales) VALUES ('"+equipo.getNombre()+"', '"+equipo.getIniciales()+"')";
            PreparedStatement pstmt = conexion.getConexion().prepareStatement(consulta);
            pstmt.executeUpdate();

            conexion.closeConexion();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void editarDeporte(Equipo equipo) {
        try {
            conexion = new ConexionBD();

            // editamos la tabla Deportista
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

                // Creamos el Deporte
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
