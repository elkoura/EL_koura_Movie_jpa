package DAO;

import entities.Acteur;
import entities.DataMissingException;
import entities.Film;
import entities.Role;
import entities.RoleId;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
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

    public Role findById(RoleId id) {
        return em.find(Role.class, id);
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
                    continue; // Skip header or empty lines
                }

                String[] values = line.split(";");
                if (values.length < 3) {
                    continue; // Skip lines with insufficient data
                }

                RoleId roleId = new RoleId(values[0], values[1]);

                Role role = new Role();
                role.setId(roleId);
                role.setPersonnage(values[2]);
                rolesList.add(role);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return rolesList;
    }

    public void insererRoles(List<Role> roles) throws DataMissingException {
        em.getTransaction().begin();
        try {
            for (Role role : roles) {
                if (!isRoleInDB(role)) {
                    Film film = FilmDAO.getFilmByImdbId(role.getId().getFilmId(), em);
					Acteur acteur = ActeurDAO.getActeurByIMDB(role.getId().getActeurId(), em);
					role.setFilm(film);
					role.setActeur(acteur);
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
                "SELECT r FROM Role r WHERE r.id.filmId = :filmId AND r.id.acteurId = :acteurId AND r.personnage = :personnage",
                Role.class);
        query.setParameter("filmId", role.getId().getFilmId());
        query.setParameter("acteurId", role.getId().getActeurId());
        query.setParameter("personnage", role.getPersonnage());
        return !query.getResultList().isEmpty();
    }

    public static List<Role> extractingRoles(EntityManager em) {
        TypedQuery<Role> query = em.createQuery("SELECT r FROM Role r", Role.class);
        return query.getResultList();
    }

    public static boolean isRoleInDB(List<Role> roles, String[] tab) {
        for (Role item : roles) {
            if (item.getActeur().getIdImdb().equals(tab[1]) && item.getFilm().getIdImdb().equals(tab[0])
                    && item.getPersonnage().equals(tab[2])) {
                return true;
            }
        }
        return false;
    }

    public static void setRolesFromList(List<String> listeRoles, EntityManager em) {
        Iterator<String> iterator = listeRoles.iterator();
        while (iterator.hasNext()) {
            String ligneCourante = iterator.next();
            String[] tab = ligneCourante.split(";", -1);
            List<Role> roles = extractingRoles(em);
            if (!isRoleInDB(roles, tab)) {
                try {
                    Film film = FilmDAO.getFilmByImdbId(tab[0], em);
                    Acteur acteur = ActeurDAO.getActeurByIMDB(tab[1], em);
                    Role role = new Role(film, acteur, tab[2]);
                    em.persist(role);
                } catch (DataMissingException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
