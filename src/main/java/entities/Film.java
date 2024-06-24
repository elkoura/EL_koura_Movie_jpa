package entities;

import jakarta.persistence.*;
import java.util.Set;

@Entity
@Table(name = "Film")
public class Film {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "idImdb", nullable = false, unique = true)
    private String idImdb;

    @Column(name = "nom")
    private String nom;

    @Column(name = "annee")
    private Integer annee;

    @Column(name = "rating")
    private Double rating;

    @Column(name = "url")
    private String url;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lieuTournage_id")
    private Lieu lieuTournage;

    @ManyToMany
    @JoinTable(name = "film_genre",
            joinColumns = @JoinColumn(name = "id_film"),
            inverseJoinColumns = @JoinColumn(name = "id_genre"))
    private Set<Genre> genres;

    @ManyToMany
    @JoinTable(name = "film_realisateur",
            joinColumns = @JoinColumn(name = "film_id"),
            inverseJoinColumns = @JoinColumn(name = "realisateurId"))
    private Set<Realisateur> realisateurs;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "langue_id")
    private Langue langue;

    @Column(name = "resume", columnDefinition = "TEXT")
    private String resume;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pays_id")
    private Pays pays;



    // Getters et setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public Integer getAnnee() {
        return annee;
    }

    public void setAnnee(Integer annee) {
        this.annee = annee;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Lieu getLieuTournage() {
        return lieuTournage;
    }

    public void setLieuTournage(Lieu lieuTournage) {
        this.lieuTournage = lieuTournage;
    }

    public Set<Genre> getGenres() {
        return genres;
    }

    public void setGenres(Set<Genre> genres) {
        this.genres = genres;
    }

    public Langue getLangue() {
        return langue;
    }

    public void setLangue(Langue langue) {
        this.langue = langue;
    }

    public String getResume() {
        return resume;
    }

    public void setResume(String resume) {
        this.resume = resume;
    }

    public Pays getPays() {
        return pays;
    }

    public void setPays(Pays pays) {
        this.pays = pays;
    }

    public Set<Realisateur> getRealisateurs() {
        return realisateurs;
    }

    public void setRealisateurs(Set<Realisateur> realisateurs) {
        this.realisateurs = realisateurs;
    }
}
