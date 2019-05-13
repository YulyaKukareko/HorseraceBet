package by.epam.javawebtraining.kukareko.horseracebet.dao.country;

import by.epam.javawebtraining.kukareko.horseracebet.dao.AbstractDAO;
import by.epam.javawebtraining.kukareko.horseracebet.dao.builder.AbstractBuilder;
import by.epam.javawebtraining.kukareko.horseracebet.dao.builder.FactoryBuilder;
import by.epam.javawebtraining.kukareko.horseracebet.dao.builder.TypeBuilder;
import by.epam.javawebtraining.kukareko.horseracebet.model.entity.Country;
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
 * @version 1.0 07 May 2019
 */
public class CountryDAOImpl extends AbstractDAO implements CountryDAO {

    private static final ReentrantLock LOCK = new ReentrantLock();

    private static CountryDAOImpl dao;

    private AbstractBuilder builder;

    private CountryDAOImpl() {
        builder = FactoryBuilder.getBuilder(TypeBuilder.COUNTRY);
    }

    public static CountryDAOImpl getInstance() {
        if (dao == null) {
            LOCK.lock();
            if (dao == null) {
                dao = new CountryDAOImpl();
            }
            LOCK.unlock();
        }
        return dao;
    }


    @Override
    public Country getById(Long id) throws HorseRaceBetException {
        Map<Integer, Object> queryParams = new HashMap<>();
        queryParams.put(1, id);
        Country country = null;

        ResultSet rs = executeQuery(configurationManager.getProperty("SQL.selectCountryById"), queryParams, true);
        try {
            if (rs.next()) {
                country = (Country) builder.getEntity(rs);
            }
        } catch (SQLException ex) {
            LOGGER.error(ex.getMessage());
        }
        return country;
    }

    @Override
    public List<Country> getAll() throws HorseRaceBetException {
        ResultSet rs;
        List<Country> horses = new ArrayList<>();

        rs = executeQuery(configurationManager.getProperty("SQL.selectCountry"), null, true);
        try {
            while (rs.next()) {
                horses.add((Country) builder.getEntity(rs));
            }
        } catch (SQLException ex) {
            LOGGER.error(ex.getMessage());
        }
        return horses;
    }

    @Override
    public void save(Country country) throws HorseRaceBetException {
        Map<Integer, Object> queryParams = new HashMap<>();
        queryParams.put(1, country.getName());

        executeQuery(configurationManager.getProperty("SQL.insertCountry"), queryParams, false);
    }

    @Override
    public void update(Country entity) throws HorseRaceBetException {
        Map<Integer, Object> queryParams = new HashMap<>();
        queryParams.put(1, entity.getName());
        queryParams.put(2, entity.getId());

        executeQuery(configurationManager.getProperty("SQL.updateCountry"), queryParams, false);
    }

    @Override
    public void delete(Country country) throws HorseRaceBetException {
        Map<Integer, Object> queryParams = new HashMap<>();
        queryParams.put(1, country.getId());

        executeQuery(configurationManager.getProperty("SQL.deleteCountry"), queryParams, false);
    }
}
