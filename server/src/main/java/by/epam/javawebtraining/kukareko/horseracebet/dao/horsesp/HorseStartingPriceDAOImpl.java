package by.epam.javawebtraining.kukareko.horseracebet.dao.horsesp;

import static by.epam.javawebtraining.kukareko.horseracebet.util.constant.SQLConstant.*;

import by.epam.javawebtraining.kukareko.horseracebet.dao.builder.AbstractBuilder;
import by.epam.javawebtraining.kukareko.horseracebet.dao.builder.FactoryBuilder;
import by.epam.javawebtraining.kukareko.horseracebet.dao.builder.TypeBuilder;
import by.epam.javawebtraining.kukareko.horseracebet.dao.AbstractDAO;
import by.epam.javawebtraining.kukareko.horseracebet.model.entity.HorseStartingPrice;
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
 * @version 1.0 20 Apr 2019
 */
public class HorseStartingPriceDAOImpl extends AbstractDAO implements HorseStartingPriceDAO {

    private static final ReentrantLock LOCK = new ReentrantLock();

    private static HorseStartingPriceDAOImpl dao;

    private String selectById;
    private String select;
    private String insert;
    private String update;
    private String delete;
    private String selectJoinFirstHorseBet;
    private String selectJoinSecondHorseBet;
    private String selectByRaceId;
    private String selectCountByRaceId;

    private AbstractBuilder builder;

    private HorseStartingPriceDAOImpl() {
        this.selectById = configurationManager.getProperty(SQL_SELECT_HORSE_STARTING_PRICE_BY_ID);
        this.select = configurationManager.getProperty(SQL_SELECT_STARTING_PRICE);
        this.insert = configurationManager.getProperty(SQL_INSERT_HORSE_STARTING_PRICE);
        this.update = configurationManager.getProperty(SQL_UPDATE_HORSE_STARTING_PRICE);
        this.delete = configurationManager.getProperty(SQL_DELETE_HORSE_STARTING_PRICE);
        this.selectJoinFirstHorseBet = configurationManager.getProperty(SQL_SELECT_HORSE_STARTING_PRICES_JOIN_FIRST_HORSE_BET);
        this.selectJoinSecondHorseBet = configurationManager.getProperty(SQL_SELECT_HORSE_STARTING_PRICES_JOIN_SECOND_HORSE_BET);
        this.selectByRaceId = configurationManager.getProperty(SQL_SELECT_STARTING_PRICE_BY_RACE_ID);
        this.selectCountByRaceId = configurationManager.getProperty(SQL_SELECT_STARTING_PRICE_COUNT_BY_RACE_ID);
        this.builder = FactoryBuilder.getBuilder(TypeBuilder.HORSE_STARTING_PRICE);
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
    public HorseStartingPrice getById(Long id) throws HorseRaceBetException {
        Map<Integer, Object> queryParams = new HashMap<>();
        queryParams.put(1, id);
        HorseStartingPrice horseStartingPrice = null;

        ResultSet rs = executeQuery(selectById, queryParams, true);

        try {
            if (rs.next()) {
                horseStartingPrice = (HorseStartingPrice) builder.getEntity(rs);
            }
        } catch (SQLException ex) {
            LOGGER.error(ex.getMessage());
        }
        return horseStartingPrice;
    }

    @Override
    public List<HorseStartingPrice> getAll() throws HorseRaceBetException {
        ResultSet rs = executeQuery(select, null, true);

        return getHorseStartingPrices(rs);
    }

    @Override
    public void save(HorseStartingPrice horseSP) throws HorseRaceBetException {
        Map<Integer, Object> queryParams = buildParamsMap(horseSP);

        executeQuery(insert, queryParams, false);
    }

    @Override
    public void update(HorseStartingPrice horseSP) throws HorseRaceBetException {
        Map<Integer, Object> queryParams = buildParamsMap(horseSP);
        queryParams.put(4, horseSP.getId());

        executeQuery(update, queryParams, false);
    }

    @Override
    public void delete(HorseStartingPrice horseSP) throws HorseRaceBetException {
        Map<Integer, Object> queryParams = new HashMap<>();
        queryParams.put(1, horseSP.getId());

        executeQuery(delete, queryParams, false);
    }

    @Override
    public List<HorseStartingPrice> getJoinFirstHorsesBet() throws HorseRaceBetException {
        ResultSet rs = executeQuery(selectJoinFirstHorseBet, null, true);

        return getHorseStartingPrices(rs);
    }

    @Override
    public List<HorseStartingPrice> getJoinSecondHorsesBet() throws HorseRaceBetException {
        ResultSet rs = executeQuery(selectJoinSecondHorseBet, null, true);

        return getHorseStartingPrices(rs);
    }

    @Override
    public List<HorseStartingPrice> getByRaceId(long raceId) throws HorseRaceBetException {
        Map<Integer, Object> queryParams = new HashMap<>();
        queryParams.put(1, raceId);

        ResultSet rs = executeQuery(selectByRaceId, queryParams, true);
        return getHorseStartingPrices(rs);
    }

    @Override
    public Integer getCountByRaceId(long raceId) throws HorseRaceBetException {
        Integer contRow = null;
        Map<Integer, Object> queryParams = new HashMap<>();
        queryParams.put(1, raceId);

        ResultSet rs = executeQuery(selectCountByRaceId, queryParams, true);
        try {
            while (rs.next()) {
                contRow = rs.getInt(1);
            }
        } catch (SQLException ex) {
            LOGGER.error(ex.getMessage());
        }
        return contRow;
    }

    private List<HorseStartingPrice> getHorseStartingPrices(ResultSet rs) {
        List<HorseStartingPrice> horseStartingPrices = new ArrayList<>();

        try {
            while (rs.next()) {
                horseStartingPrices.add((HorseStartingPrice) builder.getEntity(rs));
            }
        } catch (SQLException ex) {
            LOGGER.error(ex.getMessage());
        }
        return horseStartingPrices;
    }

    private Map<Integer, Object> buildParamsMap(HorseStartingPrice horseSP) {
        Map<Integer, Object> queryParams = new HashMap<>();

        queryParams.put(1, horseSP.getRaceId());
        queryParams.put(2, horseSP.getHorseId());
        queryParams.put(3, horseSP.getSp());

        return queryParams;
    }
}
