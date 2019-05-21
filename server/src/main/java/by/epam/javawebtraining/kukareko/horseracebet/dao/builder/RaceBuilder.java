package by.epam.javawebtraining.kukareko.horseracebet.dao.builder;

import static by.epam.javawebtraining.kukareko.horseracebet.util.constant.EntityBuilderColumnConstant.*;

import by.epam.javawebtraining.kukareko.horseracebet.model.entity.Race;
import by.epam.javawebtraining.kukareko.horseracebet.model.entity.RaceType;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Yulya Kukareko
 * @version 1.0 15 Apr 2019
 */
public class RaceBuilder extends AbstractBuilder<Race> {

    private static final ReentrantLock LOCK;

    private static RaceBuilder instance;

    static {
        LOCK = new ReentrantLock();
    }

    private RaceBuilder() {
    }

    public static RaceBuilder getInstance() {
        if (instance == null) {
            LOCK.lock();
            if (instance == null) {
                instance = new RaceBuilder();
            }
            LOCK.unlock();
        }
        return instance;
    }

    @Override
    public Race getEntity(ResultSet rs) {
        Race race = null;

        try {
            race = new Race();
            race.setId(rs.getLong(ID_COLUMN));
            race.setName(rs.getString(NAME_COLUMN));
            race.setCountryId(rs.getLong(COUNTRY_ID_COLUMN));
            race.setTime(rs.getTimestamp(TIME_COLUMN));
            race.setType(RaceType.valueOf(rs.getString(TYPE_COLUMN).toUpperCase()));
            race.setPurse(rs.getBigDecimal(PURSE_COLUMN));
            race.setDistance(rs.getDouble(DISTANCE_COLUMN));
        } catch (SQLException ex) {
            LOGGER.error(ex.getMessage());
        }

        return race;
    }
}
