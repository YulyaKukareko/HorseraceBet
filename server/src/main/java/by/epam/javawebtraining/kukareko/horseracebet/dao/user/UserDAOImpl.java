package by.epam.javawebtraining.kukareko.horseracebet.dao.user;

import static by.epam.javawebtraining.kukareko.horseracebet.util.constant.SQLConstant.*;

import by.epam.javawebtraining.kukareko.horseracebet.dao.builder.AbstractBuilder;
import by.epam.javawebtraining.kukareko.horseracebet.dao.builder.FactoryBuilder;
import by.epam.javawebtraining.kukareko.horseracebet.dao.builder.TypeBuilder;
import by.epam.javawebtraining.kukareko.horseracebet.dao.AbstractDAO;
import by.epam.javawebtraining.kukareko.horseracebet.model.entity.User;
import by.epam.javawebtraining.kukareko.horseracebet.model.exception.HorseRaceBetException;
import by.epam.javawebtraining.kukareko.horseracebet.model.exception.logical.DatabaseConnectionException;
import by.epam.javawebtraining.kukareko.horseracebet.model.exception.logical.IncorrectInputParamException;
import by.epam.javawebtraining.kukareko.horseracebet.util.CryptMD5;
import by.epam.javawebtraining.kukareko.horseracebet.util.constant.GeneralConstants;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Yulya Kukareko
 * @version 1.0 13 Apr 2019
 */
public class UserDAOImpl extends AbstractDAO implements UserDAO {

    private static final ReentrantLock LOCK = new ReentrantLock();

    private static UserDAOImpl dao;

    private String selectById;
    private String insert;
    private String select;
    private String update;
    private String delete;
    private String checkByLoginAndPassword;
    private String checkExistsEmail;
    private String addBalanceMoney;

    private AbstractBuilder builder;

    private UserDAOImpl() {
        this.builder = FactoryBuilder.getBuilder(TypeBuilder.USER);
        this.selectById = configurationManager.getProperty(SQL_SELECT_USER_BY_ID);
        this.insert = configurationManager.getProperty(SQL_INSERT_USER);
        this.select = configurationManager.getProperty(SQL_SELECT_USER);
        this.update = configurationManager.getProperty(SQL_UPDATE_USER);
        this.delete = configurationManager.getProperty(SQL_DELETE_USER);
        this.checkByLoginAndPassword = configurationManager.getProperty(SQL_CHECK_USER_BY_LOGIN_AND_PASSWORD);
        this.checkExistsEmail = configurationManager.getProperty(SQL_CHECK_EXISTS_EMAIL);
        this.addBalanceMoney = configurationManager.getProperty(SQL_ADDING_USER_BALANCE_MONEY);
    }

    public static UserDAOImpl getInstance() {
        if (dao == null) {
            LOCK.lock();
            if (dao == null) {
                dao = new UserDAOImpl();
            }
            LOCK.unlock();
        }
        return dao;
    }

    @Override
    public User getById(Long id) throws HorseRaceBetException {
        Map<Integer, Object> queryParams = new HashMap<>();
        queryParams.put(1, id);

        ResultSet rs = executeQuery(selectById, queryParams, true);
        return getUser(rs);
    }

    @Override
    public void save(User user) throws HorseRaceBetException {
        String cryptoPassword = CryptMD5.cryptWithMD5(user.getPassword());
        user.setPassword(cryptoPassword);
        Map<Integer, Object> queryParams = buildParamsMap(user);
        queryParams.put(6, user.getPassword());
        queryParams.put(7, user.getRole());

        executeQuery(insert, queryParams, false);
    }

    @Override
    public List<User> getAll() throws HorseRaceBetException {
        List<User> users = new ArrayList<>();

        ResultSet rs = executeQuery(select, null, true);
        try {
            while (rs.next()) {
                users.add((User) builder.getEntity(rs));
            }
        } catch (SQLException ex) {
            LOGGER.error(ex.getMessage());
        }
        return users;
    }

    @Override
    public void update(User user) throws HorseRaceBetException {
        Map<Integer, Object> queryParams = buildParamsMap(user);
        queryParams.put(6, user.getId());

        executeQuery(update, queryParams, false);
    }

    @Override
    public void delete(User user) throws IncorrectInputParamException, DatabaseConnectionException {
        Map<Integer, Object> queryParams = new HashMap<>();
        queryParams.put(1, user.getId());

        executeQuery(delete, queryParams, false);
    }

    @Override
    public User getByLoginAndPassword(String login, String password) throws HorseRaceBetException {
        Map<Integer, Object> queryParams = new HashMap<>();
        queryParams.put(1, login);
        queryParams.put(2, password);

        ResultSet rs = executeQuery(checkByLoginAndPassword, queryParams, true);

        return getUser(rs);
    }

    @Override
    public boolean checkExistsEmail(String email) throws HorseRaceBetException {
        Map<Integer, Object> queryParams = new HashMap<>();
        queryParams.put(1, email);

        ResultSet rs = executeQuery(checkExistsEmail, queryParams, true);
        try {
            if ((rs.next())) {
                return rs.getInt(GeneralConstants.EXIST_COLUMN) == 0;
            }
        } catch (SQLException ex) {
            LOGGER.error(ex.getMessage());
        }
        return true;
    }

    private User getUser(ResultSet rs) {
        User user = null;
        try {
            if (rs.next()) {
                user = (User) builder.getEntity(rs);
            }
        } catch (SQLException ex) {
            LOGGER.error(ex.getMessage());
        }
        return user;
    }

    @Override
    public void updateBalance(Long id, BigDecimal money) throws HorseRaceBetException {
        Map<Integer, Object> queryParams = new HashMap<>();
        queryParams.put(1, money);
        queryParams.put(2, id);

        executeQuery(addBalanceMoney, queryParams, false);
    }

    private Map<Integer, Object> buildParamsMap(User user) {
        Map<Integer, Object> queryParams = new HashMap<>();

        queryParams.put(1, user.getEmail());
        queryParams.put(2, user.getFirstName());
        queryParams.put(3, user.getLastName());
        queryParams.put(4, user.getCountryId());
        queryParams.put(5, user.getBalance());

        return queryParams;
    }
}