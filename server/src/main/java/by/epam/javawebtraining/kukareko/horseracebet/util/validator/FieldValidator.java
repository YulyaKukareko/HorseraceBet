package by.epam.javawebtraining.kukareko.horseracebet.util.validator;

import static by.epam.javawebtraining.kukareko.horseracebet.util.constant.RegexpConstant.*;
import static by.epam.javawebtraining.kukareko.horseracebet.util.constant.ExceptionMessageConstant.*;

import by.epam.javawebtraining.kukareko.horseracebet.model.exception.logical.IncorrectInputParamException;
import by.epam.javawebtraining.kukareko.horseracebet.util.ConfigurationManager;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * @author Yulya Kukareko
 * @version 1.0 06 May 2019
 */
public class FieldValidator {

    private static ConfigurationManager configurationManager;

    static {
        configurationManager = ConfigurationManager.getInstance();
    }

    /**
     * @param data
     * @throws IncorrectInputParamException
     */
    public static void validateEmail(String data) throws IncorrectInputParamException {
        validateString(data, EMAIL_REG_EXP, INCORRECT_EMAIL_MESSAGE);
    }

    /**
     * @param data
     * @throws IncorrectInputParamException
     */
    public static void validatePassword(String data) throws IncorrectInputParamException {
        validateString(data, PASSWORD_REG_EXP, INCORRECT_PASSWORD_MESSAGE);
    }

    /**
     * @param data
     * @throws IncorrectInputParamException
     */
    public static void validateName(String data) throws IncorrectInputParamException {
        validateString(data, NAME_REG_EXP, INPUT_PARAMETERS_INCORRECT_MESSAGE);
    }

    private static void validateString(String data, String regexp, String message) throws IncorrectInputParamException {
        String validateNameRegExp = configurationManager.getProperty(regexp);
        String incorrectParamsMes = configurationManager.getProperty(message);

        if (data == null || !data.matches(validateNameRegExp)) {
            throw new IncorrectInputParamException(incorrectParamsMes);
        }
    }

    /**
     * @param data
     * @throws IncorrectInputParamException
     */
    public static void validateId(long data) throws IncorrectInputParamException {
        String incorrectParamsMes = configurationManager.getProperty(INPUT_PARAMETERS_INCORRECT_MESSAGE);

        if (data < 1) {
            throw new IncorrectInputParamException(incorrectParamsMes);
        }
    }

    /**
     * @param data
     * @throws IncorrectInputParamException
     */
    public static void validateSecondHorseId(long data) throws IncorrectInputParamException {
        validateNumber(data, INPUT_PARAMETERS_INCORRECT_MESSAGE);
    }

    /**
     * @param data
     * @throws IncorrectInputParamException
     */
    public static void validateMoney(BigDecimal data) throws IncorrectInputParamException {
        String incorrectSumMes = configurationManager.getProperty(INCORRECT_SUM_MESSAGE);

        if (data.signum() < 0) {
            throw new IncorrectInputParamException(incorrectSumMes);
        }
    }

    /**
     * @param data
     * @throws IncorrectInputParamException
     */
    public static void validateCoefficient(float data) throws IncorrectInputParamException {
        validateNumber(data, INCORRECT_COEFFICIENT_MESSAGE);
    }

    /**
     * @param data
     * @throws IncorrectInputParamException
     */
    public static void validateWeightOrDistance(float data) throws IncorrectInputParamException {
        validateNumber(data, INPUT_PARAMETERS_INCORRECT_MESSAGE);
    }

    /**
     * @param data
     * @throws IncorrectInputParamException
     */
    public static void validatePlace(int data) throws IncorrectInputParamException {
        validateNumber(data, INCORRECT_PLACE_NUMBER_MESSAGE);
    }

    private static void validateNumber(float data, String message) throws IncorrectInputParamException {
        String incorrectPlaceNumberMes = configurationManager.getProperty(message);

        if (data < 0) {
            throw new IncorrectInputParamException(incorrectPlaceNumberMes);
        }
    }

    /**
     * @param time
     * @throws IncorrectInputParamException
     */
    public static void validateRaceTime(Timestamp time) throws IncorrectInputParamException {
        String incorrectTimeMes = configurationManager.getProperty(INCORRECT_TIME_MESSAGE);

        if (time.before(new Timestamp(System.currentTimeMillis()))) {
            throw new IncorrectInputParamException(incorrectTimeMes);
        }
    }

    /**
     * @param obj
     * @throws IncorrectInputParamException
     */
    public static void validateEnum(Object obj) throws IncorrectInputParamException {
        String incorrectParamsMes = configurationManager.getProperty(INPUT_PARAMETERS_INCORRECT_MESSAGE);

        if (obj == null) {
            throw new IncorrectInputParamException(incorrectParamsMes);
        }
    }
}