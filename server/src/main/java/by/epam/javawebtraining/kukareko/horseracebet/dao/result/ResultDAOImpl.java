package by.epam.javawebtraining.kukareko.horseracebet.dao.result;

import by.epam.javawebtraining.kukareko.horseracebet.dao.builder.AbstractBuilder;
import by.epam.javawebtraining.kukareko.horseracebet.dao.builder.FactoryBuilder;
import by.epam.javawebtraining.kukareko.horseracebet.dao.builder.TypeBuilder;
import by.epam.javawebtraining.kukareko.horseracebet.dao.AbstractDAO;
import by.epam.javawebtraining.kukareko.horseracebet.model.entity.Result;

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

    private static ResultDAOImpl dao;
    private static final ReentrantLock LOCK = new ReentrantLock();
    private static final AbstractBuilder builder;

    static {
        builder = FactoryBuilder.getBuilder(TypeBuilder.RESULT);
    }

    private ResultDAOImpl() {
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
    public Result getById(Long id) {
        Map<Integer, Object> queryParams = new HashMap<>();
        queryParams.put(1, id);
        Result result = null;

        try {
            ResultSet rs = executeQuery(configurationManager.getProperty("SQL.selectResultById"), queryParams, true);
            if ((rs != null) && (rs.next())) {
                result = (Result) builder.getEntity(rs);
            }
        } catch (SQLException ex) {
            LOGGER.error(ex.getMessage());
        }
        return result;
    }

    @Override
    public List<Result> getAll() {
        ResultSet rs;
        List<Result> results = new ArrayList<>();

        try {
            rs = executeQuery(configurationManager.getProperty("SQL.selectResult"), null, true);
            if (rs != null) {
                while (rs.next()) {
                    results.add((Result) builder.getEntity(rs));
                }
            }
        } catch (SQLException ex) {
            LOGGER.error(ex.getMessage());
        }
        return results;
    }

    @Override
    public boolean save(Result result) {
        Map<Integer, Object> queryParams = buildParamsMap(result);

        try {
            executeQuery(configurationManager.getProperty("SQL.insertResult"), queryParams, false);
        } catch (SQLException ex) {
            LOGGER.error(ex.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public boolean update(Result result) {
        Map<Integer, Object> queryParams = buildParamsMap(result);
        queryParams.put(4, result.getId());

        try {
            executeQuery(configurationManager.getProperty("SQL.updateResult"), queryParams, false);
        } catch (SQLException ex) {
            LOGGER.error(ex.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public boolean delete(Result result) {
        Map<Integer, Object> queryParams = new HashMap<>();
        queryParams.put(1, result.getId());

        try {
            executeQuery(configurationManager.getProperty("SQL.deleteResult"), queryParams, false);
        } catch (SQLException ex) {
            LOGGER.error(ex.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public List<Result> getByRaceId(long raceId) {
        ResultSet rs;
        List<Result> results = new ArrayList<>();
        Map<Integer, Object> queryParams = new HashMap<>();
        queryParams.put(1, raceId);

        try {
            rs = executeQuery(configurationManager.getProperty("SQL.selectResultByRaceId"), queryParams, true);
            if (rs != null) {
                while (rs.next()) {
                    results.add((Result) builder.getEntity(rs));
                }
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
