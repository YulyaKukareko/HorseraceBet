package by.epam.javawebtraining.kukareko.horseracebet.model.exception;

/**
 * @author Yulya Kukareko
 * @version 1.0 11 Apr 2019
 */
public class HorseRaceBetException extends Exception {

    public HorseRaceBetException() {
    }

    public HorseRaceBetException(String message) {
        super(message);
    }

    public HorseRaceBetException(Throwable cause) {
        super(cause);
    }
}
