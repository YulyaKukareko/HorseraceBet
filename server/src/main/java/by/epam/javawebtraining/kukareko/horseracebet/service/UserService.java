package by.epam.javawebtraining.kukareko.horseracebet.service;

import by.epam.javawebtraining.kukareko.horseracebet.dao.user.UserDAO;
import by.epam.javawebtraining.kukareko.horseracebet.dao.user.UserDAOImpl;
import by.epam.javawebtraining.kukareko.horseracebet.model.entity.User;
import by.epam.javawebtraining.kukareko.horseracebet.util.CryptMD5;

import java.math.BigDecimal;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Yulya Kukareko
 * @version 1.0 14 Apr 2019
 */
public class UserService {

    private UserDAO userDAO;

    private static UserService service;
    private static final ReentrantLock LOCK = new ReentrantLock();

    private UserService() {
        userDAO = UserDAOImpl.getInstance();
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

    public User checkUser(String login, String password) {
        password = CryptMD5.cryptWithMD5(password);
        return userDAO.getByLoginAndPassword(login, password);
    }

    public boolean save(User user) {
        return userDAO.save(user);
    }

    public User getUserById(Long id) {
        return userDAO.getById(id);
    }

    public boolean makeBet(Long id, BigDecimal betMoney) {
        return userDAO.makeBet(id, betMoney);
    }

    public boolean update(User user) {
        return userDAO.update(user);
    }
}
