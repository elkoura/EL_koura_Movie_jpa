package entities;

import jakarta.persistence.*;



import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "Film")
public class Film {
    @Id
    private String idImdb;
    private String nom;
    private int annee;
    private double rating;
    
    @Column(name = "URL")
    private String url;
    
    @Column(length = 1024)
    private String resume;

    @ManyToOne
    @JoinColumn(name = "LIEU_TOURNAGE_ID")
    private Lieu lieuTournage;

    @ManyToOne
    @JoinColumn(name = "PAYS_ID")
    private Pays pays;

    @ManyToOne
    @JoinColumn(name = "LANGUE_ID")
    private Langue langue;

    @ManyToMany
    @JoinTable(name = "Film_Genre", joinColumns = @JoinColumn(name = "FILM_ID"), inverseJoinColumns = @JoinColumn(name = "GENRE_ID"))
    private Set<Genre> genres = new HashSet<>();

    // Constructor without arguments
    public Film() {}

	/**
	 * @return the idImdb
	 */
	public String getIdImdb() {
		return idImdb;
	}

	/**
	 * @return the nom
	 */
	public String getNom() {
		return nom;
	}

	/**
	 * @return the annee
	 */
	public int getAnnee() {
		return annee;
	}

	/**
	 * @return the rating
	 */
	public double getRating() {
		return rating;
	}

	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @return the resume
	 */
	public String getResume() {
		return resume;
	}

	/**
	 * @return the lieuTournage
	 */
	public Lieu getLieuTournage() {
		return lieuTournage;
	}

	/**
	 * @return the pays
	 */
	public Pays getPays() {
		return pays;
	}

	/**
	 * @return the langue
	 */
	public Langue getLangue() {
		return langue;
	}

	/**
	 * @return the genres
	 */
	public Set<Genre> getGenres() {
		return genres;
	}

	/**
	 * @param idImdb the idImdb to set
	 */
	public void setIdImdb(String idImdb) {
		this.idImdb = idImdb;
	}

	/**
	 * @param nom the nom to set
	 */
	public void setNom(String nom) {
		this.nom = nom;
	}

	/**
	 * @param annee the annee to set
	 */
	public void setAnnee(int annee) {
		this.annee = annee;
	}

	/**
	 * @param rating the rating to set
	 */
	public void setRating(double rating) {
		this.rating = rating;
	}

	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * @param resume the resume to set
	 */
	public void setResume(String resume) {
		this.resume = resume;
	}

	/**
	 * @param lieuTournage the lieuTournage to set
	 */
	public void setLieuTournage(Lieu lieuTournage) {
		this.lieuTournage = lieuTournage;
	}

	/**
	 * @param pays the pays to set
	 */
	public void setPays(Pays pays) {
		this.pays = pays;
	}

	/**
	 * @param langue the langue to set
	 */
	public void setLangue(Langue langue) {
		this.langue = langue;
	}

	/**
	 * @param genres the genres to set
	 */
	public void setGenres(Set<Genre> genres) {
		this.genres = genres;
	}

	public static Film getFilmByIMDB(String string, EntityManager em) throws DataMissingException {
		TypedQuery<Film> query = em.createQuery("SELECT a From Film a", Film.class);
		List<Film> films = query.getResultList();
		Film film = new Film();
		for (Film item : films) {
			if (item.getIdImdb().equals(string)) {
				film = item;
				em.persist(film);
			}

		}
		if (film.getIdImdb() == null) {
			throw new DataMissingException("no find moviez");
		}
		return film;
	}

}
	
