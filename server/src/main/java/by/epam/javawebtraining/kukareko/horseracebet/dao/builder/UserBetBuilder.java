package by.epam.javawebtraining.kukareko.horseracebet.dao.builder;

import static by.epam.javawebtraining.kukareko.horseracebet.util.constant.EntityBuilderColumnConstant.*;

import by.epam.javawebtraining.kukareko.horseracebet.model.entity.UserBet;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Yulya Kukareko
 * @version 1.0 15 Apr 2019
 */
public class UserBetBuilder extends AbstractBuilder<UserBet> {

    private static final ReentrantLock LOCK;

    private static UserBetBuilder instance;

    static {
        LOCK = new ReentrantLock();
    }

    private UserBetBuilder() {
    }

    public static UserBetBuilder getInstance() {
        if (instance == null) {
            LOCK.lock();
            if (instance == null) {
                instance = new UserBetBuilder();
            }
            LOCK.unlock();
        }
        return instance;
    }

    @Override
    public UserBet getEntity(ResultSet rs) {
        UserBet userBet = null;

        try {
            userBet = new UserBet();
            userBet.setId(rs.getLong(ID_COLUMN));
            userBet.setBetId(rs.getLong(BET_ID_COLUMN));
            userBet.setUserId(rs.getLong(USER_ID_COLUMN));
            userBet.setBetMoney(rs.getBigDecimal(BET_MONEY_COLUMN));
            userBet.setHaveSp(rs.getBoolean(HAVE_SP));
            userBet.setStatus(rs.getString(STATUS_COLUMN));
            userBet.setCoefficient(rs.getFloat(COEFFICIENT_COLUMN));
        } catch (SQLException ex) {
            LOGGER.error(ex.getMessage());
        }

        return userBet;
    }
}
