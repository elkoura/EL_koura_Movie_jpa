package entities;

import jakarta.persistence.*;

@Entity
@Table(name = "ROLE")
public class Role {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ID_FILM", nullable = false)
    private Film film;

    @ManyToOne
    @JoinColumn(name = "ID_ACTEUR", nullable = false)
    private Acteur acteur;

    private String personnage;

    public Role() {
        // Default constructor
    }

    public Role(Film film, Acteur acteur, String personnage) {
        this.film = film;
        this.acteur = acteur;
        this.personnage = personnage;
    }

    // Getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    @Override
    public String toString() {
        return "Role [id=" + id + ", personnage=" + personnage + ", film=" + film.getNom() + ", acteur=" + acteur.getIdentite() + "]";
    }
}
