package DAO;

import java.io.Serializable;
import java.util.Objects;

public class FilmRealisateurId implements Serializable {
    private Long filmId;
    private Long realisateurId;

    // Constructeur sans arguments
    public FilmRealisateurId() {
    }

    // Constructeur avec les deux clés primaires
    public FilmRealisateurId(Long filmId, Long realisateurId) {
        this.filmId = filmId;
        this.realisateurId = realisateurId;
    }

    // Getters et setters (si nécessaire)

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

    // Equals et HashCode pour la comparaison et le stockage en collection
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FilmRealisateurId that = (FilmRealisateurId) o;
        return Objects.equals(filmId, that.filmId) &&
                Objects.equals(realisateurId, that.realisateurId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(filmId, realisateurId);
    }
}
