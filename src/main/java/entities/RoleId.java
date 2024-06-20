package entities;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Embeddable;
import javax.persistence.Column;

@Embeddable
public class RoleId implements Serializable {
    @Column(name = "film_id")
    private String filmId;

    @Column(name = "acteur_id")
    private String acteurId;

    // Constructeur par défaut
    public RoleId() {
    }

    // Constructeur avec paramètres
    public RoleId(String filmId, String acteurId) {
        this.filmId = filmId;
        this.acteurId = acteurId;
    }

    // Getters and setters
    public String getFilmId() {
        return filmId;
    }

    public void setFilmId(String filmId) {
        this.filmId = filmId;
    }

    public String getActeurId() {
        return acteurId;
    }

    public void setActeurId(String acteurId) {
        this.acteurId = acteurId;
    }

    // hashCode and equals methods
    @Override
    public int hashCode() {
        return Objects.hash(filmId, acteurId);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        RoleId roleId = (RoleId) obj;
        return filmId.equals(roleId.filmId) && acteurId.equals(roleId.acteurId);
    }
}
