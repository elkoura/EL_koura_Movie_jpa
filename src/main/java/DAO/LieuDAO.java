package DAO;

import entities.Lieu;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

public class LieuDAO {

    public static Lieu ajouterLieuPersonnes(String ville, EntityManager em) {
        TypedQuery<Lieu> query = em.createQuery("SELECT l FROM Lieu l WHERE l.ville = :ville", Lieu.class);
        query.setParameter("ville", ville);
        Lieu lieu = query.getResultStream().findFirst().orElse(null);

        if (lieu == null) {
            lieu = new Lieu();
            lieu.setVille(ville);
            // Optional: Set detailsSup and pays if available
            em.getTransaction().begin();
            em.persist(lieu);
            em.getTransaction().commit();
        }

        return lieu;
    }
}
