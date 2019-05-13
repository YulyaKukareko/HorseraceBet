package by.epam.javawebtraining.kukareko.horseracebet.dao.userbet;

import by.epam.javawebtraining.kukareko.horseracebet.dao.builder.AbstractBuilder;
import by.epam.javawebtraining.kukareko.horseracebet.dao.builder.FactoryBuilder;
import by.epam.javawebtraining.kukareko.horseracebet.dao.builder.TypeBuilder;
import by.epam.javawebtraining.kukareko.horseracebet.dao.AbstractDAO;
import by.epam.javawebtraining.kukareko.horseracebet.model.entity.UserBet;
import by.epam.javawebtraining.kukareko.horseracebet.model.exception.HorseRaceBetException;

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

    private static final ReentrantLock LOCK = new ReentrantLock();

    private static UserBetDAOImpl dao;

    private AbstractBuilder builder;

    private UserBetDAOImpl() {
        builder = FactoryBuilder.getBuilder(TypeBuilder.USER_BET);
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
    public UserBet getById(Long id) throws HorseRaceBetException {
        Map<Integer, Object> queryParams = new HashMap<>();
        queryParams.put(1, id);
        UserBet userBet = null;

        ResultSet rs = executeQuery(configurationManager.getProperty("SQL.selectUserBetById"), queryParams, true);
        try {
            if (rs.next()) {
                userBet = (UserBet) builder.getEntity(rs);
            }
        } catch (SQLException ex) {
            LOGGER.error(ex.getMessage());
        }
        return userBet;
    }

    @Override
    public List<UserBet> getAll() throws HorseRaceBetException {
        ResultSet rs = executeQuery(configurationManager.getProperty("SQL.selectUserBet"), null, true);
        return getUserBets(rs);
    }

    @Override
    public void save(UserBet userBet) throws HorseRaceBetException {
        Map<Integer, Object> queryParams = buildParamsMap(userBet);

        executeProcedure(configurationManager.getProperty("SQL.insertUserBet"), queryParams, false);
    }

    @Override
    public List<UserBet> getByUserId(long userId) throws HorseRaceBetException {
        Map<Integer, Object> queryParams = new HashMap<>();

        queryParams.put(1, userId);

        ResultSet rs = executeQuery(configurationManager.getProperty("SQL.selectUserBetByUserId"), queryParams, true);
        return getUserBets(rs);
    }

    @Override
    public void update(UserBet userBet) throws HorseRaceBetException {
        Map<Integer, Object> queryParams = buildParamsMap(userBet);
        queryParams.put(6, userBet.getId());

        executeQuery(configurationManager.getProperty("SQL.updateUserBet"), queryParams, false);
    }

    @Override
    public void delete(UserBet userBet) throws HorseRaceBetException {
        Map<Integer, Object> queryParams = new HashMap<>();
        queryParams.put(1, userBet.getId());

        executeQuery(configurationManager.getProperty("SQL.deleteUserBet"), queryParams, false);
    }

    private List<UserBet> getUserBets(ResultSet rs) {
        List<UserBet> userBets = new ArrayList<>();

        try {
            while (rs.next()) {
                userBets.add((UserBet) builder.getEntity(rs));
            }
        } catch (SQLException ex) {
            LOGGER.error(ex.getMessage());
        }
        return userBets;
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
