package by.epam.javawebtraining.kukareko.horseracebet.dao.race;

import by.epam.javawebtraining.kukareko.horseracebet.dao.builder.AbstractBuilder;
import by.epam.javawebtraining.kukareko.horseracebet.dao.builder.FactoryBuilder;
import by.epam.javawebtraining.kukareko.horseracebet.dao.builder.TypeBuilder;
import by.epam.javawebtraining.kukareko.horseracebet.dao.AbstractDAO;
import by.epam.javawebtraining.kukareko.horseracebet.model.entity.Race;
import by.epam.javawebtraining.kukareko.horseracebet.model.exception.HorseRaceBetException;
import by.epam.javawebtraining.kukareko.horseracebet.model.exception.logical.DatabaseConnectionException;
import by.epam.javawebtraining.kukareko.horseracebet.model.exception.logical.IncorrectInputParamException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Yulya Kukareko
 * @version 1.0 18 Apr 2019
 */
public class RaceDAOImpl extends AbstractDAO implements RaceDAO {

    private static final ReentrantLock LOCK = new ReentrantLock();

    private static RaceDAOImpl dao;

    private AbstractBuilder builder;

    private RaceDAOImpl() {
        builder = FactoryBuilder.getBuilder(TypeBuilder.RACE);
    }

    public static RaceDAOImpl getInstance() {
        if (dao == null) {
            LOCK.lock();
            if (dao == null) {
                dao = new RaceDAOImpl();
            }
            LOCK.unlock();
        }
        return dao;
    }

    @Override
    public Race getById(Long id) throws IncorrectInputParamException, DatabaseConnectionException {
        Map<Integer, Object> queryParams = new HashMap<>();
        queryParams.put(1, id);
        Race race = null;

        ResultSet rs = executeQuery(configurationManager.getProperty("SQL.selectRaceById"), queryParams, true);

        try {
            if (rs.next()) {
                race = (Race) builder.getEntity(rs);
            }
        } catch (SQLException ex) {
            LOGGER.error(ex.getMessage());
        }
        return race;
    }

    @Override
    public List<Race> getAll() throws HorseRaceBetException {
        ResultSet rs = executeQuery(configurationManager.getProperty("SQL.selectRace"), null, true);

        return getRaces(rs);
    }

    @Override
    public void save(Race race) throws HorseRaceBetException {
        Map<Integer, Object> queryParams = buildParamsMap(race);

        executeQuery(configurationManager.getProperty("SQL.insertRace"), queryParams, false);
    }

    @Override
    public void update(Race race) throws HorseRaceBetException {
        Map<Integer, Object> queryParams = buildParamsMap(race);
        queryParams.put(7, race.getId());

        executeQuery(configurationManager.getProperty("SQL.updateRace"), queryParams, false);
    }

    @Override
    public void delete(Race race) throws HorseRaceBetException {
        Map<Integer, Object> queryParams = new HashMap<>();
        queryParams.put(1, race.getId());

        executeQuery(configurationManager.getProperty("SQL.deleteRace"), queryParams, false);
    }

    @Override
    public List<Race> getJoinHorseStarting() throws HorseRaceBetException {
        ResultSet rs = executeQuery(configurationManager.getProperty("SQL.selectRaceJoinHorseStartingPrice"), null, true);

        return getRaces(rs);
    }

    @Override
    public List<Race> getJoinBet() throws HorseRaceBetException {
        ResultSet rs = executeQuery(configurationManager.getProperty("SQL.selectRaceJoinBet"), null, true);

        return getRaces(rs);
    }

    @Override
    public List<Race> getJoinHorseStartingPriceById(long id) throws HorseRaceBetException {
        Map<Integer, Object> queryParams = new HashMap<>();
        queryParams.put(1, id);

        ResultSet rs = executeQuery(configurationManager.getProperty("SQL.selectRaceJoinHorseStartingPriceById"), queryParams, true);
        return getRaces(rs);
    }

    @Override
    public List<Race> getCompletedRacesNotJoinResult() throws HorseRaceBetException {
        ResultSet rs = executeQuery(configurationManager.getProperty("SQL.selectCompletedRace"), null, true);

        return getRaces(rs);
    }

    @Override
    public List<Race> getNotJoinResult() throws HorseRaceBetException {
        ResultSet rs = executeQuery(configurationManager.getProperty("SQL.selectRaceNotJoinResult"), null, true);

        return getRaces(rs);
    }

    @Override
    public List<Race> getCompletedRacesJoinResult() throws HorseRaceBetException {
        ResultSet rs = executeQuery(configurationManager.getProperty("SQL.selectCompletedRaceJoinResult"), null, true);

        return getRaces(rs);
    }

    private List<Race> getRaces(ResultSet rs) {
        List<Race> races = new ArrayList<>();

        try {
            while (rs.next()) {
                races.add((Race) builder.getEntity(rs));
            }
        } catch (SQLException ex) {
            LOGGER.error(ex.getMessage());
        }
        return races;
    }

    private Map<Integer, Object> buildParamsMap(Race race) {
        Map<Integer, Object> queryParams = new HashMap<>();

        queryParams.put(1, race.getName());
        queryParams.put(2, race.getCountryId());
        queryParams.put(3, race.getDistance());
        queryParams.put(4, race.getPurse());
        queryParams.put(5, race.getType());
        queryParams.put(6, race.getTime());

        return queryParams;
    }
}
