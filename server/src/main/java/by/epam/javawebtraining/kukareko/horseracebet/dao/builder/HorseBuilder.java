package by.epam.javawebtraining.kukareko.horseracebet.dao.builder;

import by.epam.javawebtraining.kukareko.horseracebet.model.entity.Horse;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Yulya Kukareko
 * @version 1.0 14 Apr 2019
 */
public class HorseBuilder extends AbstractBuilder<Horse> {

    private static final ReentrantLock LOCK;
    private static HorseBuilder instance;

    static {
        LOCK = new ReentrantLock();
    }

    private HorseBuilder() {
    }

    public static HorseBuilder getInstance() {
        if (instance == null) {
            LOCK.lock();
            if (instance == null) {
                instance = new HorseBuilder();
            }
            LOCK.unlock();
        }
        return instance;
    }

    @Override
    public Horse getEntity(ResultSet rs) {
        Horse horse = null;
        try {
            horse = new Horse();
            horse.setId(rs.getLong("id"));
            horse.setName(rs.getString("name"));
            horse.setJockey(rs.getString("jockey"));
            horse.setTrainer(rs.getString("trainer"));
            horse.setWeight(rs.getLong("weight"));
        } catch (SQLException ex) {
            LOGGER.error(ex.getMessage());
        }

        return horse;
    }
}