package clients;

import models.exceptions.ReadWriteException;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.InvocationTargetException;
import java.util.*;


/**
 * 1) requires default constructors for T and all its inner classes
 * 2) cyclic dependencies should not exist
 * @param <T> class to be read or write
 */
public class CsvDao<T> extends FileDao<T> {
    @Target(value= ElementType.FIELD)
    @Retention(value= RetentionPolicy.RUNTIME)
    public @interface Checkable {
        String patten();
    }

    private List<Class<?>> supClasses;

    public CsvDao() { }

    public CsvDao(String filename, Class<T> clazz) {
        super(filename, clazz);
        supClasses = getSuperclasses(clazz);
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

    @Override
    public List<T> read() throws ReadWriteException{
        var data = new ArrayList<T>();
        try(var in = new Scanner(new File(filename))) {
            while (in.hasNextLine()) {
                var line = new StringTokenizer(in.nextLine(), ",", false);
                var inst = clazz.getConstructor().newInstance();
                for (var clss : supClasses){
                    createClass(line, inst, clss);
                }
                data.add(inst);
            }
        } catch (IllegalAccessException | NoSuchMethodException | InstantiationException | InvocationTargetException | DatatypeConfigurationException | FileNotFoundException e) {
            throw new ReadWriteException(e);
        }
        return data;
    }

    private void createClass(StringTokenizer line, Object instance, Class<?> clss) throws IllegalAccessException, DatatypeConfigurationException, NoSuchMethodException, InvocationTargetException, InstantiationException, ReadWriteException {
        for (var field : clss.getDeclaredFields()) {
            field.setAccessible(true);
            var type = field.getType();
            if (type.equals(boolean.class) || type.equals(Boolean.class)) {
                field.setBoolean(instance, Boolean.parseBoolean(line.nextToken().strip()));
            }
            else if (type.equals(int.class) || type.equals(Integer.class)) {
                field.setInt(instance, Integer.parseInt(line.nextToken().strip()));
            }
            else if (type.equals(String.class)) {
                var value = line.nextToken().strip();
                if (field.isAnnotationPresent(Checkable.class)){
                    var ann = field.getAnnotation(Checkable.class);
                    if (!value.matches(ann.patten())){
                        throw new ReadWriteException("\"" + value + "\" does not match the pattern " + ann.patten());
                    }
                }
                field.set(instance, value);
            }
            else if (type.equals(XMLGregorianCalendar.class)) {
                field.set(instance, DatatypeFactory.newInstance().newXMLGregorianCalendar(line.nextToken().strip()));
            }
            else {
                var fieldInstance = type.getConstructor().newInstance();
                for (var supClasses : getSuperclasses(type)){
                    createClass(line, fieldInstance, type);
                }
                field.set(instance, fieldInstance);
            }
        }
    }

    @Override
    public void write(List<T> data) throws ReadWriteException {
        try(var out = new FileWriter(filename)) {
            for (T obj : data) {
                for (var clss : supClasses) {
                    printFields(out, obj, clss);
                }
                out.write('\n');
            }
        } catch (IOException | IllegalAccessException e) {
            throw new ReadWriteException(e);
        }

    }

    private void printFields(FileWriter out, Object instance, Class<?> clss) throws IllegalAccessException, IOException {
        for (var field : clss.getDeclaredFields()) {
            field.setAccessible(true);
            var type = field.getType();
            if (type.equals(boolean.class) || type.equals(Boolean.class) ||
                    type.equals(int.class) || type.equals(Integer.class) ||
                    type.equals(String.class)) {
                out.write(field.get(instance) + ", ");
            }
            else if (type.equals(XMLGregorianCalendar.class)) {
                var date = ((XMLGregorianCalendar) field.get(instance));
                out.write(date.getYear() + "-" +
                        (date.getMonth() < 10 ? "0" : "") + date.getMonth() + "-" +
                        (date.getDay() < 10 ? "0" : "") + date.getDay() + ", ");
            }
            else {
                printFields(out, field.get(instance), type);
            }
        }
    }
}
