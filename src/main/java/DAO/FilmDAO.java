package DAO;

import entities.Film;
import entities.Genre;
import entities.Lieu;
import entities.Langue;
import entities.Pays;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Data Access Object (DAO) for managing Film entities.
 */
public class FilmDAO {
    private EntityManager em;

    public FilmDAO(EntityManager em) {
        this.em = em;
    }

    public Film getFilmByImdbId(String imdbId) {
        TypedQuery<Film> query = em.createQuery("SELECT f FROM Film f WHERE f.idImdb = :imdbId", Film.class);
        query.setParameter("imdbId", imdbId);
        return query.getSingleResult();
    }

    /**
     * Reads a CSV file and creates a list of Film objects.
     * @param cheminFichier the path to the CSV file.
     * @return a list of Film objects.
     */
    public List<Film> lireFichier(String cheminFichier) {
        List<Film> filmsList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(cheminFichier))) {
            String line;
            br.readLine(); // Ignore header line
            while ((line = br.readLine()) != null) {
                String[] values = line.split(";");
                if (values.length >= 10) { // Ensure there are enough columns
                    Film film = new Film();
                    film.setIdImdb(values[0]);
                    film.setNom(values[1]);
                    film.setAnnee(Integer.parseInt(values[2]));
                    film.setRating(Double.parseDouble(values[3]));
                    film.setUrl(values[4]);
                    film.setLieuTournage(findOrCreateLieu(values[5]));
                    film.setGenres(parseGenres(values[6]));
                    film.setLangue(findOrCreateLangue(values[7]));
                    film.setResume(values[8]);
                    film.setPays(findOrCreatePays(values[9]));
                    filmsList.add(film);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return filmsList;
    }

    /**
     * Inserts a Film object into the database.
     * @param film the Film object to be inserted.
     */
    public void insert(Film film) {
        em.getTransaction().begin();
        em.persist(film);
        em.getTransaction().commit();
    }

    /**
     * Finds a Film object by its ID.
     * @param id the ID of the Film object.
     * @return the Film object.
     */
    public Film findById(Long id) {
        return em.find(Film.class, id);
    }

    /**
     * Retrieves all Film objects from the database.
     * @return a list of Film objects.
     */
    public List<Film> findAll() {
        TypedQuery<Film> query = em.createQuery("SELECT f FROM Film f", Film.class);
        return query.getResultList();
    }

    /**
     * Updates a Film object in the database.
     * @param film the Film object to be updated.
     */
    public void update(Film film) {
        em.getTransaction().begin();
        em.merge(film);
        em.getTransaction().commit();
    }

    /**
     * Deletes a Film object from the database.
     * @param film the Film object to be deleted.
     */
    public void delete(Film film) {
        em.getTransaction().begin();
        em.remove(em.contains(film) ? film : em.merge(film));
        em.getTransaction().commit();
    }

    /**
     * Inserts a list of Film objects into the database.
     * @param films the list of Film objects to be inserted.
     */
    public void enregistrerFilms(List<Film> films) {
        em.getTransaction().begin();
        for (Film film : films) {
            em.persist(film);
        }
        em.getTransaction().commit();
    }

    /**
     * Extracts all Film objects from the database.
     * @param em the EntityManager to be used for database operations.
     * @return a list of Film objects.
     */
    public static List<Film> extractingFilms(EntityManager em) {
        TypedQuery<Film> query = em.createQuery("SELECT f FROM Film f", Film.class);
        return query.getResultList();
    }

    /**
     * Checks if a Film object with a given IMDb ID exists in the list.
     * @param films the list of Film objects.
     * @param imdbId the IMDb ID to check.
     * @return true if the Film exists, false otherwise.
     */
    public static boolean isFilmInDBByImdbId(List<Film> films, String imdbId) {
        for (Film film : films) {
            if (film.getIdImdb().equals(imdbId)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Retrieves a Film object by its IMDb ID.
     * @param imdbId the IMDb ID of the Film.
     * @param em the EntityManager to be used for database operations.
     * @return the Film object.
     */
    public static Film getFilmByImdbId(String imdbId, EntityManager em) {
        TypedQuery<Film> query = em.createQuery("SELECT f FROM Film f WHERE f.idImdb = :imdbId", Film.class);
        query.setParameter("imdbId", imdbId);
        return query.getResultStream().findFirst().orElse(null);
    }

    private Lieu findOrCreateLieu(String ville) {
        if (ville == null || ville.isEmpty()) {
            return null;
        }
        TypedQuery<Lieu> query = em.createQuery("SELECT l FROM Lieu l WHERE l.ville = :villeName", Lieu.class);
        query.setParameter("villeName", ville);
        List<Lieu> lieux = query.getResultList();
        if (!lieux.isEmpty()) {
            return lieux.get(0);
        } else {
            Lieu lieu = new Lieu();
            lieu.setVille(ville);
            em.persist(lieu);
            return lieu;
        }
    }

    private Set<Genre> parseGenres(String genresString) {
        Set<Genre> genres = new HashSet<>();
        if (genresString == null || genresString.isEmpty()) {
            return genres;
        }
        String[] genresArray = genresString.split(",");
        for (String genreName : genresArray) {
            TypedQuery<Genre> query = em.createQuery("SELECT g FROM Genre g WHERE g.nom = :genreName", Genre.class);
            query.setParameter("genreName", genreName.trim());
            List<Genre> genreList = query.getResultList();
            if (!genreList.isEmpty()) {
                genres.add(genreList.get(0));
            } else {
                Genre genre = new Genre();
                genre.setNom(genreName.trim());
                em.persist(genre);
                genres.add(genre);
            }
        }
        return genres;
    }

    private Langue findOrCreateLangue(String langueName) {
        if (langueName == null || langueName.isEmpty()) {
            return null;
        }
        TypedQuery<Langue> query = em.createQuery("SELECT l FROM Langue l WHERE l.nom = :langueName", Langue.class);
        query.setParameter("langueName", langueName);
        List<Langue> langues = query.getResultList();
        if (!langues.isEmpty()) {
            return langues.get(0);
        } else {
            Langue langue = new Langue();
            langue.setNom(langueName);
            em.persist(langue);
            return langue;
        }
    }

    private Pays findOrCreatePays(String paysName) {
        if (paysName == null || paysName.isEmpty()) {
            return null;
        }
        TypedQuery<Pays> query = em.createQuery("SELECT p FROM Pays p WHERE p.nom = :paysName", Pays.class);
        query.setParameter("paysName", paysName);
        List<Pays> paysList = query.getResultList();
        if (!paysList.isEmpty()) {
            return paysList.get(0);
        } else {
            Pays pays = new Pays();
            pays.setNom(paysName);
            em.persist(pays);
            return pays;
        }
    }
}
