package by.epam.javawebtraining.kukareko.horseracebet.dao.race;

import by.epam.javawebtraining.kukareko.horseracebet.dao.DAO;
import by.epam.javawebtraining.kukareko.horseracebet.model.entity.Race;

import java.util.List;

/**
 * @author Yulya Kukareko
 * @version 1.0 18 Apr 2019
 */
public interface RaceDAO extends DAO<Race, Long> {

    List<Race> getJoinBet();

    List<Race> getJoinHorseStarting();

    List<Race> getJoinHorseStartingPriceById(long raceId);

    List<Race> getCompletedRacesNotJoinResult();

    List<Race> getCompletedRacesJoinResult();

    List<Race> getNotJoinResult();
}
