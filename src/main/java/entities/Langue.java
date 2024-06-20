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

    @OneToMany(mappedBy = "langue")
    private Set<Film> films = new HashSet<>();

    // Constructor without arguments
    public Langue() {}

    // Getters and setters
    // ...
    
    
    public static Langue getLangueByName(List<Langue> langues, String string) {
        Langue langue = new Langue();
        for (Langue item : langues) {
            if (item.getNom().equals(string)) {
                langue = item;
            }
        }
        return langue;
    }

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @return the nom
	 */
	public String getNom() {
		return nom;
	}

	/**
	 * @return the films
	 */
	public Set<Film> getFilms() {
		return films;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @param nom the nom to set
	 */
	public void setNom(String nom) {
		this.nom = nom;
	}

	/**
	 * @param films the films to set
	 */
	public void setFilms(Set<Film> films) {
		this.films = films;
	}
}
