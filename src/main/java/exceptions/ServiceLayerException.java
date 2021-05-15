package exceptions;

public class ServiceLayerException extends RuntimeException {
    public ServiceLayerException() {
    }

    public ServiceLayerException(String message) {
        super(message);
    }

    public ServiceLayerException(Exception e) {
        super(e.getMessage(), e.getCause());
    }

}