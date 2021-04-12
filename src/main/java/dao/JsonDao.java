package dao;

import com.fasterxml.jackson.databind.ObjectMapper;
import exceptions.ReadWriteException;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class JsonDao<T> extends FileDao<T> {
    public JsonDao(String filename, Class<T> clazz) {
        super(filename, clazz);
    }

    public JsonDao() {}

    @Override
    public List<T> read() throws ReadWriteException {
        try {
            var mapper = new ObjectMapper();
            var reader = mapper.readerForListOf(clazz);

            @SuppressWarnings("unchecked")
            var events = (List<T>) reader.readValue(new File(filename));
            return events;
        } catch (IOException e) {
            throw new ReadWriteException(e);
        }
    }

    @Override
    public void write(List<T> data) throws ReadWriteException {
        try {
            var mapper = new ObjectMapper();
            mapper.writeValue(new File(filename), data);
        } catch (IOException e) {
            throw new ReadWriteException(e);
        }
    }
}
