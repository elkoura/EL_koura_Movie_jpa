package entities;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "Role")
public class Role implements Serializable {
    @EmbeddedId
    private RoleId id;

    @ManyToOne
    @MapsId("filmId")
    @JoinColumn(name = "film_id")
    private Film film;

    @ManyToOne
    @MapsId("acteurId")
    @JoinColumn(name = "acteur_id")
    private Acteur acteur;

    private String personnage;

    public Role() {
        // Default constructor
    }

    public Role(Film film, Acteur acteur, String personnage) {
        this.id = new RoleId(film.getIdImdb(), acteur.getIdImdb());
        this.film = film;
        this.acteur = acteur;
        this.personnage = personnage;
    }

    // Getters and setters

    @Override
    public String toString() {
        return "Role [id=" + id + ", film=" + film + ", acteur=" + acteur + ", personnage=" + personnage + "]";
    }

    public RoleId getId() {
        return id;
    }

    public void setId(RoleId id) {
        this.id = id;
    }

    public Film getFilm() {
        return film;
    }

    public void setFilm(Film film) {
        this.film = film;
    }

    public Acteur getActeur() {
        return acteur;
    }

    public void setActeur(Acteur acteur) {
        this.acteur = acteur;
    }

    public String getPersonnage() {
        return personnage;
    }

    public void setPersonnage(String personnage) {
        this.personnage = personnage;
    }
}
