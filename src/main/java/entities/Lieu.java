package entities;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Lieu")
public class Lieu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String ville;
    private String detailsSup;

    @ManyToOne
    @JoinColumn(name = "PAYS_ID")
    private Pays pays;

    @OneToMany(mappedBy = "lieuTournage")
    private Set<Film> films = new HashSet<>();

    @OneToMany(mappedBy = "lieuNaissance")
    private Set<Acteur> acteurs = new HashSet<>();

    @OneToMany(mappedBy = "lieuNaissance")
    private Set<Realisateur> realisateurs = new HashSet<>();

    // Constructor without arguments
    public Lieu() {}

    // Constructor with arguments
    public Lieu(String ville, String detailsSup, Pays pays) {
        this.ville = ville;
        this.detailsSup = detailsSup;
        this.pays = pays;
    }

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @return the ville
	 */
	public String getVille() {
		return ville;
	}

	/**
	 * @return the detailsSup
	 */
	public String getDetailsSup() {
		return detailsSup;
	}

	/**
	 * @return the pays
	 */
	public Pays getPays() {
		return pays;
	}

	/**
	 * @return the films
	 */
	public Set<Film> getFilms() {
		return films;
	}

	/**
	 * @return the acteurs
	 */
	public Set<Acteur> getActeurs() {
		return acteurs;
	}

	/**
	 * @return the realisateurs
	 */
	public Set<Realisateur> getRealisateurs() {
		return realisateurs;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @param ville the ville to set
	 */
	public void setVille(String ville) {
		this.ville = ville;
	}

	/**
	 * @param detailsSup the detailsSup to set
	 */
	public void setDetailsSup(String detailsSup) {
		this.detailsSup = detailsSup;
	}

	/**
	 * @param pays the pays to set
	 */
	public void setPays(Pays pays) {
		this.pays = pays;
	}

	/**
	 * @param films the films to set
	 */
	public void setFilms(Set<Film> films) {
		this.films = films;
	}

	/**
	 * @param acteurs the acteurs to set
	 */
	public void setActeurs(Set<Acteur> acteurs) {
		this.acteurs = acteurs;
	}

	/**
	 * @param realisateurs the realisateurs to set
	 */
	public void setRealisateurs(Set<Realisateur> realisateurs) {
		this.realisateurs = realisateurs;
	}

    // Getters and setters
    // ...
    
    
}
