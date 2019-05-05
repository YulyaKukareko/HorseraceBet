package by.epam.javawebtraining.kukareko.horseracebet.dao.userbet;

import by.epam.javawebtraining.kukareko.horseracebet.dao.builder.AbstractBuilder;
import by.epam.javawebtraining.kukareko.horseracebet.dao.builder.FactoryBuilder;
import by.epam.javawebtraining.kukareko.horseracebet.dao.builder.TypeBuilder;
import by.epam.javawebtraining.kukareko.horseracebet.dao.AbstractDAO;
import by.epam.javawebtraining.kukareko.horseracebet.model.entity.UserBet;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Yulya Kukareko
 * @version 1.0 28 Апр. 2019
 */
public class UserBetDAOImpl extends AbstractDAO implements UserBetDAO {

    private static UserBetDAOImpl dao;
    private static final ReentrantLock LOCK = new ReentrantLock();
    private static final AbstractBuilder builder;

    static {
        builder = FactoryBuilder.getBuilder(TypeBuilder.USER_BET);
    }

    private UserBetDAOImpl() {
    }

    public static UserBetDAOImpl getInstance() {
        if (dao == null) {
            LOCK.lock();
            if (dao == null) {
                dao = new UserBetDAOImpl();
            }
            LOCK.unlock();
        }
        return dao;
    }

    @Override
    public UserBet getById(Long id) {
        Map<Integer, Object> queryParams = new HashMap<>();
        queryParams.put(1, id);
        UserBet userBet = null;

        try {
            ResultSet rs = executeQuery(configurationManager.getProperty("SQL.selectUserBetById"), queryParams, true);
            if ((rs != null) && (rs.next())) {
                userBet = (UserBet) builder.getEntity(rs);
            }
        } catch (SQLException ex) {
            LOGGER.error(ex.getMessage());
        }
        return userBet;
    }

    @Override
    public List<UserBet> getAll() {
        ResultSet rs;
        List<UserBet> userBets = new ArrayList<>();

        try {
            rs = executeQuery(configurationManager.getProperty("SQL.selectUserBet"), null, true);
            if (rs != null) {
                while (rs.next()) {
                    userBets.add((UserBet) builder.getEntity(rs));
                }
            }
        } catch (SQLException ex) {
            LOGGER.error(ex.getMessage());
        }
        return userBets;
    }

    @Override
    public boolean save(UserBet userBet) {
        Map<Integer, Object> queryParams = buildParamsMap(userBet);

        try {
            executeQuery(configurationManager.getProperty("SQL.insertUserBet"), queryParams, false);
        } catch (SQLException ex) {
            LOGGER.error(ex.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public List<UserBet> getByUserId(long userId) {
        ResultSet rs;
        List<UserBet> userBets = new ArrayList<>();
        Map<Integer, Object> queryParams = new HashMap<>();

        queryParams.put(1, userId);

        try {
            rs = executeQuery(configurationManager.getProperty("SQL.selectUserBetByUserId"), queryParams, true);
            if (rs != null) {
                while (rs.next()) {
                    userBets.add((UserBet) builder.getEntity(rs));
                }
            }
        } catch (SQLException ex) {
            LOGGER.error(ex.getMessage());
        }
        return userBets;
    }

    @Override
    public boolean update(UserBet userBet) {
        Map<Integer, Object> queryParams = buildParamsMap(userBet);
        queryParams.put(6, userBet.getId());

        try {
            executeQuery(configurationManager.getProperty("SQL.updateUserBet"), queryParams, false);
        } catch (SQLException ex) {
            LOGGER.error(ex.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public boolean delete(UserBet userBet) {
        Map<Integer, Object> queryParams = new HashMap<>();
        queryParams.put(1, userBet.getId());

        try {
            executeQuery(configurationManager.getProperty("SQL.deleteUserBet"), queryParams, false);
        } catch (SQLException ex) {
            LOGGER.error(ex.getMessage());
            return false;
        }
        return true;
    }

    private Map<Integer, Object> buildParamsMap(UserBet userBet) {
        Map<Integer, Object> queryParams = new HashMap<>();

        queryParams.put(1, userBet.getBetId());
        queryParams.put(2, userBet.getUserId());
        queryParams.put(3, userBet.isHaveSp());
        queryParams.put(4, userBet.getBetMoney());
        queryParams.put(5, userBet.getCoefficient());

        return queryParams;
    }
}
