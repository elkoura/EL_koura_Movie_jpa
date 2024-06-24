package DAO;

import entities.Acteur;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class ActeurDAO {
    private EntityManager em;

    public ActeurDAO(EntityManager em) {
        this.em = em;
    }

    public void insert(Acteur acteur) {
        try {
            em.getTransaction().begin();
            em.persist(acteur);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
        }
    }


    public Acteur findById(Long id) {
        return em.find(Acteur.class, id);
    }

    public Acteur findByIMDB(String idImdb) {
        TypedQuery<Acteur> query = em.createQuery(
                "SELECT a FROM Acteur a WHERE a.idImdb = :idImdb", Acteur.class);
        query.setParameter("idImdb", idImdb);
        List<Acteur> result = query.getResultList();
        return result.isEmpty() ? null : result.get(0);
    }

    public List<Acteur> findAll() {
        TypedQuery<Acteur> query = em.createQuery("SELECT a FROM Acteur a", Acteur.class);
        return query.getResultList();
    }

    public void update(Acteur acteur) {
        em.getTransaction().begin();
        em.merge(acteur);
        em.getTransaction().commit();
    }

    public void delete(Acteur acteur) {
        em.getTransaction().begin();
        em.remove(em.contains(acteur) ? acteur : em.merge(acteur));
        em.getTransaction().commit();
    }
    public Acteur getActeurByIMDB(String idImdb) {
        TypedQuery<Acteur> query = em.createQuery(
                "SELECT a FROM Acteur a WHERE a.idImdb = :idImdb", Acteur.class);
        query.setParameter("idImdb", idImdb);
        List<Acteur> result = query.getResultList();
        return result.isEmpty() ? null : result.get(0);
    }

    public List<Acteur> lireFichier(String filePath) {
        List<Acteur> acteursList = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM d yyyy");

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty() || line.startsWith("IDIMDB;IDENTITE;DATE_NAISSANCE;LIEU_NAISSANCE;URL")) {
                    continue; // Skip header or empty lines
                }

                String[] values = line.split(";");
                Acteur acteur = new Acteur();
                acteur.setIdImdb(values[0]);
                acteur.setIdentite(values[1]);
                try {
                    acteur.setDateNaissance(LocalDate.parse(values[2].trim(), formatter));
                } catch (DateTimeParseException e) {
                    System.err.println("Erreur de format de date pour la ligne: " + line);
                    e.printStackTrace();
                }
                // You should set lieuNaissance here if applicable
                acteur.setUrl(values[4]); // Assuming URL is in the CSV file
                acteursList.add(acteur);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return acteursList;
    }


}
