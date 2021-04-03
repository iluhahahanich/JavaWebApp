package service;

import clients.CsvDao;
import clients.Dao;
import clients.JsonDao;
import clients.XmlDao;
import handlers.LoggingProxyHandler;
import handlers.SportEventsHandler;
import models.exceptions.ReadWriteException;
import models.sportEvents.SportEvent;

import java.io.File;
import java.lang.reflect.Proxy;
import java.util.List;


// TODO: thread safety
// TODO: ID

public class ServiceLayer<T> {

    public static final String daoType = "csv";
    public static final String appDir = "../webapps/UPweb/";

    private static SportEventsHandler handler = new SportEventsHandler();
    private Dao<T> readDao, writeDao;

    public ServiceLayer(Class<T> clazz) {
        initDao(clazz);
    }

    public List<T> read() {
        synchronized (Dao.class) {
            try {
                return readDao.read();
            } catch (ReadWriteException e) {
                System.out.println(e.getMessage());
                e.printStackTrace();
            }
        }
        return null;
    }

    public List<T> write(List<T> data) {
        synchronized (Dao.class) {
            try {
                writeDao.write(data);
            } catch (ReadWriteException e) {
                System.out.println(e.getMessage());
                e.printStackTrace();
            }
        }
        return null;
    }

    private void initDao(Class<T> clazz){
        switch (daoType) {
            case "xml" -> {
                readDao = new XmlDao<T>(appDir + "data/in_" + clazz.getSimpleName() + "s.xml", clazz);
                writeDao = new XmlDao<T>(appDir + "data/out_" + clazz.getSimpleName() + "s.xml", clazz);
            }
            case "json" -> {
                readDao = new JsonDao<T>(appDir + "data/in_" + clazz.getSimpleName() + "s.json", clazz);
                writeDao = new JsonDao<T>(appDir + "data/out_" + clazz.getSimpleName() + "s.json", clazz);
            }
            case "csv" -> {
                readDao = new CsvDao<T>(appDir + "data/in_" + clazz.getSimpleName() + "s.csv", clazz);
                writeDao = new CsvDao<T>(appDir + "data/out_" + clazz.getSimpleName() + "s.csv", clazz);
            }
        }

        readDao = getLogged(readDao);
        writeDao = getLogged(writeDao);
    }

    private Dao<T> getLogged(Dao<T> dao){
        return (Dao<T>) Proxy.newProxyInstance(
                LoggingProxyHandler.class.getClassLoader(),
                new Class[]{Dao.class},
                new LoggingProxyHandler<>(dao, new File(appDir + "log.txt")));
    }

}
