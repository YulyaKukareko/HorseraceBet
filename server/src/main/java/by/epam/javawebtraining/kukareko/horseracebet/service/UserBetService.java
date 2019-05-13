package by.epam.javawebtraining.kukareko.horseracebet.service;

import static by.epam.javawebtraining.kukareko.horseracebet.util.validator.FieldValidator.*;

import by.epam.javawebtraining.kukareko.horseracebet.dao.userbet.UserBetDAO;
import by.epam.javawebtraining.kukareko.horseracebet.dao.userbet.UserBetDAOImpl;
import by.epam.javawebtraining.kukareko.horseracebet.model.entity.UserBet;
import by.epam.javawebtraining.kukareko.horseracebet.model.exception.HorseRaceBetException;
import by.epam.javawebtraining.kukareko.horseracebet.model.exception.logical.IncorrectInputParamException;

import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Yulya Kukareko
 * @version 1.0 28 Apr 2019
 */
public class UserBetService {

    private static final ReentrantLock LOCK = new ReentrantLock();

    private static UserBetService service;

    private UserBetDAO userBetDAO;

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

    public void save(UserBet userBet) throws HorseRaceBetException {
        validateBetObject(userBet);

        userBetDAO.save(userBet);
    }

    public List<UserBet> getByUserId(Long userId) throws HorseRaceBetException {
        validateId(userId);

        return userBetDAO.getByUserId(userId);
    }

    private void validateBetObject(UserBet userBet) throws IncorrectInputParamException {
        validateMoney(userBet.getBetMoney());
        validateCoefficient(userBet.getCoefficient());
        validateId(userBet.getUserId());
        validateId(userBet.getBetId());
    }
}