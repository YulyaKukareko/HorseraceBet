package by.epam.javawebtraining.kukareko.horseracebet.model.exception.technical;

/**
 * @author Yulya Kukareko
 * @version 1.0 11 Апр. 2019
 */
public class InvalidDriverNameException extends HorseBetTechnicalException {

    public InvalidDriverNameException(ClassNotFoundException cause) {
        super(cause);
    }
}
