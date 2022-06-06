package model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Access(AccessType.FIELD)
@Table(name = "jugadores")
public class Jugadores implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_jugadores")
    int id;
    @Column(name = "nom",length = 20)
    String nom;

    @ManyToOne
    @JoinColumn(name = "equiposjuegan")
    Equipos equipo;

    @Column(name = "nacionalitat", length = 12)
    String nationality;
    @Column(name = "any_naixement")
    Integer birthYear;


    public Jugadores() {
    }

    public Jugadores(String name, Integer birthYear, String nationality , Equipos equipos) {
        super();
        this.nom = name;
        this.equipo = equipos;
        this.nationality = nationality;
        this.birthYear = birthYear;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Equipos getEquipo() {
        return equipo;
    }

    public void setEquipo(Equipos equipo) {
        this.equipo = equipo;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public Integer getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(Integer birthYear) {
        this.birthYear = birthYear;
    }

    @Override
    public String toString() {
        return "Jugadores{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", equipo=" + equipo +
                ", nationality='" + nationality + '\'' +
                ", birthYear=" + birthYear +
                '}';
    }
}
