package es.guillearana.proyecto1.conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.TimeZone;

public class ConexionBD {
	private Connection conexion;

	public ConexionBD() throws SQLException {
		String url = Propiedades.getValor("url") + "?serverTimezone=" + TimeZone.getDefault().getID();
		String user = Propiedades.getValor("user");
		String password = Propiedades.getValor("password");
		// Creamos la conexion
		conexion = DriverManager.getConnection(url, user, password);
		conexion.setAutoCommit(true);
	}
	public Connection getConexion() {
		return conexion;
	}
	public void closeConexion() throws SQLException {
		conexion.close();
	}
}
