package by.epam.javawebtraining.kukareko.horseracebet.model.exception;

/**
 * @author Yulya Kukareko
 * @version 1.0 11 Apr 2019
 */
public class HorseraceBetException extends Exception {

    public HorseraceBetException() {
    }

    public HorseraceBetException(String message) {
        super(message);
    }

    public HorseraceBetException(Throwable cause) {
        super(cause);
    }
}
