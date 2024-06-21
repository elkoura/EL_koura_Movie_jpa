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

    // Constructeur sans arguments
    public Film() {}

    // Getters et setters
    public String getIdImdb() {
        return idImdb;
    }

    public void setIdImdb(String idImdb) {
        this.idImdb = idImdb;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getAnnee() {
        return annee;
    }

    public void setAnnee(int annee) {
        this.annee = annee;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getResume() {
        return resume;
    }

    public void setResume(String resume) {
        this.resume = resume;
    }

    public Lieu getLieuTournage() {
        return lieuTournage;
    }

    public void setLieuTournage(Lieu lieuTournage) {
        this.lieuTournage = lieuTournage;
    }

    public Pays getPays() {
        return pays;
    }

    public void setPays(Pays pays) {
        this.pays = pays;
    }

    public Langue getLangue() {
        return langue;
    }

    public void setLangue(Langue langue) {
        this.langue = langue;
    }

    public Set<Genre> getGenres() {
        return genres;
    }

    public void setGenres(Set<Genre> genres) {
        this.genres = genres;
    }

    public static Film getFilmByIMDB(String idImdb, EntityManager em) throws DataMissingException {
        TypedQuery<Film> query = em.createQuery("SELECT a FROM Film a WHERE a.idImdb = :idImdb", Film.class);
        query.setParameter("idImdb", idImdb);
        List<Film> films = query.getResultList();
        if (films.isEmpty()) {
            throw new DataMissingException("Film not found with ID: " + idImdb);
        }
        return films.get(0);
    }
}
