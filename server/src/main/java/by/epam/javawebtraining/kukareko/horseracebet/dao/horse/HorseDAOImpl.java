package by.epam.javawebtraining.kukareko.horseracebet.dao.horse;

import static by.epam.javawebtraining.kukareko.horseracebet.util.constant.SQLConstant.*;

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

    private String selectById;
    private String select;
    private String insert;
    private String update;
    private String delete;
    private String selectJoinHorseStartingPrice;
    private String selectJoinStartingPriceAndFirstHorseBet;
    private String selectJoinStartingPriceAndFirstHorseBySPId;
    private String selectJoinStartingPriceAndSecondHorseBet;
    private String selectJoinHorseStartingPriceByRaceId;
    private String selectJoinHorseStartingPriceExcludingByRaceId;

    private AbstractBuilder builder;

    private HorseDAOImpl() {
        this.builder = FactoryBuilder.getBuilder(TypeBuilder.HORSE);
        this.selectById = configurationManager.getProperty(SQL_SELECT_HORSE_BY_ID);
        this.select = configurationManager.getProperty(SQL_SELECT_HORSE);
        this.insert = configurationManager.getProperty(SQL_INSERT_HORSE);
        this.update = configurationManager.getProperty(SQL_UPDATE_HORSE);
        this.delete = configurationManager.getProperty(SQL_DELETE_HORSE);
        this.selectJoinHorseStartingPrice = configurationManager.getProperty(SQL_SELECT_HORSES_JOIN_HORSE_STARTING_PRICE);
        this.selectJoinStartingPriceAndFirstHorseBet = configurationManager.getProperty(SQL_SELECT_HORSES_JOIN_STARTING_PRICE_AND_FIRST_HORSE_BET);
        this.selectJoinStartingPriceAndFirstHorseBySPId = configurationManager.getProperty(SQL_SELECT_HORSE_JOIN_STARTING_PRICE_AND_FIRST_HORSE_BY_SPID);
        this.selectJoinStartingPriceAndSecondHorseBet = configurationManager.getProperty(SQL_SELECT_HORSES_JOIN_STARTING_PRICE_AND_SECOND_HORSE_BET);
        this.selectJoinHorseStartingPriceByRaceId = configurationManager.getProperty(SQL_SELECT_HORSES_JOIN_HORSE_STARTING_PRICE_BY_RACE_ID);
        this.selectJoinHorseStartingPriceExcludingByRaceId = configurationManager.getProperty(SQL_SELECT_HORSES_JOIN_HORSE_STARTING_PRICE_EXCLUDING_BY_RACE_ID);
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

        ResultSet rs = executeQuery(selectById, queryParams, true);
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
        ResultSet rs = executeQuery(select, null, true);

        return getHorses(rs);
    }

    @Override
    public void save(Horse horse) throws HorseRaceBetException {
        Map<Integer, Object> queryParams = buildParamsMap(horse);

        executeQuery(insert, queryParams, false);
    }

    @Override
    public void update(Horse horse) throws HorseRaceBetException {
        Map<Integer, Object> queryParams = buildParamsMap(horse);
        queryParams.put(5, horse.getId());

        executeQuery(update, queryParams, false);
    }

    @Override
    public void delete(Horse horse) throws HorseRaceBetException {
        Map<Integer, Object> queryParams = new HashMap<>();
        queryParams.put(1, horse.getId());

        executeQuery(delete, queryParams, false);
    }

    @Override
    public List<Horse> getJoinHorseStartingPrice() throws HorseRaceBetException {
        ResultSet rs = executeQuery(selectJoinHorseStartingPrice, null, true);
        return getHorses(rs);
    }

    @Override
    public List<Horse> getJoinFirstHorseBetAndHorseStartingPrice() throws HorseRaceBetException {
        ResultSet rs = executeQuery(selectJoinStartingPriceAndFirstHorseBet, null, true);

        return getHorses(rs);
    }

    @Override
    public Horse getJoinHorseStartingPriceByStartingPriceId(long startingPriceId) throws HorseRaceBetException {
        Horse horse = null;
        Map<Integer, Object> queryParams = new HashMap<>();
        queryParams.put(1, startingPriceId);

        ResultSet rs = executeQuery(selectJoinStartingPriceAndFirstHorseBySPId, queryParams, true);
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
        ResultSet rs = executeQuery(selectJoinStartingPriceAndSecondHorseBet, null, true);

        return getHorses(rs);
    }

    @Override
    public List<Horse> getJoinHorseStartingPriceByRaceId(long raceId) throws HorseRaceBetException {
        Map<Integer, Object> queryParams = new HashMap<>();
        queryParams.put(1, raceId);

        ResultSet rs = executeQuery(selectJoinHorseStartingPriceByRaceId, queryParams, true);
        return getHorses(rs);
    }

    @Override
    public List<Horse> getJoinHorseStartingPriceExcludingByRaceId(long raceId) throws HorseRaceBetException {
        Map<Integer, Object> queryParams = new HashMap<>();
        queryParams.put(1, raceId);

        ResultSet rs = executeQuery(selectJoinHorseStartingPriceExcludingByRaceId, queryParams, true);
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