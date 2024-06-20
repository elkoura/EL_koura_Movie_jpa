package entities;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Realisateurs")
public class Realisateur {
    @Id
    private String idImdb;
    private String identite;
    private String dateNaissance;
    private String url;

    @ManyToOne
    @JoinColumn(name = "LIEU_NAISSANCE_ID")
    private Lieu lieuNaissance;

    @ManyToMany
    @JoinTable(name = "FilmRealisateur", 
               joinColumns = @JoinColumn(name = "REALISATEUR_ID"), 
               inverseJoinColumns = @JoinColumn(name = "FILM_ID"))
    private Set<Film> films = new HashSet<>();

    public Realisateur() {}

    // Getters and setters
    public String getIdImdb() { return idImdb; }
    public void setIdImdb(String idImdb) { this.idImdb = idImdb; }

    public String getIdentite() { return identite; }
    public void setIdentite(String identite) { this.identite = identite; }

    public String getDateNaissance() { return dateNaissance; }
    public void setDateNaissance(String dateNaissance) { this.dateNaissance = dateNaissance; }

    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }

    public Lieu getLieuNaissance() { return lieuNaissance; }
    public void setLieuNaissance(Lieu lieuNaissance) { this.lieuNaissance = lieuNaissance; }

    public Set<Film> getFilms() { return films; }
    public void setFilms(Set<Film> films) { this.films = films; }
}
