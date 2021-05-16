package app;

import dao.CsvDao;
import dao.Dao;
import dao.JsonDao;
import dao.XmlDao;
import exceptions.ServiceLayerException;
import app.LoggingProxyHandler;
import exceptions.ReadWriteException;
import models.AgeGroup;
import models.SportEvent;

import java.io.File;
import java.lang.reflect.Proxy;
import java.time.DayOfWeek;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ServiceLayer<T> {

    public static final String daoType = "json";
    public static final String appDir = "../webapps/UPweb/";

    private Dao<T> readDao, writeDao;

    public ServiceLayer(Class<T> clazz) {
        initDao(clazz);
    }

    public List<T> read() {
        synchronized (Dao.class) {
            try {
                return readDao.read();
            } catch (ReadWriteException e) {
                throw new ServiceLayerException(e);
            }
        }
    }

    public void write(List<T> data) {
        synchronized (Dao.class) {
            try {
                writeDao.write(data);
            } catch (ReadWriteException e) {
                throw new ServiceLayerException(e);
            }
        }
    }

    private void initDao(Class<T> clazz){
        switch (daoType) {
            case "xml" -> {
                readDao = new XmlDao<T>(appDir + "data/in_" + clazz.getSimpleName() + ".xml", clazz);
                writeDao = new XmlDao<T>(appDir + "data/in_" + clazz.getSimpleName() + ".xml", clazz);
            }
            case "json" -> {
                readDao = new JsonDao<T>(appDir + "data/in_" + clazz.getSimpleName() + ".json", clazz);
                writeDao = new JsonDao<T>(appDir + "data/in_" + clazz.getSimpleName() + ".json", clazz);
            }
            case "csv" -> {
                readDao = new CsvDao<T>(appDir + "data/in_" + clazz.getSimpleName() + ".csv", clazz);
                writeDao = new CsvDao<T>(appDir + "data/in_" + clazz.getSimpleName() + ".csv", clazz);
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

    public List<?> getSortedByAgeGroupAttendance(List<? extends SportEvent> events, AgeGroup group){
        return events.stream()
                .sorted(Comparator.comparingInt(e -> e.getAttendance().getAttendanceByAgeGroup(group)))
                .collect(Collectors.toList());
    }

    public int getAverageByDayOfWeek(List<? extends SportEvent> events, DayOfWeek dayOfWeek){
        var attendance = events.stream()
                .filter(e -> dayOfWeek.equals(DayOfWeek.of(e.getDate().toGregorianCalendar().get(Calendar.DAY_OF_WEEK))))
                .mapToInt(e -> e.getAttendance().getTotalAttendance())
                .toArray();
        return attendance.length != 0 ? Arrays.stream(attendance).sum() / attendance.length : 0;
    }
}
