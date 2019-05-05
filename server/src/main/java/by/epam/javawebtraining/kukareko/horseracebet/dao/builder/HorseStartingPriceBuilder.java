package by.epam.javawebtraining.kukareko.horseracebet.dao.builder;

import by.epam.javawebtraining.kukareko.horseracebet.model.entity.HorseStartingPrice;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Yulya Kukareko
 * @version 1.0 14 Apr 2019
 */
public class HorseStartingPriceBuilder extends AbstractBuilder<HorseStartingPrice> {

    private static final ReentrantLock LOCK;
    private static HorseStartingPriceBuilder instance;

    static {
        LOCK = new ReentrantLock();
    }

    private HorseStartingPriceBuilder() {
    }

    public static HorseStartingPriceBuilder getInstance() {
        if (instance == null) {
            LOCK.lock();
            if (instance == null) {
                instance = new HorseStartingPriceBuilder();
            }
            LOCK.unlock();
        }
        return instance;
    }

    @Override
    public HorseStartingPrice getEntity(ResultSet rs) {
        HorseStartingPrice sp = null;
        try {
            sp = new HorseStartingPrice();
            sp.setId(rs.getLong("id"));
            sp.setRaceId(rs.getLong("race_id"));
            sp.setHorseId(rs.getLong("horse_id"));
            sp.setSp(rs.getFloat("sp"));
        } catch (SQLException ex) {
            LOGGER.error(ex.getMessage());
        }

        return sp;
    }
}
