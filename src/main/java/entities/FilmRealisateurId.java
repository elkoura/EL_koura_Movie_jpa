package entities;

import java.io.Serializable;
import java.util.Objects;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class FilmRealisateurId implements Serializable {
    
    @Column(name = "filmId")
    private Long filmId;

    @Column(name = "realisateurId")
    private Long realisateurId;

    // Default constructor
    public FilmRealisateurId() {}

    public FilmRealisateurId(Long filmId, Long realisateurId) {
        this.filmId = filmId;
        this.realisateurId = realisateurId;
    }

    // Getters and setters
    public Long getFilmId() {
        return filmId;
    }

    public void setFilmId(Long filmId) {
        this.filmId = filmId;
    }

    public Long getRealisateurId() {
        return realisateurId;
    }

    public void setRealisateurId(Long realisateurId) {
        this.realisateurId = realisateurId;
    }

    // hashCode and equals
    @Override
    public int hashCode() {
        return Objects.hash(filmId, realisateurId);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        FilmRealisateurId that = (FilmRealisateurId) obj;
        return Objects.equals(filmId, that.filmId) && Objects.equals(realisateurId, that.realisateurId);
    }
}
