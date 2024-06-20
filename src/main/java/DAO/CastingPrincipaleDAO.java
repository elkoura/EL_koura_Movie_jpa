package DAO;

import entities.CastingPrincipale;
import entities.CastingPrincipaleId;
import entities.Acteur;
import entities.Film;
import jakarta.persistence.EntityManager;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CastingPrincipaleDAO {

    private EntityManager em;

    public CastingPrincipaleDAO(EntityManager em) {
        this.em = em;
    }

    public void insert(CastingPrincipale castingPrincipale) {
        em.getTransaction().begin();
        em.persist(castingPrincipale);
        em.getTransaction().commit();
    }

    public List<CastingPrincipale> lireFichier(String filePath) {
        List<CastingPrincipale> castingPrincipaleList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty() || line.startsWith("FILM;ID ACTEUR;")) {
                    continue; // Skip header or empty lines
                }

                String[] values = line.split(";");
                if (values.length < 2) {
                    continue; // Skip lines with insufficient data
                }

                Long filmId = Long.parseLong(values[0].trim());
                Long acteurId = Long.parseLong(values[1].trim());

                // Assurez-vous que les objets Film et Acteur existent en base de données
                Film film = em.find(Film.class, filmId);
                Acteur acteur = em.find(Acteur.class, acteurId);

                if (film != null && acteur != null) {
                    CastingPrincipale castingPrincipale = new CastingPrincipale();
                    CastingPrincipaleId castingPrincipaleId = new CastingPrincipaleId(filmId, acteurId);
                    castingPrincipale.setId(castingPrincipaleId);
                    castingPrincipale.setFilm(film);
                    castingPrincipale.setActeur(acteur);

                    castingPrincipaleList.add(castingPrincipale);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return castingPrincipaleList;
    }
}
