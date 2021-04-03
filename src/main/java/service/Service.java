package service;

import clients.CsvDao;
import clients.Dao;
import clients.JsonDao;
import clients.XmlDao;
import handlers.LoggingProxyHandler;
import handlers.SportEventsHandler;
import models.exceptions.ReadWriteException;

import java.io.File;
import java.lang.reflect.Proxy;
import java.util.List;


// TODO: thread safety
// TODO: ID

public class Service {

    public static final String daoType = "csv";
    public static String appDir = "../webapps/UPweb/";
    public static SportEventsHandler handler = new SportEventsHandler();

    public static Dao readDao, writeDao;

    public static List<?> read(Class<?> clazz) {
        synchronized (Dao.class) {
            initDao(clazz);

            try {
                return readDao.read();
            } catch (ReadWriteException e) {
                System.out.println(e.getMessage());
                e.printStackTrace();
            }
        }
        return null;
    }

    private static void initDao(Class<?> clazz){
        switch (daoType) {
            case "xml" -> {
                readDao = new XmlDao<>(appDir + "data/in_" + clazz.getSimpleName() + "s.xml", clazz);
                writeDao = new XmlDao<>(appDir + "data/out_" + clazz.getSimpleName() + "s.xml", clazz);
            }
            case "json" -> {
                readDao = new JsonDao<>(appDir + "data/in_" + clazz.getSimpleName() + "s.json", clazz);
                writeDao = new JsonDao<>(appDir + "data/out_" + clazz.getSimpleName() + "s.json", clazz);
            }
            case "csv" -> {
                readDao = new CsvDao<>(appDir + "data/in_" + clazz.getSimpleName() + "s.csv", clazz);
                writeDao = new CsvDao<>(appDir + "data/out_" + clazz.getSimpleName() + "s.csv", clazz);
            }
            default -> {
                System.out.println(daoType + " is non-existing dao type!");
            }
        }

        readDao = (Dao) Proxy.newProxyInstance(
                LoggingProxyHandler.class.getClassLoader(),
                new Class[]{Dao.class},
                new LoggingProxyHandler<>(readDao, new File(appDir + "log.txt")));

        writeDao = (Dao) Proxy.newProxyInstance(
                LoggingProxyHandler.class.getClassLoader(),
                new Class[]{Dao.class},
                new LoggingProxyHandler<>(writeDao, new File(appDir + "log.txt")));
    }

}
