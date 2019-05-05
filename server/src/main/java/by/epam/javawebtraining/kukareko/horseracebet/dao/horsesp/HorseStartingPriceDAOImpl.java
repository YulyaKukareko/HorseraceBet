package by.epam.javawebtraining.kukareko.horseracebet.dao.horsesp;

import by.epam.javawebtraining.kukareko.horseracebet.dao.builder.AbstractBuilder;
import by.epam.javawebtraining.kukareko.horseracebet.dao.builder.FactoryBuilder;
import by.epam.javawebtraining.kukareko.horseracebet.dao.builder.TypeBuilder;
import by.epam.javawebtraining.kukareko.horseracebet.dao.AbstractDAO;
import by.epam.javawebtraining.kukareko.horseracebet.model.entity.HorseStartingPrice;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Yulya Kukareko
 * @version 1.0 20 Apr 2019
 */
public class HorseStartingPriceDAOImpl extends AbstractDAO implements HorseStartingPriceDAO {

    private static HorseStartingPriceDAOImpl dao;
    private static final ReentrantLock LOCK = new ReentrantLock();
    private static final AbstractBuilder builder;

    static {
        builder = FactoryBuilder.getBuilder(TypeBuilder.HORSE_STARTING_PRICE);
    }

    private HorseStartingPriceDAOImpl() {
    }

    public static HorseStartingPriceDAOImpl getInstance() {
        if (dao == null) {
            LOCK.lock();
            if (dao == null) {
                dao = new HorseStartingPriceDAOImpl();
            }
            LOCK.unlock();
        }
        return dao;
    }

    @Override
    public HorseStartingPrice getById(Long id) {
        Map<Integer, Object> queryParams = new HashMap<>();
        queryParams.put(1, id);
        HorseStartingPrice horseStartingPrice = null;

        try {
            ResultSet rs = executeQuery(configurationManager.getProperty("SQL.selectHorseStartingPriceById"), queryParams, true);
            if ((rs != null) && (rs.next())) {
                horseStartingPrice = (HorseStartingPrice) builder.getEntity(rs);
            }
        } catch (SQLException ex) {
            LOGGER.error(ex.getMessage());
        }
        return horseStartingPrice;
    }

    @Override
    public List<HorseStartingPrice> getAll() {
        ResultSet rs;
        List<HorseStartingPrice> horses = new ArrayList<>();

        try {
            rs = executeQuery(configurationManager.getProperty("SQL.selectStartingPrice"), null, true);
            if (rs != null) {
                while (rs.next()) {
                    horses.add((HorseStartingPrice) builder.getEntity(rs));
                }
            }
        } catch (SQLException ex) {
            LOGGER.error(ex.getMessage());
        }
        return horses;
    }

    @Override
    public boolean save(HorseStartingPrice horseSP) {
        Map<Integer, Object> queryParams = buildParamsMap(horseSP);

        try {
            executeQuery(configurationManager.getProperty("SQL.insertHorseStartingPrice"), queryParams, false);
        } catch (SQLException ex) {
            LOGGER.error(ex.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public boolean update(HorseStartingPrice horseSP) {
        Map<Integer, Object> queryParams = buildParamsMap(horseSP);
        queryParams.put(4, horseSP.getId());

        try {
            executeQuery(configurationManager.getProperty("SQL.updateHorseStartingPrice"), queryParams, false);
        } catch (SQLException ex) {
            LOGGER.error(ex.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public boolean delete(HorseStartingPrice horseSP) {
        Map<Integer, Object> queryParams = new HashMap<>();
        queryParams.put(1, horseSP.getId());

        try {
            executeQuery(configurationManager.getProperty("SQL.deleteHorseStartingPrice"), queryParams, false);
        } catch (SQLException ex) {
            LOGGER.error(ex.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public List<HorseStartingPrice> getJoinFirstHorsesBet() {
        ResultSet rs;
        List<HorseStartingPrice> horseStartingPrices = new ArrayList<>();

        try {
            rs = executeQuery(configurationManager.getProperty("SQL.selectHorseStartingPricesJoinFirstHorseBet"), null, true);
            if (rs != null) {
                while (rs.next()) {
                    horseStartingPrices.add((HorseStartingPrice) builder.getEntity(rs));
                }
            }
        } catch (SQLException ex) {
            LOGGER.error(ex.getMessage());
        }
        return horseStartingPrices;
    }

    @Override
    public List<HorseStartingPrice> getJoinSecondHorsesBet() {
        ResultSet rs;
        List<HorseStartingPrice> horseStartingPrices = new ArrayList<>();

        try {
            rs = executeQuery(configurationManager.getProperty("SQL.selectHorseStartingPricesJoinSecondHorseBet"), null, true);
            if (rs != null) {
                while (rs.next()) {
                    horseStartingPrices.add((HorseStartingPrice) builder.getEntity(rs));
                }
            }
        } catch (SQLException ex) {
            LOGGER.error(ex.getMessage());
        }
        return horseStartingPrices;
    }

    @Override
    public List<HorseStartingPrice> getByRaceId(long raceId) {
        ResultSet rs;
        List<HorseStartingPrice> horseStartingPrices = new ArrayList<>();
        Map<Integer, Object> queryParams = new HashMap<>();
        queryParams.put(1, raceId);

        try {
            rs = executeQuery(configurationManager.getProperty("SQL.selectStartingPriceByRaceId"), queryParams, true);
            if (rs != null) {
                while (rs.next()) {
                    horseStartingPrices.add((HorseStartingPrice) builder.getEntity(rs));
                }
            }
        } catch (SQLException ex) {
            LOGGER.error(ex.getMessage());
        }
        return horseStartingPrices;
    }

    @Override
    public Integer getCountByRaceId(long raceId) {
        ResultSet rs;
        Integer contRow = null;
        Map<Integer, Object> queryParams = new HashMap<>();
        queryParams.put(1, raceId);

        try {
            rs = executeQuery(configurationManager.getProperty("SQL.selectStartingPriceCountByRaceId"), queryParams, true);
            while (rs.next()) {
                contRow = rs.getInt(1);
            }
        } catch (SQLException ex) {
            LOGGER.error(ex.getMessage());
        }
        return contRow;
    }

    private Map<Integer, Object> buildParamsMap(HorseStartingPrice horseSP) {
        Map<Integer, Object> queryParams = new HashMap<>();

        queryParams.put(1, horseSP.getRaceId());
        queryParams.put(2, horseSP.getHorseId());
        queryParams.put(3, horseSP.getSp());

        return queryParams;
    }
}
