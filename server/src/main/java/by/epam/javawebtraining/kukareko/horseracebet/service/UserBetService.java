package by.epam.javawebtraining.kukareko.horseracebet.service;

import static by.epam.javawebtraining.kukareko.horseracebet.util.validator.FieldValidator.*;

import by.epam.javawebtraining.kukareko.horseracebet.dao.user.UserDAO;
import by.epam.javawebtraining.kukareko.horseracebet.dao.user.UserDAOImpl;
import by.epam.javawebtraining.kukareko.horseracebet.dao.userbet.UserBetDAO;
import by.epam.javawebtraining.kukareko.horseracebet.dao.userbet.UserBetDAOImpl;
import by.epam.javawebtraining.kukareko.horseracebet.model.entity.User;
import by.epam.javawebtraining.kukareko.horseracebet.model.entity.UserBet;
import by.epam.javawebtraining.kukareko.horseracebet.model.exception.HorseRaceBetException;
import by.epam.javawebtraining.kukareko.horseracebet.model.exception.logical.IncorrectInputParamException;
import by.epam.javawebtraining.kukareko.horseracebet.util.ConfigurationManager;
import by.epam.javawebtraining.kukareko.horseracebet.util.constant.ExceptionMessageConstant;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Yulya Kukareko
 * @version 1.0 28 Apr 2019
 */
public class UserBetService {

    private static final ReentrantLock LOCK = new ReentrantLock();

    private static UserBetService service;

    private String insufficientFundsMes;

    private UserBetDAO userBetDAO;
    private UserDAO userDAO;
    private ConfigurationManager configurationManager;

    private UserBetService() {
        this.userDAO = UserDAOImpl.getInstance();
        this.userBetDAO = UserBetDAOImpl.getInstance();
        this.configurationManager = ConfigurationManager.getInstance();
        this.insufficientFundsMes = configurationManager.getProperty(ExceptionMessageConstant.INSUFFICIENT_FUNDS_MESSAGE);
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

    /**
     * @param userBet
     * @throws HorseRaceBetException
     */
    public void save(UserBet userBet) throws HorseRaceBetException {
        User user  = userDAO.getById(userBet.getUserId());
        BigDecimal accountBalance = user.getBalance().subtract(userBet.getBetMoney());

        if(accountBalance.signum() >= 0) {
            validateBetObject(userBet);

            userBetDAO.save(userBet);
        } else {
            throw new IncorrectInputParamException(insufficientFundsMes);
        }
    }

    /**
     * @param userId
     * @return user bets by user id
     * @throws HorseRaceBetException
     */
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