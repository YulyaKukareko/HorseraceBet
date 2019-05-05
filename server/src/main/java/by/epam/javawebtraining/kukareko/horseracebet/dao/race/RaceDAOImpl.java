package by.epam.javawebtraining.kukareko.horseracebet.dao.race;

import by.epam.javawebtraining.kukareko.horseracebet.dao.builder.AbstractBuilder;
import by.epam.javawebtraining.kukareko.horseracebet.dao.builder.FactoryBuilder;
import by.epam.javawebtraining.kukareko.horseracebet.dao.builder.TypeBuilder;
import by.epam.javawebtraining.kukareko.horseracebet.dao.AbstractDAO;
import by.epam.javawebtraining.kukareko.horseracebet.model.entity.Race;

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

    private static RaceDAOImpl dao;
    private static final ReentrantLock LOCK = new ReentrantLock();
    private static final AbstractBuilder builder;

    static {
        builder = FactoryBuilder.getBuilder(TypeBuilder.RACE);
    }

    private RaceDAOImpl() {
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
    public Race getById(Long id) {
        Map<Integer, Object> queryParams = new HashMap<>();
        queryParams.put(1, id);
        Race race = null;

        try {
            ResultSet rs = executeQuery(configurationManager.getProperty("SQL.selectRaceById"), queryParams, true);
            if ((rs != null) && (rs.next())) {
                race = (Race) builder.getEntity(rs);
            }
        } catch (SQLException ex) {
            LOGGER.error(ex.getMessage());
        }
        return race;
    }

    @Override
    public List<Race> getAll() {
        ResultSet rs;
        List<Race> races = new ArrayList<>();

        try {
            rs = executeQuery(configurationManager.getProperty("SQL.selectRace"), null, true);
            if (rs != null) {
                while (rs.next()) {
                    races.add((Race) builder.getEntity(rs));
                }
            }
        } catch (SQLException ex) {
            LOGGER.error(ex.getMessage());
        }
        return races;
    }

    @Override
    public boolean save(Race race) {
        Map<Integer, Object> queryParams = buildParamsMap(race);

        try {
            executeQuery(configurationManager.getProperty("SQL.insertRace"), queryParams, false);
        } catch (SQLException ex) {
            LOGGER.error(ex.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public boolean update(Race race) {
        Map<Integer, Object> queryParams = buildParamsMap(race);
        queryParams.put(6, race.getId());

        try {
            executeQuery(configurationManager.getProperty("SQL.updateRace"), queryParams, false);
        } catch (SQLException ex) {
            LOGGER.error(ex.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public boolean delete(Race race) {
        Map<Integer, Object> queryParams = new HashMap<>();
        queryParams.put(1, race.getId());

        try {
            executeQuery(configurationManager.getProperty("SQL.deleteRace"), queryParams, false);
        } catch (SQLException ex) {
            LOGGER.error(ex.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public List<Race> getJoinHorseStarting() {
        ResultSet rs;
        List<Race> races = new ArrayList<>();

        try {
            rs = executeQuery(configurationManager.getProperty("SQL.selectRaceJoinHorseStartingPrice"), null, true);
            if (rs != null) {
                while (rs.next()) {
                    races.add((Race) builder.getEntity(rs));
                }
            }
        } catch (SQLException ex) {
            LOGGER.error(ex.getMessage());
        }
        return races;
    }

    @Override
    public List<Race> getJoinBet() {
        ResultSet rs;
        List<Race> races = new ArrayList<>();

        try {
            rs = executeQuery(configurationManager.getProperty("SQL.selectRaceJoinBet"), null, true);
            if (rs != null) {
                while (rs.next()) {
                    races.add((Race) builder.getEntity(rs));
                }
            }
        } catch (SQLException ex) {
            LOGGER.error(ex.getMessage());
        }
        return races;
    }

    @Override
    public List<Race> getJoinHorseStartingPriceById(long id) {
        ResultSet rs;
        List<Race> races = new ArrayList<>();
        Map<Integer, Object> queryParams = new HashMap<>();
        queryParams.put(1, id);

        try {
            rs = executeQuery(configurationManager.getProperty("SQL.selectRaceJoinHorseStartingPriceById"), queryParams, true);
            if (rs != null) {
                while (rs.next()) {
                    races.add((Race) builder.getEntity(rs));
                }
            }
        } catch (SQLException ex) {
            LOGGER.error(ex.getMessage());
        }
        return races;
    }

    @Override
    public List<Race> getCompletedRacesNotJoinResult() {
        ResultSet rs;
        List<Race> races = new ArrayList<>();

        try {
            rs = executeQuery(configurationManager.getProperty("SQL.selectCompletedRace"), null, true);
            if (rs != null) {
                while (rs.next()) {
                    races.add((Race) builder.getEntity(rs));
                }
            }
        } catch (SQLException ex) {
            LOGGER.error(ex.getMessage());
        }
        return races;
    }

    @Override
    public List<Race> getNotJoinResult() {
        ResultSet rs;
        List<Race> races = new ArrayList<>();

        try {
            rs = executeQuery(configurationManager.getProperty("SQL.selectRaceNotJoinResult"), null, true);
            if (rs != null) {
                while (rs.next()) {
                    races.add((Race) builder.getEntity(rs));
                }
            }
        } catch (SQLException ex) {
            LOGGER.error(ex.getMessage());
        }
        return races;
    }

    @Override
    public List<Race> getCompletedRacesJoinResult() {
        ResultSet rs;
        List<Race> races = new ArrayList<>();

        try {
            rs = executeQuery(configurationManager.getProperty("SQL.selectCompletedRaceJoinResult"), null, true);
            if (rs != null) {
                while (rs.next()) {
                    races.add((Race) builder.getEntity(rs));
                }
            }
        } catch (SQLException ex) {
            LOGGER.error(ex.getMessage());
        }
        return races;
    }

    private Map<Integer, Object> buildParamsMap(Race race) {
        Map<Integer, Object> queryParams = new HashMap<>();

        queryParams.put(1, race.getLocation());
        queryParams.put(2, race.getDistance());
        queryParams.put(3, race.getPurse());
        queryParams.put(4, race.getType());
        queryParams.put(5, race.getTime());

        return queryParams;
    }
}
