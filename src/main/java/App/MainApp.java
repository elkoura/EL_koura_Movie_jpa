package App;

import entities.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.io.File;
import java.util.List;

import DAO.ActeurDAO;
import DAO.FilmDAO;
import DAO.FilmRealisateurDAO;
import DAO.PaysDAO;
import DAO.RealisateurDAO;
import DAO.RoleDAO;

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

        // Chemins des fichiers CSV
        String[] cheminsFichiers = {
                "C:\\Users\\fouad\\OneDrive\\Bureau\\test-fichier-csv\\testPays.csv",
                "C:\\Users\\fouad\\OneDrive\\Bureau\\test-fichier-csv\\testActeurs.csv",
                "C:\\Users\\fouad\\OneDrive\\Bureau\\test-fichier-csv\\testRealisateur.csv",
                "C:\\Users\\fouad\\OneDrive\\Bureau\\test-fichier-csv\\testFilms.csv",
                "C:\\Users\\fouad\\OneDrive\\Bureau\\test-fichier-csv\\testRoles.csv",
                "C:\\Users\\fouad\\OneDrive\\Bureau\\test-fichier-csv\\testFilmes_realisateur.csv"
        };

        // Vérifier si les fichiers existent
        for (String chemin : cheminsFichiers) {
            File fichier = new File(chemin);
            if (!fichier.exists()) {
                System.out.println("Le fichier " + chemin + " est introuvable.");
                return; // Arrête l'exécution si un fichier est manquant
            }
        }

        try {
            em.getTransaction().begin();

            // Lire les fichiers CSV et insérer les données dans la base de données
            List<Pays> paysList = paysDao.lireFichier(cheminsFichiers[0]);
            for (Pays pays : paysList) {
                paysDao.insert(pays);
            }

            List<Acteur> acteursList = acteurDao.lireFichier(cheminsFichiers[1]);
            for (Acteur acteur : acteursList) {
                acteurDao.insert(acteur);
            }

            List<Realisateur> realisateursList = realisateurDao.lireFichier(cheminsFichiers[2]);
            realisateurDao.enregistrerRealisateurs(realisateursList);

            List<Film> filmsList = filmDao.lireFichier(cheminsFichiers[3]);
            for (Film film : filmsList) {
                filmDao.insert(film);
            }

            List<Role> rolesList = roleDao.lireFichier(cheminsFichiers[4]);
            for (Role role : rolesList) {
                roleDao.insert(role);
            }

            List<FilmRealisateur> filmRealisateursList = filmRealisateurDao.lireFichier(cheminsFichiers[5]);
            for (FilmRealisateur filmRealisateur : filmRealisateursList) {
                filmRealisateurDao.insert(filmRealisateur);
            }

            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            em.close();
            emf.close();
        }
    }
}
