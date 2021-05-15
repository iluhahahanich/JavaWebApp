package dao;

import exceptions.ReadWriteException;

import java.util.List;

public interface Dao<T> {

    List<T> read() throws ReadWriteException;

    void write(List<T> data) throws ReadWriteException;
}
