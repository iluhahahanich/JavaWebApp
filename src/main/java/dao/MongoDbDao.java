package dao;

import app.Identifiable;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import dev.morphia.Datastore;
import dev.morphia.Morphia;
import exceptions.ReadWriteException;
import org.bson.types.ObjectId;

import java.util.*;

public class MongoDbDao<T extends Identifiable<String>> implements Dao<T, String> {

    private final Morphia morphia = new Morphia();
    private final MongoClient db = new MongoClient(new MongoClientURI("mongodb://mongodb:27017"));
    private final Datastore ds = morphia.createDatastore(db, "up_db");

    protected Class<T> clazz = null;

    public MongoDbDao(Class<T> clazz) {
        this.clazz = clazz;
        morphia.map(clazz);
    }

    @Override
    public void create(T obj) throws ReadWriteException {
        ds.save(obj);
    }

    @Override
    public List<T> readAll() throws ReadWriteException {
        return ds.find(clazz).into(new ArrayList<>());
    }

    @Override
    public T read(String key) throws ReadWriteException {
        return ds.createQuery(clazz)
                .field("_id")
                .equal(new ObjectId(key))
                .first();
    }

    @Override
    public void delete(String key) throws ReadWriteException {
        var query = ds.createQuery(clazz)
                .field("_id")
                .equal(new ObjectId(key));

        ds.delete(query);
    }

    @Override
    public void update(T obj) throws ReadWriteException {

        Map<String, Object> fields = getFields(obj);

        var query = ds.createQuery(clazz)
                .field("_id")
                .equal(new ObjectId(obj.getId()));

        var upd = ds.createUpdateOperations(clazz);
        fields.forEach(upd::set);

        ds.update(query, upd);
    }

    private Map<String, Object> getFields(Object obj) {
        var map = new HashMap<String, Object>();
        for (var clss: getSuperclasses(clazz)){
            for (var field : clss.getDeclaredFields()) {
                if (field.getName().equals("id")) {
                    continue;
                }
                field.setAccessible(true);
                try {
                    map.put(field.getName(), field.get(obj));
                } catch (IllegalAccessException ignored) {
                }
            }
        }
        return map;
    }

    private List<Class<?>> getSuperclasses(Class<?> claz){
        List<Class<?>> classes = new LinkedList<>();
        Class<?> clss = claz;
        while (clss.getSuperclass() != null){
            classes.add(clss);
            clss = clss.getSuperclass();
        }
        Collections.reverse(classes);
        return classes;
    }
}
