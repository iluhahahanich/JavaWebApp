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

    public static final String daoType = "postgre";
    public static final String appDir = "../webapps/UPweb/";

    private Dao<T, String> dao;

    public ServiceLayer(Class<T> clazz) {
        initDao(clazz);
    }

    public void delete(String key) {
        synchronized (Dao.class) {
            try {
                dao.delete(key);
            } catch (ReadWriteException e){
                throw new ServiceLayerException(e);
            }
        }
    }

    public void create(T obj) {
        synchronized (Dao.class) {
            try {
                dao.create(obj);
            } catch (ReadWriteException e){
                throw new ServiceLayerException(e);
            }
        }
    }

    public List<T> readAll() {
        synchronized (Dao.class) {
            try {
                return dao.readAll();
            } catch (ReadWriteException e){
                throw new ServiceLayerException(e);
            }
        }
    }

    public T read(String key) {
        synchronized (Dao.class) {
            try {
                return dao.read(key);
            } catch (ReadWriteException e){
                throw new ServiceLayerException(e);
            }
        }
    }

    public void update(T obj) {
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
            case "xml" -> {
                dao = new XmlDao<>(appDir + "data/in_" + clazz.getSimpleName() + ".xml", clazz);
            }
            case "json" -> {
                dao = new JsonDao<>(appDir + "data/in_" + clazz.getSimpleName() + ".json", clazz);
            }
            case "csv" -> {
                dao = new CsvDao<>(appDir + "data/in_" + clazz.getSimpleName() + ".csv", clazz);
            }
            case "postgre" -> {
                dao = new PostgreSqlDao<>(clazz);
            }
        }

        dao = getLogged(dao);
    }

    private Dao<T, String> getLogged(Dao<T, String> dao){
        return (Dao<T, String>) Proxy.newProxyInstance(
                LoggingProxyHandler.class.getClassLoader(),
                new Class[]{Dao.class},
                new LoggingProxyHandler<>(dao, new File(appDir + "log.txt")));
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
}
