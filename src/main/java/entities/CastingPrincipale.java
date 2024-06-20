package entities;

import jakarta.persistence.*;

@Entity
@Table(name = "CastingPrincipale")
public class CastingPrincipale {

    @EmbeddedId
    private CastingPrincipaleId id;

    @ManyToOne
    @MapsId("acteurId")
    @JoinColumn(name = "ACTEUR_ID")
    private Acteur acteur;

    @ManyToOne
    @JoinColumn(name = "FILM_ID", insertable = false, updatable = false)
    private Film film;

    // Constructeur sans arguments
    public CastingPrincipale() {}

    public CastingPrincipaleId getId() {
        return id;
    }

    public void setId(CastingPrincipaleId id) {
        this.id = id;
    }

    public Acteur getActeur() {
        return acteur;
    }

    public void setActeur(Acteur acteur) {
        this.acteur = acteur;
    }

    public Film getFilm() {
        return film;
    }

    public void setFilm(Film film) {
        this.film = film;
    }
}
