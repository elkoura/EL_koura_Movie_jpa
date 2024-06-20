package App;


import jakarta.persistence.*;

public class DatabaseConnectionTest {
    private static final String PERSISTENCE_UNIT_NAME = "filmsPU";
    private static EntityManagerFactory emf;

    public static void main(String[] args) {
        try {
            // Initialiser EntityManagerFactory
            emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
            EntityManager em = emf.createEntityManager();

            // Tester la connexion
            testConnection(em);

            // Fermer l'EntityManager et EntityManagerFactory
            em.close();
            emf.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void testConnection(EntityManager em) {
        try {
            em.getTransaction().begin();
            em.createNativeQuery("SELECT 1").getSingleResult();
            em.getTransaction().commit();
            System.out.println("La connexion à la base de données est réussie.");
        } catch (Exception e) {
            System.err.println("Échec de la connexion à la base de données.");
            e.printStackTrace();
        }
    }
}
