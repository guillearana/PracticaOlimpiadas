package es.guillearana.proyecto1.model;

import java.util.Objects;

public class Participacion {
    int id_deportista, id_evento, id_equipo, edad;
    String medalla, deportista, evento, equipo;

    public Participacion(String deportista, int id_deportista, int id_evento, String evento, String equipo, int edad, String medalla) {
        this.deportista = deportista;
        this.id_deportista = id_deportista;
        this.id_evento = id_evento;
        this.evento = evento;
        this.equipo = equipo;
        this.edad = edad;
        this.medalla = medalla;
    }

    public Participacion(int id_deportista, int id_evento, int id_equipo, int edad, String medalla) {
        this.id_deportista = id_deportista;
        this.id_evento = id_evento;
        this.id_equipo = id_equipo;
        this.edad = edad;
        this.medalla = medalla;
    }

    public Participacion(int id_equipo, int edad, String medalla) {
        this.id_equipo = id_equipo;
        this.edad = edad;
        this.medalla = medalla;
    }



    public void setId_deportista(int id_deportista) {
        this.id_deportista = id_deportista;
    }

    public void setId_evento(int id_evento) {
        this.id_evento = id_evento;
    }

    public void setId_equipo(int id_equipo) {
        this.id_equipo = id_equipo;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public void setMedalla(String medalla) {
        this.medalla = medalla;
    }

    public void setDeportista(String deportista) {
        this.deportista = deportista;
    }

    public void setEvento(String evento) {
        this.evento = evento;
    }

    public void setEquipo(String equipo) {
        this.equipo = equipo;
    }

    public String getDeportista() {
        return deportista;
    }

    public String getEvento() {
        return evento;
    }

    public String getEquipo() {
        return equipo;
    }

    public int getId_deportista() {
        return id_deportista;
    }

    public int getId_evento() {
        return id_evento;
    }

    public int getId_equipo() {
        return id_equipo;
    }

    public int getEdad() {
        return edad;
    }

    public String getMedalla() {
        return medalla;
    }

    @Override
    public int hashCode() {
        return Objects.hash(edad, id_deportista, id_equipo, id_evento, medalla);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Participacion other = (Participacion) obj;
        return edad == other.edad && id_deportista == other.id_deportista && id_equipo == other.id_equipo
                && id_evento == other.id_evento && Objects.equals(medalla, other.medalla);
    }

    @Override
    public String toString() {
        return "Participacion [id_deportista=" + id_deportista + ", id_evento=" + id_evento + ", id_equipo=" + id_equipo
                + ", edad=" + edad + ", medalla=" + medalla + ", deportista=" + deportista + ", evento=" + evento
                + ", equipo=" + equipo + "]";
    }




}
