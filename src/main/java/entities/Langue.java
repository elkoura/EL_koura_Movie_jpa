package entities;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "Langue")
public class Langue {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String nom;

    @OneToMany(mappedBy = "langue", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Film> films = new HashSet<>();

    // Constructor without arguments
    public Langue() {}

    // Constructor with 'nom' parameter
    public Langue(String nom) {
        this.nom = nom;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Set<Film> getFilms() {
        return films;
    }

    public void setFilms(Set<Film> films) {
        this.films = films;
    }

    // Static method to get Langue by name
    public static Langue getLangueByName(List<Langue> langues, String name) {
        for (Langue langue : langues) {
            if (langue.getNom().equals(name)) {
                return langue;
            }
        }
        return null; // Return null if no match is found
    }

    @Override
    public String toString() {
        return "Langue [id=" + id + ", nom=" + nom + "]";
    }
}
