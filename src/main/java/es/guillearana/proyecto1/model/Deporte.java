package es.guillearana.proyecto1.model;

import java.util.Objects;

public class Deporte {
    int id_deporte;
    String nombre;

    public Deporte(int id_deporte, String nombre) {
        this.id_deporte = id_deporte;
        this.nombre = nombre;
    }

    public Deporte(String nombre) {
        this.nombre = nombre;
    }

    public int getId_deporte() {
        return id_deporte;
    }

    public String getNombre() {
        return nombre;
    }

    @Override
    public String toString() {
        return id_deporte+" - "+nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id_deporte, nombre);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Deporte other = (Deporte) obj;
        return id_deporte == other.id_deporte && Objects.equals(nombre, other.nombre);
    }



}
