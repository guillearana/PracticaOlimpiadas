package es.guillearana.proyecto1.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import es.guillearana.proyecto1.conexion.ConexionBD;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import es.guillearana.proyecto1.model.Olimpiada;

/**
 * Clase encargada de realizar las operaciones de acceso a datos para la entidad Olimpiada.
 * Contiene métodos para cargar, añadir, editar, eliminar y obtener el último ID de una olimpiada en la base de datos.
 */
public class OlimpiadasDao {
    private ConexionBD conexion;

    /**
     * Carga las olimpiadas que coinciden con la cadena de búsqueda proporcionada.
     *
     * @param cadena La cadena utilizada para filtrar las olimpiadas a cargar.
     * @return Una lista observable de olimpiadas que coinciden con la búsqueda.
     */
    public ObservableList<Olimpiada> cargarOlimpiadas(String cadena)  {
        ObservableList<Olimpiada> listaOlimpiadas = FXCollections.observableArrayList();
        try {
            conexion = new ConexionBD();
            String consulta = "select * "
                    + "from Olimpiada "
                    + "WHERE nombre LIKE '%"+cadena+"%' ";

            PreparedStatement pstmt = conexion.getConexion().prepareStatement(consulta);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                // Guardamos todos los datos
                int id_olimpiada = rs.getInt("id_olimpiada");
                int anio = rs.getInt("anio");
                String nombre = rs.getString("nombre");
                String ciudad = rs.getString("ciudad");
                String temporada = rs.getString("temporada");

                // Creamos la Olimpiada
                Olimpiada o = new Olimpiada(id_olimpiada, anio, nombre, ciudad, temporada);
                listaOlimpiadas.add(o);
            }
            rs.close();
            conexion.closeConexion();

            return listaOlimpiadas;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listaOlimpiadas;
    }

    /**
     * Añade una nueva olimpiada en la base de datos.
     *
     * @param olimpiada La olimpiada a añadir.
     */
    public void aniadirOlimpiada(Olimpiada olimpiada) {
        try {
            conexion = new ConexionBD();

            // Añadir en la tabla de Olimpiada utilizando PreparedStatement
            String consulta = "INSERT INTO Olimpiada (nombre, anio, temporada, ciudad) VALUES (?, ?, ?, ?)";
            try (PreparedStatement pstmt = conexion.getConexion().prepareStatement(consulta)) {
                // Establecer los parámetros
                pstmt.setString(1, olimpiada.getNombre());
                pstmt.setInt(2, olimpiada.getAnio());
                pstmt.setString(3, olimpiada.getTemporada());
                pstmt.setString(4, olimpiada.getCiudad());

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
     * Edita los datos de una olimpiada en la base de datos.
     *
     * @param olimpiada La olimpiada con los nuevos datos a actualizar.
     */
    public void editarOlimpiada(Olimpiada olimpiada) {
        try{
            conexion = new ConexionBD();
            String consulta = "UPDATE Olimpiada SET nombre = ?, anio = ?, temporada = ?, ciudad = ? WHERE id_olimpiada = ?";

            PreparedStatement pstmt = conexion.getConexion().prepareStatement(consulta);
            // Establecer los parámetros
            pstmt.setString(1, olimpiada.getNombre());
            pstmt.setInt(2, olimpiada.getAnio());
            pstmt.setString(3, olimpiada.getTemporada());
            pstmt.setString(4, olimpiada.getCiudad());
            pstmt.setInt(5, olimpiada.getId_olimpiada());

            // Ejecutar la actualización
            pstmt.executeUpdate();
        } catch (SQLException e) {
            // Manejar excepciones de SQL
            e.printStackTrace();
        }
    }

    /**
     * Elimina una olimpiada y los registros asociados a ella de la base de datos.
     * Primero elimina las participaciones y los eventos asociados, luego elimina la olimpiada.
     *
     * @param a La olimpiada a eliminar.
     */
    public void borrarOlimpiada(Olimpiada a) {
        try {
            conexion = new ConexionBD();

            // Borrar de la tabla Participacion
            String consulta = "DELETE FROM Participacion WHERE id_evento IN (SELECT id_evento FROM Evento WHERE id_olimpiada = "+a.getId_olimpiada()+")";
            PreparedStatement pstmt = conexion.getConexion().prepareStatement(consulta);
            pstmt.executeUpdate();

            // Borrar de la tabla Evento
            consulta = "DELETE FROM Evento WHERE id_olimpiada = "+ a.getId_olimpiada();
            pstmt = conexion.getConexion().prepareStatement(consulta);
            pstmt.executeUpdate();

            // Borrar de la tabla Olimpiada
            consulta = "DELETE FROM Olimpiada WHERE id_olimpiada = "+a.getId_olimpiada();
            pstmt = conexion.getConexion().prepareStatement(consulta);
            pstmt.executeUpdate();

            conexion.closeConexion();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Obtiene el próximo ID disponible para insertar una nueva olimpiada.
     * Este ID corresponde al valor máximo actual en la tabla de Olimpiada + 1.
     *
     * @return El próximo ID disponible.
     */
    public int ultimoId() {
        try {
            conexion = new ConexionBD();
            String consulta = "select MAX(id_olimpiada) as ID from Olimpiada";
            PreparedStatement pstmt = conexion.getConexion().prepareStatement(consulta);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                int ultimoId = rs.getInt("ID");

                return ultimoId + 1;
            }
            rs.close();
            conexion.closeConexion();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
