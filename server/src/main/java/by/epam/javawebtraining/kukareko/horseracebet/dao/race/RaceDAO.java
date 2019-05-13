package by.epam.javawebtraining.kukareko.horseracebet.dao.race;

import by.epam.javawebtraining.kukareko.horseracebet.dao.DAO;
import by.epam.javawebtraining.kukareko.horseracebet.model.entity.Race;
import by.epam.javawebtraining.kukareko.horseracebet.model.exception.HorseRaceBetException;

import java.util.List;

/**
 * @author Yulya Kukareko
 * @version 1.0 18 Apr 2019
 */
public interface RaceDAO extends DAO<Race, Long> {

    List<Race> getJoinBet() throws HorseRaceBetException;

    List<Race> getJoinHorseStarting() throws HorseRaceBetException;

    List<Race> getJoinHorseStartingPriceById(long raceId) throws HorseRaceBetException;

    List<Race> getCompletedRacesNotJoinResult() throws HorseRaceBetException;

    List<Race> getCompletedRacesJoinResult() throws HorseRaceBetException;

    List<Race> getNotJoinResult() throws HorseRaceBetException;
}
