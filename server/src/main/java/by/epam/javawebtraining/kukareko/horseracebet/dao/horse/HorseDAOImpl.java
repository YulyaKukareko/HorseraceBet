package by.epam.javawebtraining.kukareko.horseracebet.dao.horse;

import by.epam.javawebtraining.kukareko.horseracebet.dao.builder.AbstractBuilder;
import by.epam.javawebtraining.kukareko.horseracebet.dao.builder.FactoryBuilder;
import by.epam.javawebtraining.kukareko.horseracebet.dao.builder.TypeBuilder;
import by.epam.javawebtraining.kukareko.horseracebet.dao.AbstractDAO;
import by.epam.javawebtraining.kukareko.horseracebet.model.entity.Horse;
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

    private static HorseDAOImpl dao;
    private static final ReentrantLock LOCK = new ReentrantLock();
    private static final AbstractBuilder builder;

    static {
        builder = FactoryBuilder.getBuilder(TypeBuilder.HORSE);
    }

    private HorseDAOImpl() {
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
    public Horse getById(Long id) {
        Map<Integer, Object> queryParams = new HashMap<>();
        queryParams.put(1, id);
        Horse horse = null;

        try {
            ResultSet rs = executeQuery(configurationManager.getProperty("SQL.selectHorseById"), queryParams, true);
            if ((rs != null) && (rs.next())) {
                horse = (Horse) builder.getEntity(rs);
            }
        } catch (SQLException ex) {
            LOGGER.error(ex.getMessage());
        }
        return horse;
    }

    @Override
    public List<Horse> getAll() {
        ResultSet rs;
        List<Horse> horses = new ArrayList<>();

        try {
            rs = executeQuery(configurationManager.getProperty("SQL.selectHorse"), null, true);
            if (rs != null) {
                while (rs.next()) {
                    horses.add((Horse) builder.getEntity(rs));
                }
            }
        } catch (SQLException ex) {
            LOGGER.error(ex.getMessage());
        }
        return horses;
    }

    @Override
    public boolean save(Horse horse) {
        Map<Integer, Object> queryParams = buildParamsMap(horse);

        try {
            executeQuery(configurationManager.getProperty("SQL.insertHorse"), queryParams, false);
        } catch (SQLException ex) {
            LOGGER.error(ex.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public boolean update(Horse horse) {
        Map<Integer, Object> queryParams = buildParamsMap(horse);
        queryParams.put(5, horse.getId());

        try {
            executeQuery(configurationManager.getProperty("SQL.updateHorse"), queryParams, false);
        } catch (SQLException ex) {
            LOGGER.error(ex.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public boolean delete(Horse horse) {
        Map<Integer, Object> queryParams = new HashMap<>();
        queryParams.put(1, horse.getId());

        try {
            executeQuery(configurationManager.getProperty("SQL.deleteHorse"), queryParams, false);
        } catch (SQLException ex) {
            LOGGER.error(ex.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public List<Horse> getJoinHorseStartingPrice() {
        ResultSet rs;
        List<Horse> horses = new ArrayList<>();

        try {
            rs = executeQuery(configurationManager.getProperty("SQL.selectHorsesJoinHorseStartingPrice"), null, true);
            if (rs != null) {
                while (rs.next()) {
                    horses.add((Horse) builder.getEntity(rs));
                }
            }
        } catch (SQLException ex) {
            LOGGER.error(ex.getMessage());
        }
        return horses;
    }

    @Override
    public List<Horse> getJoinFirstHorseBetAndHorseStartingPrice() {
        ResultSet rs;
        List<Horse> horses = new ArrayList<>();

        try {
            rs = executeQuery(configurationManager.getProperty("SQL.selectHorsesJoinStartingPriceAndFirstHorseBet"), null, true);
            if (rs != null) {
                while (rs.next()) {
                    horses.add((Horse) builder.getEntity(rs));
                }
            }
        } catch (SQLException ex) {
            LOGGER.error(ex.getMessage());
        }
        return horses;
    }

    @Override
    public Horse getJoinHorseStartingPriceByStartingPriceId(long startingPriceId) {
        ResultSet rs;
        Horse horse = null;
        Map<Integer, Object> queryParams = new HashMap<>();
        queryParams.put(1, startingPriceId);

        try {
            rs = executeQuery(configurationManager.getProperty("SQL.selectHorseJoinStartingPriceAndFirstHorseBySPId"), queryParams, true);
            if (rs != null) {
                while (rs.next()) {
                    horse = (Horse) builder.getEntity(rs);
                }
            }
        } catch (SQLException ex) {
            LOGGER.error(ex.getMessage());
        }
        return horse;
    }

    @Override
    public List<Horse> getJoinSecondHorseBetAndHorseStartingPrice() {
        ResultSet rs;
        List<Horse> horses = new ArrayList<>();

        try {
            rs = executeQuery(configurationManager.getProperty("SQL.selectHorsesJoinStartingPriceAndSecondHorseBet"), null, true);
            if (rs != null) {
                while (rs.next()) {
                    horses.add((Horse) builder.getEntity(rs));
                }
            }
        } catch (SQLException ex) {
            LOGGER.error(ex.getMessage());
        }
        return horses;
    }

    @Override
    public List<Horse> getJoinHorseStartingPriceByRaceId(long raceId) {
        ResultSet rs;
        Map<Integer, Object> queryParams = new HashMap<>();
        queryParams.put(1, raceId);
        List<Horse> horses = new ArrayList<>();

        try {
            rs = executeQuery(configurationManager.getProperty("SQL.selectHorsesJoinHorseStartingPriceByRaceId"), queryParams, true);
            if (rs != null) {
                while (rs.next()) {
                    horses.add((Horse) builder.getEntity(rs));
                }
            }
        } catch (SQLException ex) {
            LOGGER.error(ex.getMessage());
        }
        return horses;
    }

    @Override
    public List<Horse> getJoinHorseStartingPriceExcludingByRaceId(long raceId) {
        ResultSet rs;
        Map<Integer, Object> queryParams = new HashMap<>();
        queryParams.put(1, raceId);
        List<Horse> horses = new ArrayList<>();

        try {
            rs = executeQuery(configurationManager.getProperty("SQL.selectHorsesJoinHorseStartingPriceExcludingByRaceId"), queryParams, true);
            if (rs != null) {
                while (rs.next()) {
                    horses.add((Horse) builder.getEntity(rs));
                }
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