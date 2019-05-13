package by.epam.javawebtraining.kukareko.horseracebet.dao.result;

import by.epam.javawebtraining.kukareko.horseracebet.dao.builder.AbstractBuilder;
import by.epam.javawebtraining.kukareko.horseracebet.dao.builder.FactoryBuilder;
import by.epam.javawebtraining.kukareko.horseracebet.dao.builder.TypeBuilder;
import by.epam.javawebtraining.kukareko.horseracebet.dao.AbstractDAO;
import by.epam.javawebtraining.kukareko.horseracebet.model.entity.Result;
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
 * @version 1.0 24 Apr 2019
 */
public class ResultDAOImpl extends AbstractDAO implements ResultDAO {

    private static final ReentrantLock LOCK = new ReentrantLock();

    private static ResultDAOImpl dao;

    private AbstractBuilder builder;

    private ResultDAOImpl() {
        builder = FactoryBuilder.getBuilder(TypeBuilder.RESULT);
    }

    public static ResultDAOImpl getInstance() {
        if (dao == null) {
            LOCK.lock();
            if (dao == null) {
                dao = new ResultDAOImpl();
            }
            LOCK.unlock();
        }
        return dao;
    }

    @Override
    public Result getById(Long id) throws HorseRaceBetException {
        Map<Integer, Object> queryParams = new HashMap<>();
        queryParams.put(1, id);
        Result result = null;

        ResultSet rs = executeQuery(configurationManager.getProperty("SQL.selectResultById"), queryParams, true);
        try {
            if (rs.next()) {
                result = (Result) builder.getEntity(rs);
            }
        } catch (SQLException ex) {
            LOGGER.error(ex.getMessage());
        }
        return result;
    }

    @Override
    public List<Result> getAll() throws HorseRaceBetException {
        ResultSet rs = executeQuery(configurationManager.getProperty("SQL.selectResult"), null, true);

        return getResults(rs);
    }

    @Override
    public void save(Result result) throws HorseRaceBetException {
        Map<Integer, Object> queryParams = buildParamsMap(result);

        executeQuery(configurationManager.getProperty("SQL.insertResult"), queryParams, false);
    }

    @Override
    public void update(Result result) throws HorseRaceBetException {
        Map<Integer, Object> queryParams = buildParamsMap(result);
        queryParams.put(4, result.getId());

        executeQuery(configurationManager.getProperty("SQL.updateResult"), queryParams, false);
    }

    @Override
    public void delete(Result result) throws HorseRaceBetException {
        Map<Integer, Object> queryParams = new HashMap<>();
        queryParams.put(1, result.getId());

        executeQuery(configurationManager.getProperty("SQL.deleteResult"), queryParams, false);
    }

    @Override
    public List<Result> getByRaceId(long raceId) throws HorseRaceBetException {
        Map<Integer, Object> queryParams = new HashMap<>();
        queryParams.put(1, raceId);

        ResultSet rs = executeQuery(configurationManager.getProperty("SQL.selectResultByRaceId"), queryParams, true);
        return getResults(rs);
    }

    private List<Result> getResults(ResultSet rs) {
        List<Result> results = new ArrayList<>();

        try {
            while (rs.next()) {
                results.add((Result) builder.getEntity(rs));
            }
        } catch (SQLException ex) {
            LOGGER.error(ex.getMessage());
        }
        return results;
    }

    private Map<Integer, Object> buildParamsMap(Result result) {
        Map<Integer, Object> queryParams = new HashMap<>();

        queryParams.put(1, result.getRaceId());
        queryParams.put(2, result.getPlace());
        queryParams.put(3, result.getHorseId());

        return queryParams;
    }
}
