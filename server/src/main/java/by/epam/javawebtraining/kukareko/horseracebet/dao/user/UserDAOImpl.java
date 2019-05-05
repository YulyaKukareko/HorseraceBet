package by.epam.javawebtraining.kukareko.horseracebet.dao.user;

import by.epam.javawebtraining.kukareko.horseracebet.dao.builder.AbstractBuilder;
import by.epam.javawebtraining.kukareko.horseracebet.dao.builder.FactoryBuilder;
import by.epam.javawebtraining.kukareko.horseracebet.dao.builder.TypeBuilder;
import by.epam.javawebtraining.kukareko.horseracebet.dao.AbstractDAO;
import by.epam.javawebtraining.kukareko.horseracebet.model.entity.User;
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

    private static UserDAOImpl dao;
    private static final ReentrantLock LOCK = new ReentrantLock();
    private static final AbstractBuilder builder;

    static {
        builder = FactoryBuilder.getBuilder(TypeBuilder.USER);
    }

    private UserDAOImpl() {
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
    public User getById(Long id) {
        Map<Integer, Object> queryParams = new HashMap<>();
        queryParams.put(1, id);
        User user = null;

        try {
            ResultSet rs = executeQuery(configurationManager.getProperty("SQL.selectUserById"), queryParams, true);
            if ((rs != null) && (rs.next())) {
                user = (User) builder.getEntity(rs);
            }
        } catch (SQLException ex) {
            LOGGER.error(ex.getMessage());
        }
        return user;
    }

    @Override
    public boolean save(User user) {
        String cryptoPassword = CryptMD5.cryptWithMD5(user.getPassword());
        user.setPassword(cryptoPassword);
        Map<Integer, Object> queryParams = buildParamsMap(user);

        try {
            executeQuery(configurationManager.getProperty("SQL.insertUser"), queryParams, false);
        } catch (SQLException ex) {
            LOGGER.error(ex.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public List<User> getAll() {
        ResultSet rs;
        List<User> users = new ArrayList<>();

        try {
            rs = executeQuery(configurationManager.getProperty("SQL.selectUser"), null, true);
            if (rs != null) {
                while (rs.next()) {
                    users.add((User) builder.getEntity(rs));
                }
            }
        } catch (SQLException ex) {
            LOGGER.error(ex.getMessage());
        }
        return users;
    }

    @Override
    public boolean update(User user) {
        Map<Integer, Object> queryParams = buildParamsMap(user);
        queryParams.put(7, user.getId());

        try {
            executeQuery(configurationManager.getProperty("SQL.updateUser"), queryParams, false);
        } catch (SQLException ex) {
            LOGGER.error(ex.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public boolean delete(User user) {
        Map<Integer, Object> queryParams = new HashMap<>();
        queryParams.put(1, user.getId());

        try {
            executeQuery(configurationManager.getProperty("SQL.deleteUser"), queryParams, false);
        } catch (SQLException ex) {
            LOGGER.error(ex.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public boolean makeBet(long id, BigDecimal betMoney) {
        Map<Integer, Object> queryParams = new HashMap<>();
        queryParams.put(1, betMoney);
        queryParams.put(2, id);

        try {
            executeQuery(configurationManager.getProperty("SQL.makeBet"), queryParams, false);
        } catch (SQLException ex) {
            LOGGER.error(ex.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public User getByLoginAndPassword(String login, String password) {
        Map<Integer, Object> queryParams = new HashMap<>();
        queryParams.put(1, login);
        queryParams.put(2, password);
        ResultSet rs;
        User user = null;

        try {
            rs = executeQuery(configurationManager.getProperty("SQL.checkUserByLoginAndPassword"),
                    queryParams, true);
            if ((rs.next())) {
                user = (User) builder.getEntity(rs);
            }
        } catch (SQLException ex) {
            LOGGER.error(ex.getMessage());
        }

        return user;
    }

    private Map<Integer, Object> buildParamsMap(User user) {
        Map<Integer, Object> queryParams = new HashMap<>();

        queryParams.put(1, user.getLogin());
        queryParams.put(2, user.getFirstName());
        queryParams.put(3, user.getLastName());
        queryParams.put(4, user.getCountry());
        queryParams.put(5, user.getBalance());
        queryParams.put(6, user.getPassword());

        return queryParams;
    }
}
