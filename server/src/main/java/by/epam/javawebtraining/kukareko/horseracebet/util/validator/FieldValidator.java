package by.epam.javawebtraining.kukareko.horseracebet.util.validator;

import by.epam.javawebtraining.kukareko.horseracebet.model.exception.logical.IncorrectInputParamException;
import by.epam.javawebtraining.kukareko.horseracebet.util.ConfigurationManager;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * @author Yulya Kukareko
 * @version 1.0 06 May 2019
 */
public class FieldValidator {

    private static final String INCORRECT_INPUT_PARAMS_MESSAGE = "inputParametersIncorrectMessage";

    private static ConfigurationManager configurationManager;

    static {
        configurationManager = ConfigurationManager.getInstance();
    }

    public static void validateEmail(String data) throws IncorrectInputParamException {
        validateString(data, "emailRegExp", "incorrectEmailMessage");
    }

    public static void validatePassword(String data) throws IncorrectInputParamException {
        validateString(data, "passwordRegExp", "incorrectPasswordMessage");
    }

    public static void validateName(String data) throws IncorrectInputParamException {
        validateString(data, "nameRegExp", INCORRECT_INPUT_PARAMS_MESSAGE);
    }

    private static void validateString(String data, String regexp, String message) throws IncorrectInputParamException {
        String validateNameRegExp = configurationManager.getProperty(regexp);
        String incorrectParamsMes = configurationManager.getProperty(message);

        if (data == null || !data.matches(validateNameRegExp)) {
            throw new IncorrectInputParamException(incorrectParamsMes);
        }
    }

    public static void validateId(long data) throws IncorrectInputParamException {
        String incorrectParamsMes = configurationManager.getProperty(INCORRECT_INPUT_PARAMS_MESSAGE);

        if (data < 1) {
            throw new IncorrectInputParamException(incorrectParamsMes);
        }
    }

    public static void validateSecondHorseId(long data) throws IncorrectInputParamException {
        validateNumber(data, INCORRECT_INPUT_PARAMS_MESSAGE);
    }

    public static void validateMoney(BigDecimal data) throws IncorrectInputParamException {
        String incorrectSumMes = configurationManager.getProperty("incorrectSumMessage");

        if (data.signum() < 0) {
            throw new IncorrectInputParamException(incorrectSumMes);
        }
    }

    public static void validateCoefficient(float data) throws IncorrectInputParamException {
        validateNumber(data, "incorrectCoefficientMessage");
    }

    public static void validateWeightOrDistance(float data) throws IncorrectInputParamException {
        validateNumber(data, INCORRECT_INPUT_PARAMS_MESSAGE);
    }

    public static void validatePlace(int data) throws IncorrectInputParamException {
        validateNumber(data, "incorrectPlaceNumberMessage");
    }

    private static void validateNumber(float data, String message) throws IncorrectInputParamException {
        String incorrectPlaceNumberMes = configurationManager.getProperty(message);

        if (data < 0) {
            throw new IncorrectInputParamException(incorrectPlaceNumberMes);
        }
    }

    public static void validateRaceTime(Timestamp time) throws IncorrectInputParamException {
        String incorrectTimeMes = configurationManager.getProperty("incorrectTimeMessage");

        if (time.before(new Timestamp(System.currentTimeMillis()))) {
            throw new IncorrectInputParamException(incorrectTimeMes);
        }
    }

    public static void validateEnum(Object obj) throws IncorrectInputParamException {
        String incorrectParamsMes = configurationManager.getProperty(INCORRECT_INPUT_PARAMS_MESSAGE);

        if (obj == null) {
            throw new IncorrectInputParamException(incorrectParamsMes);
        }
    }
}