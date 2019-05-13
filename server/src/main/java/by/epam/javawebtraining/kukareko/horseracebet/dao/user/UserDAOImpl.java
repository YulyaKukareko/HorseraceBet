package by.epam.javawebtraining.kukareko.horseracebet.dao.user;

import by.epam.javawebtraining.kukareko.horseracebet.dao.builder.AbstractBuilder;
import by.epam.javawebtraining.kukareko.horseracebet.dao.builder.FactoryBuilder;
import by.epam.javawebtraining.kukareko.horseracebet.dao.builder.TypeBuilder;
import by.epam.javawebtraining.kukareko.horseracebet.dao.AbstractDAO;
import by.epam.javawebtraining.kukareko.horseracebet.model.entity.User;
import by.epam.javawebtraining.kukareko.horseracebet.model.exception.HorseRaceBetException;
import by.epam.javawebtraining.kukareko.horseracebet.model.exception.logical.DatabaseConnectionException;
import by.epam.javawebtraining.kukareko.horseracebet.model.exception.logical.IncorrectInputParamException;
import by.epam.javawebtraining.kukareko.horseracebet.util.CryptMD5;

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

    private AbstractBuilder builder;

    private UserDAOImpl() {
        builder = FactoryBuilder.getBuilder(TypeBuilder.USER);
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

        ResultSet rs = executeQuery(configurationManager.getProperty("SQL.selectUserById"), queryParams, true);
        return getUser(rs);
    }

    @Override
    public void save(User user) throws HorseRaceBetException {
        String cryptoPassword = CryptMD5.cryptWithMD5(user.getPassword());
        user.setPassword(cryptoPassword);
        Map<Integer, Object> queryParams = buildParamsMap(user);

        executeQuery(configurationManager.getProperty("SQL.insertUser"), queryParams, false);
    }

    @Override
    public List<User> getAll() throws HorseRaceBetException {
        List<User> users = new ArrayList<>();

        ResultSet rs = executeQuery(configurationManager.getProperty("SQL.selectUser"), null, true);
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
        queryParams.put(7, user.getId());

        executeQuery(configurationManager.getProperty("SQL.updateUser"), queryParams, false);
    }

    @Override
    public void delete(User user) throws IncorrectInputParamException, DatabaseConnectionException {
        Map<Integer, Object> queryParams = new HashMap<>();
        queryParams.put(1, user.getId());

        executeQuery(configurationManager.getProperty("SQL.deleteUser"), queryParams, false);
    }

    @Override
    public void makeBet(long id, BigDecimal betMoney) throws HorseRaceBetException {
        Map<Integer, Object> queryParams = new HashMap<>();
        queryParams.put(1, betMoney);
        queryParams.put(2, id);

        executeQuery(configurationManager.getProperty("SQL.makeBet"), queryParams, false);
    }

    @Override
    public User getByLoginAndPassword(String login, String password) throws HorseRaceBetException {
        Map<Integer, Object> queryParams = new HashMap<>();
        queryParams.put(1, login);
        queryParams.put(2, password);

        ResultSet rs = executeQuery(configurationManager.getProperty("SQL.checkUserByLoginAndPassword"),
                queryParams, true);

        return getUser(rs);
    }

    @Override
    public boolean checkExistsEmail(String email) throws HorseRaceBetException {
        Map<Integer, Object> queryParams = new HashMap<>();
        queryParams.put(1, email);

        ResultSet rs = executeQuery(configurationManager.getProperty("SQL.checkExistsEmail"), queryParams, true);
        try {
            if ((rs.next())) {
                return rs.getInt("exist") == 0;
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

        executeQuery(configurationManager.getProperty("SQL.addingUserBalanceMoney"), queryParams, false);
    }

    private Map<Integer, Object> buildParamsMap(User user) {
        Map<Integer, Object> queryParams = new HashMap<>();

        queryParams.put(1, user.getEmail());
        queryParams.put(2, user.getFirstName());
        queryParams.put(3, user.getLastName());
        queryParams.put(4, user.getCountryId());
        queryParams.put(5, user.getBalance());
        queryParams.put(6, user.getPassword());
        queryParams.put(7, user.getRole());

        return queryParams;
    }
}