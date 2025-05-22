package espacioFisico.utils;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EntityManagerHelper {
    private static EntityManagerFactory entityManagerFactory;

    private static final ThreadLocal<EntityManager> entityManagerHolder;


    static {    

        entityManagerHolder = new ThreadLocal<EntityManager>();
        Map<String, String> properties = new HashMap<>();
        properties.put("javax.persistence.jdbc.url", System.getenv("DB_URL"));
        properties.put("javax.persistence.jdbc.user", System.getenv("DB_USERNAME"));
        properties.put("javax.persistence.jdbc.password", System.getenv("DB_PASSWORD"));
        entityManagerFactory = Persistence.createEntityManagerFactory("espacioFisico",properties);

    }


    public static EntityManager getEntityManager() {

        EntityManager entityManager = entityManagerHolder.get();

        if (entityManager == null || !entityManager.isOpen()) {

            entityManager = entityManagerFactory.createEntityManager();

            entityManagerHolder.set(entityManager);

        }

        return entityManager;

    }


    public static void closeEntityManager() {

        EntityManager entityManager = entityManagerHolder.get();

        if (entityManager != null) {

            entityManagerHolder.set(null);

            entityManager.close();

        }

    }
}
