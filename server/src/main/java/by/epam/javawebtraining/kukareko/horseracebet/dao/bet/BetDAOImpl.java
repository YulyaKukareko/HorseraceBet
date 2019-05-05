package by.epam.javawebtraining.kukareko.horseracebet.dao.bet;

import by.epam.javawebtraining.kukareko.horseracebet.dao.builder.AbstractBuilder;
import by.epam.javawebtraining.kukareko.horseracebet.dao.builder.FactoryBuilder;
import by.epam.javawebtraining.kukareko.horseracebet.dao.builder.TypeBuilder;
import by.epam.javawebtraining.kukareko.horseracebet.dao.AbstractDAO;
import by.epam.javawebtraining.kukareko.horseracebet.model.entity.Bet;
import by.epam.javawebtraining.kukareko.horseracebet.model.entity.BetType;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Yulya Kukareko
 * @version 1.0 21 Apr 2019
 */
public class BetDAOImpl extends AbstractDAO implements BetDAO {

    private static BetDAOImpl dao;
    private static final ReentrantLock LOCK = new ReentrantLock();
    private static final AbstractBuilder builder;

    static {
        builder = FactoryBuilder.getBuilder(TypeBuilder.BET);
    }

    private BetDAOImpl() {
    }

    public static BetDAOImpl getInstance() {
        if (dao == null) {
            LOCK.lock();
            if (dao == null) {
                dao = new BetDAOImpl();
            }
            LOCK.unlock();
        }
        return dao;
    }

    @Override
    public Bet getById(Long id) {
        Map<Integer, Object> queryParams = new HashMap<>();
        queryParams.put(1, id);
        Bet race = null;

        try {
            ResultSet rs = executeQuery(configurationManager.getProperty("SQL.selectBetById"), queryParams, true);
            if ((rs != null) && (rs.next())) {
                race = (Bet) builder.getEntity(rs);
            }
        } catch (SQLException ex) {
            LOGGER.error(ex.getMessage());
        }
        return race;
    }

    @Override
    public List<Bet> getAll() {
        ResultSet rs;
        List<Bet> races = new ArrayList<>();

        try {
            rs = executeQuery(configurationManager.getProperty("SQL.selectBet"), null, true);
            if (rs != null) {
                while (rs.next()) {
                    races.add((Bet) builder.getEntity(rs));
                }
            }
        } catch (SQLException ex) {
            LOGGER.error(ex.getMessage());
        }
        return races;
    }

    @Override
    public List<Bet> getByRaceIdAndBetType(long raceId, BetType type, long userId) {
        ResultSet rs;
        List<Bet> bets = new ArrayList<>();
        Map<Integer, Object> queryParams = new HashMap<>();
        queryParams.put(1, userId);
        queryParams.put(2, raceId);
        queryParams.put(3, type);

        try {
            rs = executeQuery(configurationManager.getProperty("SQL.selectBetByRaceIdAndBetType"), queryParams, true);
            if (rs != null) {
                while (rs.next()) {
                    bets.add((Bet) builder.getEntity(rs));
                }
            }
        } catch (SQLException ex) {
            LOGGER.error(ex.getMessage());
        }
        return bets;
    }

    @Override
    public boolean save(Bet bet) {
        Map<Integer, Object> queryParams = buildParamsMap(bet);

        try {
            executeQuery(configurationManager.getProperty("SQL.insertBet"), queryParams, false);
        } catch (SQLException ex) {
            LOGGER.error(ex.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public boolean update(Bet bet) {
        Map<Integer, Object> queryParams = buildParamsMap(bet);
        queryParams.put(5, bet.getId());

        try {
            executeQuery(configurationManager.getProperty("SQL.updateBet"), queryParams, false);
        } catch (SQLException ex) {
            LOGGER.error(ex.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public boolean delete(Bet bet) {
        Map<Integer, Object> queryParams = new HashMap<>();
        queryParams.put(1, bet.getId());

        try {
            executeQuery(configurationManager.getProperty("SQL.deleteBet"), queryParams, false);
        } catch (SQLException ex) {
            LOGGER.error(ex.getMessage());
            return false;
        }
        return true;
    }

    private Map<Integer, Object> buildParamsMap(Bet bet) {
        Map<Integer, Object> queryParams = new HashMap<>();

        queryParams.put(1, bet.getType());
        queryParams.put(2, bet.getFirstStartingPriceHorseId());
        if (bet.getSecondStartingPriceHorseId() == -1) {
            queryParams.put(3, "NULL");
        } else {
            queryParams.put(3, bet.getSecondStartingPriceHorseId());
        }
        queryParams.put(4, bet.getCoefficient());

        return queryParams;
    }
}
