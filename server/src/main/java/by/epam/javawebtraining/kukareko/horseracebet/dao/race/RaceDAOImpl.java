package by.epam.javawebtraining.kukareko.horseracebet.dao.race;

import static by.epam.javawebtraining.kukareko.horseracebet.util.constant.SQLConstant.*;

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

    private String selectById;
    private String select;
    private String insert;
    private String update;
    private String delete;
    private String selectJoinHorseStartingPrice;
    private String selectJoinHorseStartingPriceById;
    private String selectCompletedRace;
    private String selectNotJoinResult;
    private String selectCompletedRaceJoinResult;
    private String selectJoinBet;

    private AbstractBuilder builder;

    private RaceDAOImpl() {
        this.builder = FactoryBuilder.getBuilder(TypeBuilder.RACE);
        this.selectById = configurationManager.getProperty(SQL_SELECT_RACE_BY_ID);
        this.select = configurationManager.getProperty(SQL_SELECT_RACE);
        this.insert = configurationManager.getProperty(SQL_INSERT_RACE);
        this.update = configurationManager.getProperty(SQL_UPDATE_RACE);
        this.delete = configurationManager.getProperty(SQL_DELETE_RACE);
        this.selectJoinHorseStartingPrice = configurationManager.getProperty(SQL_SELECT_RACE_JOIN_HORSE_STARTING_PRICE);
        this.selectJoinHorseStartingPriceById = configurationManager.getProperty(SQL_SELECT_RACE_JOIN_HORSE_STARTING_PRICE_BY_ID);
        this.selectCompletedRace = configurationManager.getProperty(SQL_SELECT_COMPLETED_RACE);
        this.selectNotJoinResult = configurationManager.getProperty(SQL_SELECT_RACE_NOT_JOIN_RESULT);
        this.selectCompletedRaceJoinResult = configurationManager.getProperty(SQL_SELECT_COMPLETED_RACE_JOIN_RESULT);
        this.selectJoinBet = configurationManager.getProperty(SQL_SELECT_RACE_JOIN_BET);
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

        ResultSet rs = executeQuery(selectById, queryParams, true);

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
        ResultSet rs = executeQuery(select, null, true);

        return getRaces(rs);
    }

    @Override
    public void save(Race race) throws HorseRaceBetException {
        Map<Integer, Object> queryParams = buildParamsMap(race);

        executeQuery(insert, queryParams, false);
    }

    @Override
    public void update(Race race) throws HorseRaceBetException {
        Map<Integer, Object> queryParams = buildParamsMap(race);
        queryParams.put(7, race.getId());

        executeQuery(update, queryParams, false);
    }

    @Override
    public void delete(Race race) throws HorseRaceBetException {
        Map<Integer, Object> queryParams = new HashMap<>();
        queryParams.put(1, race.getId());

        executeQuery(delete, queryParams, false);
    }

    @Override
    public List<Race> getJoinHorseStarting() throws HorseRaceBetException {
        ResultSet rs = executeQuery(selectJoinHorseStartingPrice, null, true);

        return getRaces(rs);
    }

    @Override
    public List<Race> getJoinBet() throws HorseRaceBetException {
        ResultSet rs = executeQuery(selectJoinBet, null, true);

        return getRaces(rs);
    }

    @Override
    public List<Race> getJoinHorseStartingPriceById(long id) throws HorseRaceBetException {
        Map<Integer, Object> queryParams = new HashMap<>();
        queryParams.put(1, id);

        ResultSet rs = executeQuery(selectJoinHorseStartingPriceById, queryParams, true);
        return getRaces(rs);
    }

    @Override
    public List<Race> getCompletedRacesNotJoinResult() throws HorseRaceBetException {
        ResultSet rs = executeQuery(selectCompletedRace, null, true);

        return getRaces(rs);
    }

    @Override
    public List<Race> getNotJoinResult() throws HorseRaceBetException {
        ResultSet rs = executeQuery(selectNotJoinResult, null, true);

        return getRaces(rs);
    }

    @Override
    public List<Race> getCompletedRacesJoinResult() throws HorseRaceBetException {
        ResultSet rs = executeQuery(selectCompletedRaceJoinResult, null, true);

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
