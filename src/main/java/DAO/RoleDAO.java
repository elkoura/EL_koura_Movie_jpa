package DAO;

import entities.Acteur;
import entities.Film;
import entities.Role;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RoleDAO {

    private EntityManager em;

    public RoleDAO(EntityManager em) {
        this.em = em;
    }

    public void insert(Role role) {
        em.getTransaction().begin();
        em.persist(role);
        em.getTransaction().commit();
    }

    public List<Role> findAll() {
        TypedQuery<Role> query = em.createQuery("SELECT r FROM Role r", Role.class);
        return query.getResultList();
    }

    public void update(Role role) {
        em.getTransaction().begin();
        em.merge(role);
        em.getTransaction().commit();
    }

    public void delete(Role role) {
        em.getTransaction().begin();
        em.remove(em.contains(role) ? role : em.merge(role));
        em.getTransaction().commit();
    }

    public List<Role> lireFichier(String filePath) {
        List<Role> rolesList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty() || line.startsWith("FILM;ID ACTEUR;PERSONNAGE")) {
                    continue;
                }

                String[] values = line.split(";");
                if (values.length < 3) {
                    continue;
                }

                Film film = findOrCreateFilm(values[0]);
                Acteur acteur = findOrCreateActeur(values[1]);

                Role role = new Role();
                role.setFilm(film);
                role.setActeur(acteur);
                role.setPersonnage(values[2]);

                rolesList.add(role);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return rolesList;
    }

    public void insererRoles(List<Role> roles) {
        em.getTransaction().begin();
        try {
            for (Role role : roles) {
                if (!isRoleInDB(role)) {
                    em.persist(role);
                }
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        }
    }

    private boolean isRoleInDB(Role role) {
        TypedQuery<Role> query = em.createQuery(
                "SELECT r FROM Role r WHERE r.film.idImdb = :filmId AND r.acteur.idImdb = :acteurId AND r.personnage = :personnage",
                Role.class);
        query.setParameter("filmId", role.getFilm().getIdImdb());
        query.setParameter("acteurId", role.getActeur().getIdImdb());
        query.setParameter("personnage", role.getPersonnage());
        return !query.getResultList().isEmpty();
    }

    private Film findOrCreateFilm(String idImdb) {
        TypedQuery<Film> query = em.createQuery("SELECT f FROM Film f WHERE f.idImdb = :idImdb", Film.class);
        query.setParameter("idImdb", idImdb);
        List<Film> films = query.getResultList();
        if (!films.isEmpty()) {
            return films.get(0);
        } else {
            Film film = new Film();
            film.setIdImdb(idImdb);
            em.persist(film);
            return film;
        }
    }

    private Acteur findOrCreateActeur(String idImdb) {
        TypedQuery<Acteur> query = em.createQuery("SELECT a FROM Acteur a WHERE a.idImdb = :idImdb", Acteur.class);
        query.setParameter("idImdb", idImdb);
        List<Acteur> acteurs = query.getResultList();
        if (!acteurs.isEmpty()) {
            return acteurs.get(0);
        } else {
            Acteur acteur = new Acteur();
            acteur.setIdImdb(idImdb);
            em.persist(acteur);
            return acteur;
        }
    }
}
