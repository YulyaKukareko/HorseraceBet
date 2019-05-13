package by.epam.javawebtraining.kukareko.horseracebet.dao.builder;

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
            race.setId(rs.getLong("id"));
            race.setName(rs.getString("name"));
            race.setCountryId(rs.getLong("country_id"));
            race.setTime(rs.getTimestamp("time"));
            race.setType(RaceType.valueOf(rs.getString("type").toUpperCase()));
            race.setPurse(rs.getBigDecimal("purse"));
            race.setDistance(rs.getDouble("distance"));
        } catch (SQLException ex) {
            LOGGER.error(ex.getMessage());
        }

        return race;
    }
}
