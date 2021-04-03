package models.exceptions;

public class ReadWriteException extends Exception {
    public ReadWriteException() {}

    public ReadWriteException(String message) {
        super(message);
    }

    public ReadWriteException(Exception e){
        super(e.getMessage(), e.getCause());
    }
}
