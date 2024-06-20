package entities;

import java.io.Serializable;
import java.util.Objects;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class CastingPrincipaleId implements Serializable {

    @Column(name = "FILM_ID")
    private Long filmId;

    @Column(name = "ACTEUR_ID")
    private Long acteurId;

    // Default constructor
    public CastingPrincipaleId() {}

    public CastingPrincipaleId(Long filmId, Long acteurId) {
        this.filmId = filmId;
        this.acteurId = acteurId;
    }

    // Getters and setters
    public Long getFilmId() {
        return filmId;
    }

    public void setFilmId(Long filmId) {
        this.filmId = filmId;
    }

    public Long getActeurId() {
        return acteurId;
    }

    public void setActeurId(Long acteurId) {
        this.acteurId = acteurId;
    }

    // hashCode and equals
    @Override
    public int hashCode() {
        return Objects.hash(filmId, acteurId);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        CastingPrincipaleId that = (CastingPrincipaleId) obj;
        return Objects.equals(filmId, that.filmId) && Objects.equals(acteurId, that.acteurId);
    }
}
