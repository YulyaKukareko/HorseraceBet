package by.epam.javawebtraining.kukareko.horseracebet.dao.builder;

import static by.epam.javawebtraining.kukareko.horseracebet.util.constant.EntityBuilderColumnConstant.*;

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
            sp.setId(rs.getLong(ID_COLUMN));
            sp.setRaceId(rs.getLong(RACE_ID_COLUMN));
            sp.setHorseId(rs.getLong(HORSE_ID_COLUMN));
            sp.setSp(rs.getFloat(SP_COLUMN));
        } catch (SQLException ex) {
            LOGGER.error(ex.getMessage());
        }

        return sp;
    }
}
