package by.epam.javawebtraining.kukareko.horseracebet.service;

import by.epam.javawebtraining.kukareko.horseracebet.dao.userbet.UserBetDAO;
import by.epam.javawebtraining.kukareko.horseracebet.dao.userbet.UserBetDAOImpl;
import by.epam.javawebtraining.kukareko.horseracebet.model.entity.UserBet;

import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Yulya Kukareko
 * @version 1.0 28 Apr 2019
 */
public class UserBetService {

    private UserBetDAO userBetDAO;

    private static UserBetService service;
    private static final ReentrantLock LOCK = new ReentrantLock();

    private UserBetService() {
        userBetDAO = UserBetDAOImpl.getInstance();
    }

    public static UserBetService getInstance() {
        if (service == null) {
            LOCK.lock();
            if (service == null) {
                service = new UserBetService();
            }
            LOCK.unlock();
        }
        return service;
    }

    public boolean save(UserBet userBet) {
        return userBetDAO.save(userBet);
    }

    public List<UserBet> getByUserId(Long userId) {
        return userBetDAO.getByUserId(userId);
    }
}
