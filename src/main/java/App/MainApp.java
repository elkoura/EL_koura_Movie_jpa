package App;

import jakarta.persistence.EntityManager;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import entities.Acteur;

import entities.Film;
import entities.FilmRealisateur;
import entities.Pays;
import entities.Realisateur;
import entities.Role;

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

        // Lire les fichiers CSV et insérer les données dans la base de données
        List<Pays> paysList = paysDao.lireFichier("C:\\Users\\fouad\\OneDrive\\Bureau\\test-fichier-csv\\testPays.csv");
        for (Pays pays : paysList) {
            paysDao.insert(pays);
        }

     //   List<Acteur> acteursList = acteurDao.lireFichier("C:\\Users\\fouad\\OneDrive\\Bureau\\test-fichier-csv\\testActeur.csv");
     //   for (Acteur acteur : acteursList) {
       //     acteurDao.insert(acteur);
     //   }

       // List<Realisateur> realisateursList = realisateurDao.lireFichier("C:\\Users\\fouad\\OneDrive\\Bureau\\test-fichier-csv\\testRealisateur.csv");
        //realisateurDao.enregistrerRealisateurs(realisateursList);

        List<Film> filmsList = filmDao.lireFichier("C:\\Users\\fouad\\OneDrive\\Bureau\\test-fichier-csv\\testFilm.csv");
        for (Film film : filmsList) {
            filmDao.insert(film);
        }

        List<Role> rolesList = roleDao.lireFichier("C:\\Users\\fouad\\OneDrive\\Bureau\\test-fichier-csv\\testRole.csv");
        for (Role role : rolesList) {
            roleDao.insert(role);
        }

        List<FilmRealisateur> filmRealisateursList = filmRealisateurDao.lireFichier("C:\\Users\\fouad\\OneDrive\\Bureau\\test-fichier-csv\\testFilmRealisateur.csv");
        for (FilmRealisateur filmRealisateur : filmRealisateursList) {
            filmRealisateurDao.insert(filmRealisateur);
        }

        

        em.close();
        emf.close();
    }
}
