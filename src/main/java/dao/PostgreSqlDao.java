package dao;

import app.Identifiable;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import java.util.List;

public class PostgreSqlDao<T extends Identifiable<String>> implements Dao<T, String> {

    public EntityManager em = Persistence.createEntityManagerFactory("POSTGRE").createEntityManager();

    protected Class<T> clazz = null;

    public PostgreSqlDao(Class<T> clazz) {
        this.clazz = clazz;
    }

    public void create(T obj) {
        try {
            if (!em.getTransaction().isActive()) {
                em.getTransaction().begin();
            }
            em.persist(obj);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        }
//        em.getTransaction().begin();
//        em.merge(obj);
//        em.getTransaction().commit();
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

    public T read(String key) {
        return em.find(this.clazz, key);
    }

    public void update(T obj) {
        em.getTransaction().begin();
        em.merge(obj);
        em.getTransaction().commit();
    }

    // TODO: fix
    public List<T> readAll() {
//        TypedQuery<T> namedQuery = em.createNamedQuery(this.clazz.getSimpleName() + ".getAll", this.clazz);
//        var query = em.createQuery("SELECT c FROM " + this.clazz.getSimpleName() + " c");
        var query = em.createQuery("FROM " + this.clazz.getSimpleName());
        return query.getResultList();
    }
}
