package DAO;

import entities.Realisateur;
import entities.Film;
import entities.Lieu;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class RealisateurDAO {

    private EntityManager em;

    public RealisateurDAO(EntityManager em) {
        this.em = em;
    }

    public void insert(Realisateur realisateur) {
        em.getTransaction().begin();
        em.persist(realisateur);
        em.getTransaction().commit();
    }

    public Realisateur findById(String id) {
        return em.find(Realisateur.class, id);
    }

    public List<Realisateur> findAll() {
        TypedQuery<Realisateur> query = em.createQuery("SELECT r FROM Realisateur r", Realisateur.class);
        return query.getResultList();
    }

    public void update(Realisateur realisateur) {
        em.getTransaction().begin();
        em.merge(realisateur);
        em.getTransaction().commit();
    }

    public void delete(Realisateur realisateur) {
        em.getTransaction().begin();
        em.remove(em.contains(realisateur) ? realisateur : em.merge(realisateur));
        em.getTransaction().commit();
    }

    public List<Realisateur> lireFichier(String filePath) {
        List<Realisateur> realisateursList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty() || line.startsWith("IDIMDB;IDENTITE;DATE_NAISSANCE;LIEU_NAISSANCE;URL")) {
                    continue; // Skip header or empty lines
                }

                String[] values = line.split(";");
                Realisateur realisateur = new Realisateur();
                realisateur.setIdImdb(values[0]);
                realisateur.setIdentite(values[1]);
                realisateur.setDateNaissance(values[2]);

                // Gestion de lieuNaissance
                String ville = values[3];
                Lieu lieu = em.createQuery("SELECT l FROM Lieu l WHERE l.ville = :ville", Lieu.class)
                        .setParameter("ville", ville)
                        .getResultStream()
                        .findFirst()
                        .orElseGet(() -> {
                            Lieu newLieu = new Lieu();
                            newLieu.setVille(ville);
                            em.getTransaction().begin();
                            em.persist(newLieu);
                            em.getTransaction().commit();
                            return newLieu;
                        });

                realisateur.setLieuNaissance(lieu);
                realisateur.setUrl(values[4]);

                // Check if the realisateur with this ID already exists
                if (!isRealInDBByImdbId(realisateursList, realisateur.getIdImdb())) {
                    realisateursList.add(realisateur);
                } else {
                    // Log or handle duplicate ID scenario
                    System.out.println("Skipping duplicate Realisateur from file: " + realisateur.getIdImdb());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return realisateursList;
    }


    public void enregistrerRealisateurs(List<Realisateur> realisateurs) {
        em.getTransaction().begin();
        for (Realisateur realisateur : realisateurs) {
            // Check if the entity already exists before persisting
            if (!isRealInDBByImdbId(extractingReal(em), realisateur.getIdImdb())) {
                em.persist(realisateur);
            } else {
                // Log or handle duplicate ID scenario
                System.out.println("Skipping duplicate Realisateur: " + realisateur.getIdImdb());
            }
        }
        em.getTransaction().commit();
    }


    public static void setRealisateursFromList(List<String> listeReal, EntityManager em) {
        for (String ligneCourante : listeReal) {
            String[] tab = ligneCourante.split(";", -1);
            if (!isRealInDBByImdbId(extractingReal(em), tab[0])) {
                Realisateur real = new Realisateur();
                real.setIdImdb(tab[0]);
                real.setIdentite(tab[1]);
                real.setDateNaissance(tab[2]);
                real.setLieuNaissance(LieuDAO.ajouterLieuPersonnes(tab[3], em));
                real.setUrl(tab[4]);
                em.getTransaction().begin();
                em.persist(real);
                em.getTransaction().commit();
            }
        }
    }

    public static void linkFilmToRealFromList(List<String> listeFilmReal, EntityManager em) {
        for (String ligneCourante : listeFilmReal) {
            String[] tab = ligneCourante.split(";", -1);
            Realisateur real = getRealByImdbId(extractingReal(em), tab[1]);
            if (real != null) {
                Film film = FilmDAO.getFilmByImdbId(tab[0], em);
                if (film != null && !isFilmLinkedToReal(real, film)) {
                    em.getTransaction().begin();
                    real.getFilms().add(film);
                    em.merge(real);
                    em.getTransaction().commit();
                }
            }
        }
    }

    private static boolean isFilmLinkedToReal(Realisateur real, Film film) {
        return real.getFilms().contains(film);
    }

    private static Realisateur getRealByImdbId(List<Realisateur> realisateurs, String imdbId) {
        for (Realisateur real : realisateurs) {
            if (real.getIdImdb().equals(imdbId)) {
                return real;
            }
        }
        return null;
    }

    private static boolean isRealInDBByImdbId(List<Realisateur> realisateurs, String imdbId) {
        for (Realisateur real : realisateurs) {
            if (real.getIdImdb().equals(imdbId)) {
                return true;
            }
        }
        return false;
    }

    private static List<Realisateur> extractingReal(EntityManager em) {
        TypedQuery<Realisateur> query = em.createQuery("SELECT r FROM Realisateur r", Realisateur.class);
        return query.getResultList();
    }
}
