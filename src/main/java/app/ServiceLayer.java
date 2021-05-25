package app;

import dao.*;
import exceptions.ServiceLayerException;
import exceptions.ReadWriteException;
import models.AgeGroup;
import models.SportEvent;

import java.io.File;
import java.lang.reflect.Proxy;
import java.time.DayOfWeek;
import java.util.*;
import java.util.stream.Collectors;

public class ServiceLayer<T extends Identifiable<String>> {

    private static DaoType daoType = DaoType.MONGO;
    public static final String appDir = "../webapps/UPweb/";

    private Dao<T, String> dao;

    private final Class<T> clazz;
    public ServiceLayer(Class<T> clazz) {
        this.clazz = clazz;
    }

    public void delete(String key) {
        initDao(clazz);
        synchronized (Dao.class) {
            try {
                dao.delete(key);
            } catch (ReadWriteException e){
                throw new ServiceLayerException(e);
            }
        }
    }

    public void create(T obj) {
        initDao(clazz);
        synchronized (Dao.class) {
            try {
                dao.create(obj);
            } catch (ReadWriteException e){
                throw new ServiceLayerException(e);
            }
        }
    }

    public List<T> readAll() {
        initDao(clazz);
        synchronized (Dao.class) {
            try {
                return dao.readAll();
            } catch (ReadWriteException e){
                throw new ServiceLayerException(e);
            }
        }
    }

    public T read(String key) {
        initDao(clazz);
        synchronized (Dao.class) {
            try {
                return dao.read(key);
            } catch (ReadWriteException e){
                throw new ServiceLayerException(e);
            }
        }
    }

    public void update(T obj) {
        initDao(clazz);
        synchronized (Dao.class) {
            try {
                dao.update(obj);
            } catch (ReadWriteException e){
                throw new ServiceLayerException(e);
            }
        }
    }

    private void initDao(Class<T> clazz){
        switch (daoType) {
            case XML -> dao = new XmlDao<>(appDir + "data/in_" + clazz.getSimpleName() + ".xml", clazz);
            case JSON -> dao = new JsonDao<>(appDir + "data/in_" + clazz.getSimpleName() + ".json", clazz);
            case CSV -> dao = new CsvDao<>(appDir + "data/in_" + clazz.getSimpleName() + ".csv", clazz);
            case MYSQL -> dao = new MySqlDao<>(clazz);
            case MONGO -> dao = new MongoDbDao<>(clazz);
        }

         dao = getLogged(dao);
    }

    private Dao<T, String> getLogged(Dao<T, String> dao){
        return (Dao<T, String>) Proxy.newProxyInstance(
                LoggingProxyHandler.class.getClassLoader(),
                new Class[]{Dao.class},
                new LoggingProxyHandler<>(dao, new File(appDir + "data/log.txt")));
    }

    public List<?> getSortedByAgeGroupAttendance(List<? extends SportEvent> events, AgeGroup group){
        return events.stream()
                .sorted(Comparator.comparingInt(e -> e.getAttendance().getAttendanceByAgeGroup(group)))
                .collect(Collectors.toList());
    }

    public int getAverageByDayOfWeek(List<? extends SportEvent> events, DayOfWeek dayOfWeek){
        var attendance = events.stream()
                .filter(e -> dayOfWeek.equals(DayOfWeek.of(e.getDate().getDay())))
                .mapToInt(e -> e.getAttendance().getTotalAttendance())
                .toArray();
        return attendance.length != 0 ? Arrays.stream(attendance).sum() / attendance.length : 0;
    }

    public static void setDaoType(DaoType daoType) {
        ServiceLayer.daoType = daoType;
    }

    public static DaoType getDaoType() {
        return daoType;
    }
}
