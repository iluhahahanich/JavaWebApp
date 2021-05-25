package dao;

import app.Identifiable;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import java.util.List;

public class MySqlDao<T extends Identifiable<String>> implements Dao<T, String> {

    private EntityManager em = Persistence.createEntityManagerFactory("MYSQL").createEntityManager();

    protected Class<T> clazz = null;

    public MySqlDao(Class<T> clazz) {
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

    // FIXME: getting outdated data
    public List<T> readAll() {
//        CriteriaBuilder cb = em.getCriteriaBuilder();
//        CriteriaQuery<T> cq = cb.createQuery(clazz);
//        Root<T> rootEntry = cq.from(clazz);
//        CriteriaQuery<T> all = cq.select(rootEntry);
//
//        TypedQuery<T> allQuery = em.createQuery(all);
//        return allQuery.getResultList();

//        List<T> list;
//        try {
//            if (!em.getTransaction().isActive()) {
//                em.getTransaction().begin();
//            }
//            list = em.createQuery("FROM " + this.clazz.getSimpleName()).getResultList();
//            em.getTransaction().commit();
//
//        } catch (HibernateException e) {
//            em.getTransaction().rollback();
//            throw e;
//        } finally {
////            em.close();
//        }
//        return list;


//        TypedQuery<T> namedQuery = em.createNamedQuery(this.clazz.getSimpleName() + ".getAll", this.clazz);
//        var query = em.createQuery("SELECT c FROM " + this.clazz.getSimpleName() + " c");

        var query = em.createQuery("FROM " + this.clazz.getSimpleName(), clazz);
        return query.getResultList();
    }
}
