package by.epam.javawebtraining.kukareko.horseracebet.dao.result;

import by.epam.javawebtraining.kukareko.horseracebet.dao.DAO;
import by.epam.javawebtraining.kukareko.horseracebet.model.entity.Result;

import java.util.List;

/**
 * @author Yulya Kukareko
 * @version 1.0 24 Apr 2019
 */
public interface ResultDAO extends DAO<Result, Long> {
    List<Result> getByRaceId(long raceId);
}
