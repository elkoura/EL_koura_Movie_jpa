package DAO;

import entities.Film;
import entities.FilmRealisateur;
import entities.FilmRealisateurId;
import entities.Realisateur;
import jakarta.persistence.EntityManager;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FilmRealisateurDAO {

    private EntityManager em;

    public FilmRealisateurDAO(EntityManager em) {
        this.em = em;
    }

    public void insert(FilmRealisateur filmRealisateur) {
        em.getTransaction().begin();
        em.persist(filmRealisateur);
        em.getTransaction().commit();
    }

    public List<FilmRealisateur> lireFichier(String filePath) {
        List<FilmRealisateur> filmRealisateursList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(";");
                Long filmId = Long.parseLong(values[0]);
                Long realisateurId = Long.parseLong(values[1]);

                Film film = em.find(Film.class, filmId);
                Realisateur realisateur = em.find(Realisateur.class, realisateurId);

                if (film != null && realisateur != null) {
                    FilmRealisateurId id = new FilmRealisateurId();
                    id.setFilmId(filmId);
                    id.setRealisateurId(realisateurId);

                    FilmRealisateur filmRealisateur = new FilmRealisateur();
                    filmRealisateur.setId(id);
                    filmRealisateur.setFilm(film);
                    filmRealisateur.setRealisateur(realisateur);

                    filmRealisateursList.add(filmRealisateur);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return filmRealisateursList;
    }
}
