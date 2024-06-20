package entities;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;

@Entity
@Table(name = "FilmRealisateur")
public class FilmRealisateur {

    @EmbeddedId
    private FilmRealisateurId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("filmId")
    @JoinColumn(name = "filmId", insertable = false, updatable = false)
    private Film film;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("realisateurId")
    @JoinColumn(name = "realisateurId", insertable = false, updatable = false)
    private Realisateur realisateur;

    // Default constructor
    public FilmRealisateur() {}

    // Getters and setters
    public FilmRealisateurId getId() {
        return id;
    }

    public void setId(FilmRealisateurId id) {
        this.id = id;
    }

    public Film getFilm() {
        return film;
    }

    public void setFilm(Film film) {
        this.film = film;
    }

    public Realisateur getRealisateur() {
        return realisateur;
    }

    public void setRealisateur(Realisateur realisateur) {
        this.realisateur = realisateur;
    }
}
