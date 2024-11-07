package es.guillearana.proyecto1.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import es.guillearana.proyecto1.conexion.ConexionBD;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import es.guillearana.proyecto1.model.Deportista;

/**
 * Clase que gestiona las operaciones relacionadas con los deportistas en la base de datos.
 */
public class DeportistasDao {
    private ConexionBD conexion;

    /**
     * Añade un nuevo deportista a la base de datos.
     *
     * @param a El objeto Deportista que se va a añadir.
     */
    public void aniadirDeportista(Deportista a) {
        try {
            conexion = new ConexionBD();

            // añadir en la tabla de Deportistas
            String consulta = "insert into Deportista (nombre, sexo, peso, altura, foto) VALUES ('"+a.getNombre()+"','"+a.getSexo()+"',"+a.getPeso()+","+a.getAltura()+",'"+a.getFoto()+"')";
            PreparedStatement pstmt = conexion.getConexion().prepareStatement(consulta);
            pstmt.executeUpdate();

            conexion.closeConexion();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Borra un deportista de la base de datos, eliminando también sus participaciones.
     *
     * @param a El objeto Deportista que se va a eliminar.
     */
    public void borrarDeportista(Deportista a) {
        try {
            conexion = new ConexionBD();

            // borrar de la tabla Participacion
            String consulta = "DELETE FROM Participacion WHERE id_deportista = "+a.getId_deportista();
            PreparedStatement pstmt = conexion.getConexion().prepareStatement(consulta);
            pstmt.executeUpdate();

            // borrar de la tabla Deportista
            consulta = "DELETE FROM Deportista WHERE id_deportista = "+a.getId_deportista();
            pstmt = conexion.getConexion().prepareStatement(consulta);
            pstmt.executeUpdate();

            conexion.closeConexion();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Edita los detalles de un deportista en la base de datos.
     *
     * @param a El objeto Deportista con los nuevos datos.
     */
    public void editarDeportista(Deportista a) {
        try {
            conexion = new ConexionBD();

            // editamos la tabla Deportista
            String consulta = "UPDATE Deportista "
                    + "SET nombre = '"+a.getNombre()+"', sexo = '"+a.getSexo()+"', peso = "+a.getPeso()+", altura = "+a.getAltura()+" "
                    + "WHERE id_deportista = "+a.getId_deportista();
            PreparedStatement pstmt = conexion.getConexion().prepareStatement(consulta);
            pstmt.executeUpdate();

            conexion.closeConexion();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Carga los deportistas de la base de datos que coinciden con la cadena de búsqueda.
     *
     * @param cadena Cadena que se utiliza para filtrar los deportistas por nombre.
     * @return Una lista observable de deportistas que coinciden con la búsqueda.
     */
    public ObservableList<Deportista> cargarDeportista(String cadena)  {
        ObservableList<Deportista> listaDeportistas = FXCollections.observableArrayList();
        try {
            conexion = new ConexionBD();
            String consulta = "select * "
                    + "from Deportista "
                    + "WHERE nombre LIKE '%"+cadena+"%'";

            PreparedStatement pstmt = conexion.getConexion().prepareStatement(consulta);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                // Guardamos todos los datos
                int id = rs.getInt("id_deportista");
                String nombre = rs.getString("nombre");
                String foto = rs.getString("foto");
                int peso = rs.getInt("peso");
                int altura = rs.getInt("altura");
                String sexo = rs.getString("sexo");

                // Creamos el Deportista
                Deportista a = new Deportista(id, peso, altura, nombre, foto, sexo);
                listaDeportistas.add(a);
            }
            rs.close();
            conexion.closeConexion();

            return listaDeportistas;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listaDeportistas;
    }

    /**
     * Obtiene el último ID de deportista registrado en la base de datos.
     *
     * @return El siguiente ID disponible para un nuevo deportista.
     */
    public int ultimoId() {
        try {
            conexion = new ConexionBD();
            String consulta = "select MAX(id_deportista) as ID from Deportista";
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
