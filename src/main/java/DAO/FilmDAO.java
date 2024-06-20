package DAO;

import entities.Film;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FilmDAO {

    private EntityManager em;

    public FilmDAO(EntityManager em) {
        this.em = em;
    }

    public void insert(Film film) {
        em.getTransaction().begin();
        em.persist(film);
        em.getTransaction().commit();
    }

    public Film findById(Long id) {
        return em.find(Film.class, id);
    }

    public List<Film> findAll() {
        TypedQuery<Film> query = em.createQuery("SELECT f FROM Film f", Film.class);
        return query.getResultList();
    }

    public void update(Film film) {
        em.getTransaction().begin();
        em.merge(film);
        em.getTransaction().commit();
    }

    public void delete(Film film) {
        em.getTransaction().begin();
        em.remove(em.contains(film) ? film : em.merge(film));
        em.getTransaction().commit();
    }

    public List<Film> lireFichier(String filePath) {
        List<Film> filmsList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(";");
                Film film = new Film();
                film.setIdImdb(values[0]);
                film.setNom(values[1]);
                film.setAnnee(Integer.parseInt(values[2]));
                film.setRating(Double.parseDouble(values[3]));
                film.setUrl(values[4]);
                film.setResume(values[5]);
                filmsList.add(film);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return filmsList;
    }

    public void enregistrerFilms(List<Film> films) {
        em.getTransaction().begin();
        for (Film film : films) {
            em.persist(film);
        }
        em.getTransaction().commit();
    }

    public static List<Film> extractingFilms(EntityManager em) {
        TypedQuery<Film> query = em.createQuery("SELECT f FROM Film f", Film.class);
        return query.getResultList();
    }

    public static boolean isFilmInDBByImdbId(List<Film> films, String imdbId) {
        for (Film film : films) {
            if (film.getIdImdb().equals(imdbId)) {
                return true;
            }
        }
        return false;
    }

    public static Film getFilmByImdbId(String imdbId, EntityManager em) {
        TypedQuery<Film> query = em.createQuery("SELECT f FROM Film f WHERE f.idImdb = :imdbId", Film.class);
        query.setParameter("imdbId", imdbId);
        return query.getResultStream().findFirst().orElse(null);
    }
}
