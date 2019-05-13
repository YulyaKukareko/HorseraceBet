package by.epam.javawebtraining.kukareko.horseracebet.dao.bet;

import by.epam.javawebtraining.kukareko.horseracebet.dao.builder.AbstractBuilder;
import by.epam.javawebtraining.kukareko.horseracebet.dao.builder.FactoryBuilder;
import by.epam.javawebtraining.kukareko.horseracebet.dao.builder.TypeBuilder;
import by.epam.javawebtraining.kukareko.horseracebet.dao.AbstractDAO;
import by.epam.javawebtraining.kukareko.horseracebet.model.entity.Bet;
import by.epam.javawebtraining.kukareko.horseracebet.model.entity.BetType;
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
 * @version 1.0 21 Apr 2019
 */
public class BetDAOImpl extends AbstractDAO implements BetDAO {

    private static final ReentrantLock LOCK = new ReentrantLock();

    private static BetDAOImpl dao;

    private AbstractBuilder builder;

    private BetDAOImpl() {
        builder = FactoryBuilder.getBuilder(TypeBuilder.BET);
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
    public Bet getById(Long id) throws HorseRaceBetException {
        Map<Integer, Object> queryParams = new HashMap<>();

        queryParams.put(1, id);
        Bet race = null;

        ResultSet rs = executeQuery(configurationManager.getProperty("SQL.selectBetById"), queryParams, true);

        try {
            if (rs.next()) {
                race = (Bet) builder.getEntity(rs);
            }
        } catch (SQLException ex) {
            LOGGER.error(ex.getMessage());
        }
        return race;
    }

    @Override
    public List<Bet> getAll() throws HorseRaceBetException {
        ResultSet rs = executeQuery(configurationManager.getProperty("SQL.selectBet"), null, true);

        return getBets(rs);
    }

    @Override
    public List<Bet> getByRaceIdAndBetType(long raceId, BetType type, long userId) throws HorseRaceBetException {
        Map<Integer, Object> queryParams = new HashMap<>();
        queryParams.put(1, userId);
        queryParams.put(2, raceId);
        queryParams.put(3, type);

        ResultSet rs = executeQuery(configurationManager.getProperty("SQL.selectBetByRaceIdAndBetType"), queryParams, true);
        return getBets(rs);
    }

    @Override
    public void save(Bet bet) throws HorseRaceBetException {
        Map<Integer, Object> queryParams = buildParamsMap(bet);

        executeQuery(configurationManager.getProperty("SQL.insertBet"), queryParams, false);
    }

    @Override
    public void update(Bet bet) throws HorseRaceBetException {
        Map<Integer, Object> queryParams = buildParamsMap(bet);
        queryParams.put(5, bet.getId());

        executeQuery(configurationManager.getProperty("SQL.updateBet"), queryParams, false);
    }

    @Override
    public void delete(Bet bet) throws HorseRaceBetException {
        Map<Integer, Object> queryParams = new HashMap<>();
        queryParams.put(1, bet.getId());

        executeQuery(configurationManager.getProperty("SQL.deleteBet"), queryParams, false);
    }

    private List<Bet> getBets(ResultSet rs) {
        List<Bet> bets = new ArrayList<>();

        try {
            while (rs.next()) {
                bets.add((Bet) builder.getEntity(rs));
            }
        } catch (SQLException ex) {
            LOGGER.error(ex.getMessage());
        }

        return bets;
    }

    private Map<Integer, Object> buildParamsMap(Bet bet) {
        Map<Integer, Object> queryParams = new HashMap<>();

        queryParams.put(1, bet.getType());
        queryParams.put(2, bet.getFirstStartingPriceHorseId());

        if (bet.getSecondStartingPriceHorseId() == 0) {
            queryParams.put(3, "NULL");
        } else {
            queryParams.put(3, bet.getSecondStartingPriceHorseId());
        }
        queryParams.put(4, bet.getCoefficient());

        return queryParams;
    }
}
