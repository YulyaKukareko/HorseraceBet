package by.epam.javawebtraining.kukareko.horseracebet.dao.builder;

import static by.epam.javawebtraining.kukareko.horseracebet.util.constant.EntityBuilderColumnConstant.*;

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
            bet.setId(rs.getLong(ID_COLUMN));
            bet.setType(BetType.valueOf(rs.getString(TYPE_COLUMN).toUpperCase()));
            bet.setFirstStartingPriceHorseId(rs.getLong(FIRST_HORSE_ID_COLUMN));
            bet.setSecondStartingPriceHorseId(rs.getLong(SECOND_HORSE_ID_COLUMN));
            bet.setCoefficient(rs.getFloat(COEFFICIENT_COLUMN));
        } catch (SQLException ex) {
            LOGGER.error(ex.getMessage());
        }

        return bet;
    }
}
