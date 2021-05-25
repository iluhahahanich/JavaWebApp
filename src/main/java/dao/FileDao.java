package dao;

import app.Identifiable;
import exceptions.ReadWriteException;

import java.util.List;

public abstract class FileDao<T extends Identifiable<K>, K> implements Dao<T, K> {
    protected String filename = null;

    protected Class<T> clazz = null;

    public FileDao() { }

    protected FileDao(String filename, Class<T> clazz) {
        this.filename = filename;
        this.clazz = clazz;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public Class<T> getClazz() {
        return clazz;
    }

    abstract List<T> read() throws ReadWriteException;
    abstract void write(List<T> data) throws ReadWriteException;

    @Override
    public void create(T obj) throws ReadWriteException {
        var data = read();
        data.add(obj);
        write(data);
    }

    @Override
    public List<T> readAll() throws ReadWriteException {
        return read();
    }

    @Override
    public T read(K key) throws ReadWriteException {
        var data = read();
        return data.stream().filter(e -> e.getId().equals(key)).findFirst().get();
    }

    @Override
    public void delete(K key) throws ReadWriteException {
        var data = read();
        data.removeIf(e -> e.getId().equals(key));
        write(data);
    }

    @Override
    public void update(T obj) throws ReadWriteException {
        var data = readAll();
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).getId().equals(obj.getId())){
                data.set(i, obj);
                break;
            }
        }
        write(data);
    }
}
