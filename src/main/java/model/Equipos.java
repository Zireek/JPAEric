package model;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Access(AccessType.FIELD)
@Table(name = "equipos")

public class Equipos implements Serializable {
    @Id
    @Column(name = "equipos",length = 20)
    private String equipos;

    public String getEquipos() {
        return equipos;
    }

    public void setEquipos(String equipos) {
        this.equipos = equipos;
    }

    @Override
    public String toString() {
        return "Equipos{" +
                "equipos='" + equipos + '\'' +
                '}';
    }

    public Equipos(String equipos) {
        this.equipos = equipos;
    }

    public Equipos() {
    }
}
