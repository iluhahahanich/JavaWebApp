package dao;

import models.Competition;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.util.List;

public class PostgreSqlCompetitionDao implements Dao<Competition, String> {

    public EntityManager em = Persistence.createEntityManagerFactory("POSTGRE").createEntityManager();

    public void create(Competition obj) {
        em.getTransaction().begin();
        em.persist(obj);
        em.getTransaction().commit();
    }

    @Override
    public void delete(String key) {
        em.getTransaction().begin();
        var obj = read(key);
        if (obj != null) {
            em.remove(obj);
        }
        em.getTransaction().commit();
    }

    public Competition read(String key) {
        return em.find(Competition.class, key);
    }

    public void update(Competition car) {
        em.getTransaction().begin();
        em.merge(car);
        em.getTransaction().commit();
    }

    public List<Competition> readAll() {
        TypedQuery<Competition> namedQuery = em.createNamedQuery("Competition.getAll", Competition.class);
        return namedQuery.getResultList();
    }
}
