package by.epam.javawebtraining.kukareko.horseracebet.dao.builder;

import by.epam.javawebtraining.kukareko.horseracebet.model.entity.Bet;
import by.epam.javawebtraining.kukareko.horseracebet.model.entity.BetType;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Yulya Kukareko
 * @version 1.0 14 Apr 2019
 */
public class BetBuilder extends AbstractBuilder<Bet> {

    private static final ReentrantLock LOCK;
    private static BetBuilder instance;

    static {
        LOCK = new ReentrantLock();
    }

    private BetBuilder() {
    }

    public static BetBuilder getInstance() {
        if (instance == null) {
            LOCK.lock();
            if (instance == null) {
                instance = new BetBuilder();
            }
            LOCK.unlock();
        }
        return instance;
    }

    @Override
    public Bet getEntity(ResultSet rs) {
        Bet bet = null;
        try {
            bet = new Bet();
            bet.setId(rs.getLong("id"));
            bet.setType(BetType.valueOf(rs.getString("type").toUpperCase()));
            bet.setFirstStartingPriceHorseId(rs.getLong("first_horse_id"));
            bet.setSecondStartingPriceHorseId(rs.getLong("second_horse_id"));
            bet.setCoefficient(rs.getFloat("coefficient"));
        } catch (SQLException ex) {
            LOGGER.error(ex.getMessage());
        }

        return bet;
    }
}
