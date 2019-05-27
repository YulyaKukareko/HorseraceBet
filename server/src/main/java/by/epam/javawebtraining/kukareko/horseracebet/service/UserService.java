package by.epam.javawebtraining.kukareko.horseracebet.service;

import static by.epam.javawebtraining.kukareko.horseracebet.util.validator.FieldValidator.*;

import by.epam.javawebtraining.kukareko.horseracebet.dao.user.UserDAO;
import by.epam.javawebtraining.kukareko.horseracebet.dao.user.UserDAOImpl;
import by.epam.javawebtraining.kukareko.horseracebet.model.entity.Role;
import by.epam.javawebtraining.kukareko.horseracebet.model.entity.User;
import by.epam.javawebtraining.kukareko.horseracebet.model.exception.HorseRaceBetException;
import by.epam.javawebtraining.kukareko.horseracebet.model.exception.logical.DuplicationEmailException;
import by.epam.javawebtraining.kukareko.horseracebet.model.exception.logical.IncorrectInputParamException;
import by.epam.javawebtraining.kukareko.horseracebet.util.ConfigurationManager;
import by.epam.javawebtraining.kukareko.horseracebet.util.CryptMD5;
import by.epam.javawebtraining.kukareko.horseracebet.util.constant.ExceptionMessageConstant;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Yulya Kukareko
 * @version 1.0 14 Apr 2019
 */
public class UserService {

    private static final ReentrantLock LOCK = new ReentrantLock();
    private static UserService service;
    private ConfigurationManager configurationManager;

    private String duplicationEmailMessage;

    private UserDAO userDAO;

    private UserService() {
        this.userDAO = UserDAOImpl.getInstance();
        this.configurationManager = ConfigurationManager.getInstance();
        this.duplicationEmailMessage = configurationManager.getProperty(ExceptionMessageConstant.DUPLICATION_EMAIL_MESSAGE);
    }

    public static UserService getInstance() {
        if (service == null) {
            LOCK.lock();
            if (service == null) {
                service = new UserService();
            }
            LOCK.unlock();
        }
        return service;
    }

    /**
     * @return all users
     * @throws HorseRaceBetException
     */
    public List<User> getAll() throws HorseRaceBetException {
        return userDAO.getAll();
    }

    /**
     * @param email
     * @param password
     * @return whether there is a such user
     * @throws HorseRaceBetException
     */
    public User checkUser(String email, String password) throws HorseRaceBetException {
        validateEmail(email);
        validatePassword(password);
        password = CryptMD5.cryptWithMD5(password);
        return userDAO.getByLoginAndPassword(email, password);
    }

    /**
     * @param user
     * @throws HorseRaceBetException
     */
    public void save(User user) throws HorseRaceBetException {
        validateUserObject(user);

        if (userDAO.checkExistsEmail(user.getEmail())) {
            user.setRole(Role.USER);

            userDAO.save(user);
        } else {
            throw new DuplicationEmailException(duplicationEmailMessage);
        }
    }

    /**
     * @param id
     * @return user by id
     * @throws HorseRaceBetException
     */
    public User getUserById(Long id) throws HorseRaceBetException {
        validateId(id);

        return userDAO.getById(id);
    }

    /**
     * @param id
     * @param betMoney
     * @throws HorseRaceBetException
     */
    public void addUserBalanceMoney(Long id, BigDecimal betMoney) throws HorseRaceBetException {
        validateId(id);
        validateMoney(betMoney);

        userDAO.updateBalance(id, betMoney);
    }

    /**
     * @param user
     * @throws HorseRaceBetException
     */
    public void update(User user) throws HorseRaceBetException {
        validateUserObject(user);
        validateId(user.getId());

        userDAO.update(user);
    }

    private void validateUserObject(User user) throws IncorrectInputParamException {
        validateName(user.getFirstName());
        validateName(user.getLastName());
        validateId(user.getCountryId());
        validateEmail(user.getEmail());
        validateMoney(user.getBalance());
    }
}