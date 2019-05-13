package by.epam.javawebtraining.kukareko.horseracebet.dao.horse;

import by.epam.javawebtraining.kukareko.horseracebet.dao.builder.AbstractBuilder;
import by.epam.javawebtraining.kukareko.horseracebet.dao.builder.FactoryBuilder;
import by.epam.javawebtraining.kukareko.horseracebet.dao.builder.TypeBuilder;
import by.epam.javawebtraining.kukareko.horseracebet.dao.AbstractDAO;
import by.epam.javawebtraining.kukareko.horseracebet.model.entity.Horse;
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
 * @version 1.0 11 Apr 2019
 */
public class HorseDAOImpl extends AbstractDAO implements HorseDAO {

    private static final ReentrantLock LOCK = new ReentrantLock();

    private static HorseDAOImpl dao;

    private AbstractBuilder builder;

    private HorseDAOImpl() {
        builder = FactoryBuilder.getBuilder(TypeBuilder.HORSE);
    }

    public static HorseDAOImpl getInstance() {
        if (dao == null) {
            LOCK.lock();
            if (dao == null) {
                dao = new HorseDAOImpl();
            }
            LOCK.unlock();
        }
        return dao;
    }

    @Override
    public Horse getById(Long id) throws HorseRaceBetException {
        Map<Integer, Object> queryParams = new HashMap<>();
        queryParams.put(1, id);
        Horse horse = null;

        ResultSet rs = executeQuery(configurationManager.getProperty("SQL.selectHorseById"), queryParams, true);
        try {
            if (rs.next()) {
                horse = (Horse) builder.getEntity(rs);
            }
        } catch (SQLException ex) {
            LOGGER.error(ex.getMessage());
        }
        return horse;
    }

    @Override
    public List<Horse> getAll() throws HorseRaceBetException {
        ResultSet rs = executeQuery(configurationManager.getProperty("SQL.selectHorse"), null, true);

        return getHorses(rs);
    }

    @Override
    public void save(Horse horse) throws HorseRaceBetException {
        Map<Integer, Object> queryParams = buildParamsMap(horse);

        executeQuery(configurationManager.getProperty("SQL.insertHorse"), queryParams, false);
    }

    @Override
    public void update(Horse horse) throws HorseRaceBetException {
        Map<Integer, Object> queryParams = buildParamsMap(horse);
        queryParams.put(5, horse.getId());

        executeQuery(configurationManager.getProperty("SQL.updateHorse"), queryParams, false);
    }

    @Override
    public void delete(Horse horse) throws HorseRaceBetException {
        Map<Integer, Object> queryParams = new HashMap<>();
        queryParams.put(1, horse.getId());

        executeQuery(configurationManager.getProperty("SQL.deleteHorse"), queryParams, false);
    }

    @Override
    public List<Horse> getJoinHorseStartingPrice() throws HorseRaceBetException {
        ResultSet rs = executeQuery(configurationManager.getProperty("SQL.selectHorsesJoinHorseStartingPrice"), null, true);
        return getHorses(rs);
    }

    @Override
    public List<Horse> getJoinFirstHorseBetAndHorseStartingPrice() throws HorseRaceBetException {
        ResultSet rs = executeQuery(configurationManager.getProperty("SQL.selectHorsesJoinStartingPriceAndFirstHorseBet"), null, true);

        return getHorses(rs);
    }

    @Override
    public Horse getJoinHorseStartingPriceByStartingPriceId(long startingPriceId) throws HorseRaceBetException {
        Horse horse = null;
        Map<Integer, Object> queryParams = new HashMap<>();
        queryParams.put(1, startingPriceId);

        ResultSet rs = executeQuery(configurationManager.getProperty("SQL.selectHorseJoinStartingPriceAndFirstHorseBySPId"), queryParams, true);
        try {
            while (rs.next()) {
                horse = (Horse) builder.getEntity(rs);
            }
        } catch (SQLException ex) {
            LOGGER.error(ex.getMessage());
        }
        return horse;
    }

    @Override
    public List<Horse> getJoinSecondHorseBetAndHorseStartingPrice() throws HorseRaceBetException {
        ResultSet rs = executeQuery(configurationManager.getProperty("SQL.selectHorsesJoinStartingPriceAndSecondHorseBet"), null, true);

        return getHorses(rs);
    }

    @Override
    public List<Horse> getJoinHorseStartingPriceByRaceId(long raceId) throws HorseRaceBetException {
        Map<Integer, Object> queryParams = new HashMap<>();
        queryParams.put(1, raceId);

        ResultSet rs = executeQuery(configurationManager.getProperty("SQL.selectHorsesJoinHorseStartingPriceByRaceId"), queryParams, true);
        return getHorses(rs);
    }

    @Override
    public List<Horse> getJoinHorseStartingPriceExcludingByRaceId(long raceId) throws HorseRaceBetException {
        Map<Integer, Object> queryParams = new HashMap<>();
        queryParams.put(1, raceId);

        ResultSet rs = executeQuery(configurationManager.getProperty("SQL.selectHorsesJoinHorseStartingPriceExcludingByRaceId"), queryParams, true);
        return getHorses(rs);
    }

    private List<Horse> getHorses(ResultSet rs) {
        List<Horse> horses = new ArrayList<>();

        try {
            while (rs.next()) {
                horses.add((Horse) builder.getEntity(rs));
            }
        } catch (SQLException ex) {
            LOGGER.error(ex.getMessage());
        }
        return horses;
    }

    private Map<Integer, Object> buildParamsMap(Horse horse) {
        Map<Integer, Object> queryParams = new HashMap<>();

        queryParams.put(1, horse.getTrainer());
        queryParams.put(2, horse.getJockey());
        queryParams.put(3, horse.getWeight());
        queryParams.put(4, horse.getName());

        return queryParams;
    }
}