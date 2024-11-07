package es.guillearana.proyecto1.conexion;

import java.io.FileInputStream;
import java.util.Properties;

public abstract class Propiedades{
    private static Properties props = new Properties();
    // Bloque estático para cargar la configuración desde un archivo de propiedades al iniciar la clase
    static {
        try (FileInputStream input = new FileInputStream("configuration.properties")) {
            // Cargamos las propiedades desde el archivo
            props.load(input);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Este método estático se utiliza para obtener el valor asociado a una clave en la configuración
    public static String getValor(String clave) {
        // Obtenemos el valor correspondiente a la clave proporcionada
        String valor = props.getProperty(clave);

        // Si la clave existe y tiene un valor asociado, lo retornamos
        if (valor != null) {
            return valor;
        }

        // En caso de que la clave no exista, lanzamos una excepción RuntimeException
        throw new RuntimeException("La clave solicitada en configuration.properties no está disponible");
    }

}
