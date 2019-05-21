package by.epam.javawebtraining.kukareko.horseracebet.dao.builder;

import static by.epam.javawebtraining.kukareko.horseracebet.util.constant.EntityBuilderColumnConstant.*;

import by.epam.javawebtraining.kukareko.horseracebet.model.entity.Role;
import by.epam.javawebtraining.kukareko.horseracebet.model.entity.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Yulya Kukareko
 * @version 1.0 14 Apr 2019
 */
public class UserBuilder extends AbstractBuilder<User> {

    private static final ReentrantLock LOCK;

    private static UserBuilder instance;

    static {
        LOCK = new ReentrantLock();
    }

    private UserBuilder() {
    }

    public static UserBuilder getInstance() {
        if (instance == null) {
            LOCK.lock();
            if (instance == null) {
                instance = new UserBuilder();
            }
            LOCK.unlock();
        }
        return instance;
    }

    @Override
    public User getEntity(ResultSet rs) {
        User user = null;

        try {
            user = new User();
            user.setId(rs.getLong(ID_COLUMN));
            user.setEmail(rs.getString(EMAIL_COLUMN));
            user.setFirstName(rs.getString(FIRST_NAME_COLUMN));
            user.setLastName(rs.getString(LAST_NAME_COLUMN));
            user.setBalance(rs.getBigDecimal(BALANCE_COLUMN));
            user.setCountryId(rs.getLong(COUNTRY_ID_COLUMN));
            user.setRole(Role.valueOf(rs.getString(ROLE_COLUMN).toUpperCase()));
        } catch (SQLException ex) {
            LOGGER.error(ex.getMessage());
        }

        return user;
    }
}
