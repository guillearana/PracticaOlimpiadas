package es.guillearana.proyecto1.model;

import java.util.Objects;

public class Equipo {
    int id_equipo;
    String nombre, iniciales;

    public Equipo(int id_equipo, String nombre, String iniciales) {
        this.id_equipo = id_equipo;
        this.nombre = nombre;
        this.iniciales = iniciales;
    }

    public Equipo(String nombre, String iniciales) {
        this.nombre = nombre;
        this.iniciales = iniciales;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setIniciales(String iniciales) {
        this.iniciales = iniciales;
    }

    public int getId_equipo() {
        return id_equipo;
    }

    public String getNombre() {
        return nombre;
    }

    public String getIniciales() {
        return iniciales;
    }

    @Override
    public String toString() {
        return id_equipo+"-"+nombre;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id_equipo, iniciales, nombre);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Equipo other = (Equipo) obj;
        return id_equipo == other.id_equipo && Objects.equals(iniciales, other.iniciales)
                && Objects.equals(nombre, other.nombre);
    }


}
