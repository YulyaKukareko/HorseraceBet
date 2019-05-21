package by.epam.javawebtraining.kukareko.horseracebet.dao.builder;

import static by.epam.javawebtraining.kukareko.horseracebet.util.constant.EntityBuilderColumnConstant.*;

import by.epam.javawebtraining.kukareko.horseracebet.model.entity.Country;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Yulya Kukareko
 * @version 1.0 07 May 2019
 */
public class CountryBuilder extends AbstractBuilder<Country> {

    private static final ReentrantLock LOCK;
    private static CountryBuilder instance;

    static {
        LOCK = new ReentrantLock();
    }

    private CountryBuilder() {
    }

    public static CountryBuilder getInstance() {
        if (instance == null) {
            LOCK.lock();
            if (instance == null) {
                instance = new CountryBuilder();
            }
            LOCK.unlock();
        }
        return instance;
    }

    @Override
    public Country getEntity(ResultSet rs) {
        Country country = null;

        try {
            country = new Country();
            country.setId(rs.getLong(ID_COLUMN));
            country.setName(rs.getString(NAME_COLUMN));
        } catch (SQLException ex) {
            LOGGER.error(ex.getMessage());
        }

        return country;
    }
}
