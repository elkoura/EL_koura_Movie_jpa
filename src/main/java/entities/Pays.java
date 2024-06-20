package entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Pays")
public class Pays {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idImdb; // Utilisation d'un identifiant numérique auto-incrémenté

    private String nom;
    private String url;

    // Constructeur sans arguments
    public Pays() {}

    // Getters and setters
    public Integer getIdImdb() {
        return idImdb;
    }

    public void setIdImdb(Integer idImdb) {
        this.idImdb = idImdb;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
