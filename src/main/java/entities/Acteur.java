package entities;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "ACTEUR")
public class Acteur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "idImdb")
    private String idImdb;

    @Column(name = "identite")
    private String identite;

    @Column(name = "dateNaissance")
    private LocalDate dateNaissance;

    @Column(name = "url")
    private String url;

    @Column(name = "lieuNaissance")
    private String lieuNaissance; // Champ pour le lieu de naissance sous forme de chaîne

    @Column(name = "taille")
    private double taille;

    @OneToMany(mappedBy = "acteur")
    private Set<Role> roles = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "CASTING",
            joinColumns = { @JoinColumn(name = "id_acteur") },
            inverseJoinColumns = { @JoinColumn(name = "id_film") }
    )
    private Set<Film> films = new HashSet<>();

    public Acteur() {
    }

    public Acteur(String idImdb, String identite, LocalDate dateNaissance, String url, String lieuNaissance, double taille) {
        this.idImdb = idImdb;
        this.identite = identite;
        this.dateNaissance = dateNaissance;
        this.url = url;
        this.lieuNaissance = lieuNaissance;
        this.taille = taille;
    }

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

    public String getIdentite() {
        return identite;
    }

    public void setIdentite(String identite) {
        this.identite = identite;
    }

    public LocalDate getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(LocalDate dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getLieuNaissance() {
        return lieuNaissance;
    }

    public void setLieuNaissance(String lieuNaissance) {
        this.lieuNaissance = lieuNaissance;
    }

    public double getTaille() {
        return taille;
    }

    public void setTaille(double taille) {
        this.taille = taille;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public Set<Film> getFilms() {
        return films;
    }

    public void setFilms(Set<Film> films) {
        this.films = films;
    }

    public void addRole(Role role) {
        roles.add(role);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Acteur acteur = (Acteur) o;
        return Objects.equals(idImdb, acteur.idImdb);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idImdb);
    }

    @Override
    public String toString() {
        return "Acteur{" +
                "id=" + id +
                ", idImdb='" + idImdb + '\'' +
                ", identite='" + identite + '\'' +
                ", dateNaissance=" + dateNaissance +
                ", url='" + url + '\'' +
                ", lieuNaissance='" + lieuNaissance + '\'' +
                ", taille=" + taille +
                '}';
    }
}
