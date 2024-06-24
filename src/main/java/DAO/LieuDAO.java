package DAO;

import entities.Lieu;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;

public class LieuDAO {
    private EntityManager em;

    public LieuDAO(EntityManager em) {
        this.em = em;
    }

    public Lieu trouverOuCreerLieu(String ville, String pays) {
        TypedQuery<Lieu> query = em.createQuery("SELECT l FROM Lieu l WHERE l.ville = :ville AND l.pays = :pays", Lieu.class);
        query.setParameter("ville", ville);
        query.setParameter("pays", pays);
        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            Lieu lieu = new Lieu(ville, pays);
            em.getTransaction().begin();
            em.persist(lieu);
            em.getTransaction().commit();
            return lieu;
        }
    }

    public static Lieu ajouterLieuPersonnes(String ville, EntityManager em) {
        Lieu lieu;
        try {
            TypedQuery<Lieu> query = em.createQuery("SELECT l FROM Lieu l WHERE l.ville = :ville", Lieu.class);
            query.setParameter("ville", ville);
            lieu = query.getSingleResult();
        } catch (NoResultException e) {
            lieu = new Lieu();
            lieu.setVille(ville);
            em.getTransaction().begin();
            em.persist(lieu);
            em.getTransaction().commit();
        }
        return lieu;
    }
}
