package DAO;
import jakarta.persistence.*;

import entities.Acteur;

import entities.CastingPrincipale;
import entities.Film;
import entities.FilmRealisateur;
import entities.Pays;
import entities.Realisateur;
import entities.Role;

import java.util.List;

public class MainApp {
    private static final String PERSISTENCE_UNIT_NAME = "filmsPU";

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        EntityManager em = emf.createEntityManager();

        // Créer les DAO
        ActeurDAO acteurDao = new ActeurDAO(em);
        RealisateurDAO realisateurDao = new RealisateurDAO(em);
        PaysDAO paysDao = new PaysDAO(em);
        FilmDAO filmDao = new FilmDAO(em);
        RoleDAO roleDao = new RoleDAO(em);
        FilmRealisateurDAO filmRealisateurDao = new FilmRealisateurDAO(em);
        CastingPrincipaleDAO castingPrincipaleDao = new CastingPrincipaleDAO(em);
/*
        // Lire les fichiers CSV et insérer les données dans la base de données
        List<Pays> paysList = paysDao.lireFichier("C:\\Users\\fouad\\OneDrive\\Bureau\\test-fichier-csv\\testPays.csv");
        for (Pays pays : paysList) {
            paysDao.insert(pays);
        }

        List<Acteur> acteursList = acteurDao.lireFichier("C:\\Users\\fouad\\OneDrive\\Bureau\\test-fichier-csv\\testActeurs.csv");
        for (Acteur acteur : acteursList) {
            acteurDao.insert(acteur);
        }
        
        List<Realisateur> realisateursList = realisateurDao.lireFichier("C:\\Users\\fouad\\OneDrive\\Bureau\\test-fichier-csv\\testRealisateur.csv");
        realisateurDao.enregistrerRealisateurs(realisateursList);
*/
        List<Film> filmsList = filmDao.lireFichier("C:\\Users\\fouad\\OneDrive\\Bureau\\test-fichier-csv\\testFilms.csv");
        for (Film film : filmsList) {
            filmDao.insert(film);
        }

        List<Role> rolesList = roleDao.lireFichier("C:\\Users\\fouad\\OneDrive\\Bureau\\test-fichier-csv\\testRoles.csv");
        for (Role role : rolesList) {
            roleDao.insert(role);
        }

        List<FilmRealisateur> filmRealisateursList = filmRealisateurDao.lireFichier("C:\\Users\\fouad\\OneDrive\\Bureau\\test-fichier-csv\\testFilmes_realisateur.csv");
        for (FilmRealisateur filmRealisateur : filmRealisateursList) {
            filmRealisateurDao.insert(filmRealisateur);
        }

        List<CastingPrincipale> castingPrincipalesList = castingPrincipaleDao.lireFichier("C:\\Users\\fouad\\OneDrive\\Bureau\\test-fichier-csv\\testCastingPrincipales.csv");
        for (CastingPrincipale castingPrincipale : castingPrincipalesList) {
            castingPrincipaleDao.insert(castingPrincipale);
        }

        em.close();
        emf.close();
    }
}