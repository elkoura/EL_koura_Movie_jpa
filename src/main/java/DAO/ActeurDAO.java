package DAO;

import entities.Acteur;
import jakarta.persistence.EntityManager;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ActeurDAO {
    private EntityManager em;

    public ActeurDAO(EntityManager em) {
        this.em = em;
    }

    public List<Acteur> lireFichier(String cheminFichier) {
        List<Acteur> acteurs = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(cheminFichier))) {
            String ligne;
            br.readLine(); // Ignorer la ligne d'en-tête
            while ((ligne = br.readLine()) != null) {
                String[] valeurs = ligne.split(";");
                if (valeurs.length >= 5) {
                    Acteur acteur = new Acteur();
                    acteur.setIdImdb(valeurs[0]);
                    acteur.setIdentite(valeurs[1]);
                    acteur.setDateNaissance(valeurs[2]);
                    acteur.setLieuNaissance(valeurs[3]);
                    acteur.setUrl(valeurs[5]);
                    acteurs.add(acteur);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return acteurs;
    }

    public void insert(Acteur acteur) {
        em.getTransaction().begin();
        em.persist(acteur);
        em.getTransaction().commit();
    }
}
