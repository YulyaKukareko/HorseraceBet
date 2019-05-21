package by.epam.javawebtraining.kukareko.horseracebet.dao.userbet;

import static by.epam.javawebtraining.kukareko.horseracebet.util.constant.SQLConstant.*;

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
 * @version 1.0 28 Apr. 2019
 */
public class UserBetDAOImpl extends AbstractDAO implements UserBetDAO {

    private static final ReentrantLock LOCK = new ReentrantLock();

    private static UserBetDAOImpl dao;

    private String selectById;
    private String select;
    private String insert;
    private String selectByUserId;
    private String update;
    private String delete;

    private AbstractBuilder builder;

    private UserBetDAOImpl() {
        this.builder = FactoryBuilder.getBuilder(TypeBuilder.USER_BET);
        this.selectById = configurationManager.getProperty(SQL_SELECT_USER_BET_BY_ID);
        this.select = configurationManager.getProperty(SQL_SELECT_USER_BET);
        this.insert = configurationManager.getProperty(SQL_INSERT_USER_BET);
        this.selectByUserId = configurationManager.getProperty(SQL_SELECT_USER_BET_BY_USER_ID);
        this.update = configurationManager.getProperty(SQL_UPDATE_USER_BET);
        this.delete = configurationManager.getProperty(SQL_DELETE_USER_BET);
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

        ResultSet rs = executeQuery(selectById, queryParams, true);
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
        ResultSet rs = executeQuery(select, null, true);
        return getUserBets(rs);
    }

    @Override
    public void save(UserBet userBet) throws HorseRaceBetException {
        Map<Integer, Object> queryParams = buildParamsMap(userBet);

        executeProcedure(insert, queryParams, false);
    }

    @Override
    public List<UserBet> getByUserId(long userId) throws HorseRaceBetException {
        Map<Integer, Object> queryParams = new HashMap<>();

        queryParams.put(1, userId);

        ResultSet rs = executeQuery(selectByUserId, queryParams, true);
        return getUserBets(rs);
    }

    @Override
    public void update(UserBet userBet) throws HorseRaceBetException {
        Map<Integer, Object> queryParams = buildParamsMap(userBet);
        queryParams.put(6, userBet.getId());

        executeQuery(update, queryParams, false);
    }

    @Override
    public void delete(UserBet userBet) throws HorseRaceBetException {
        Map<Integer, Object> queryParams = new HashMap<>();
        queryParams.put(1, userBet.getId());

        executeQuery(delete, queryParams, false);
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
