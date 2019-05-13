package by.epam.javawebtraining.kukareko.horseracebet.dao.builder;

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
            userBet.setId(rs.getLong("id"));
            userBet.setBetId(rs.getLong("bet_id"));
            userBet.setUserId(rs.getLong("user_id"));
            userBet.setBetMoney(rs.getBigDecimal("bet_money"));
            userBet.setHaveSp(rs.getBoolean("have_sp"));
            userBet.setStatus(rs.getString("status"));
            userBet.setCoefficient(rs.getFloat("coefficient"));
        } catch (SQLException ex) {
            LOGGER.error(ex.getMessage());
        }

        return userBet;
    }
}
