package by.epam.javawebtraining.kukareko.horseracebet.dao.builder;

import static by.epam.javawebtraining.kukareko.horseracebet.util.constant.EntityBuilderColumnConstant.*;

import by.epam.javawebtraining.kukareko.horseracebet.model.entity.Result;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Yulya Kukareko
 * @version 1.0 15 Apr 2019
 */
public class ResultBuilder extends AbstractBuilder<Result> {

    private static final ReentrantLock LOCK;

    private static ResultBuilder instance;

    static {
        LOCK = new ReentrantLock();
    }

    private ResultBuilder() {
    }

    public static ResultBuilder getInstance() {
        if (instance == null) {
            LOCK.lock();
            if (instance == null) {
                instance = new ResultBuilder();
            }
            LOCK.unlock();
        }
        return instance;
    }

    @Override
    public Result getEntity(ResultSet rs) {
        Result resultRace = null;

        try {
            resultRace = new Result();
            resultRace.setId(rs.getLong(ID_COLUMN));
            resultRace.setRaceId(rs.getLong(RACE_ID_COLUMN));
            resultRace.setHorseId(rs.getLong(HORSE_ID_COLUMN));
            resultRace.setPlace(rs.getInt(PLACE_COLUMN));
        } catch (SQLException ex) {
            LOGGER.error(ex.getMessage());
        }

        return resultRace;
    }
}
