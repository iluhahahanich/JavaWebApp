package dao;

import app.Identifiable;
import exceptions.ReadWriteException;

import java.util.List;

public interface Dao<T extends Identifiable<K>, K> {
    void create(T obj) throws ReadWriteException;
    List<T> readAll() throws ReadWriteException;
    T read(K key) throws ReadWriteException;
    void delete(K key) throws ReadWriteException;
    void update(T obj) throws ReadWriteException;
}
