package DAO;

import entities.Pays;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PaysDAO {

    private EntityManager em;

    public PaysDAO(EntityManager em) {
        this.em = em;
    }

    public void insert(Pays pays) {
        em.getTransaction().begin();
        em.persist(pays);
        em.getTransaction().commit();
    }

    public Pays findById(Long id) {
        return em.find(Pays.class, id);
    }

    public List<Pays> findAll() {
        TypedQuery<Pays> query = em.createQuery("SELECT p FROM Pays p", Pays.class);
        return query.getResultList();
    }

    public void update(Pays pays) {
        em.getTransaction().begin();
        em.merge(pays);
        em.getTransaction().commit();
    }

    public void delete(Pays pays) {
        em.getTransaction().begin();
        em.remove(em.contains(pays) ? pays : em.merge(pays));
        em.getTransaction().commit();
    }

    // Méthode pour lire un fichier CSV et retourner une liste de Pays
    public List<Pays> lireFichier(String filePath) {
        List<Pays> paysList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(";");
                String nomPays = values[0].trim();
                setPaysByName(nomPays, em); // Appel à la méthode existante pour ajouter le pays si nécessaire
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return paysList;
    }

    public static Pays setPaysByName(String nom, EntityManager em) {
        TypedQuery<Pays> query = em.createQuery("SELECT p FROM Pays p WHERE p.nom = :nom", Pays.class);
        query.setParameter("nom", nom.trim());
        List<Pays> resultat = query.getResultList();
        if (resultat.isEmpty()) {
            Pays pays = new Pays();
            pays.setNom(nom.trim());
            if (!em.getTransaction().isActive()) {
                em.getTransaction().begin();
            }
            em.persist(pays);
            em.getTransaction().commit();
            return pays; // Retourne l'entité Pays créée et persistée
        } else {
            return resultat.get(0); // Retourne le premier pays trouvé s'il existe déjà
        }
    }

    public static Pays getPaysByName(String nom, EntityManager em) {
        TypedQuery<Pays> query = em.createQuery("SELECT p FROM Pays p WHERE p.nom = :nom", Pays.class);
        query.setParameter("nom", nom.trim());
        List<Pays> resultat = query.getResultList();
        if (!resultat.isEmpty()) {
            return resultat.get(0);
        }
        return null;
    }
}
