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

    /**
     * @return races of bets
     * @throws HorseRaceBetException
     */
    List<Race> getJoinBet() throws HorseRaceBetException;

    /**
     * @return races of horse starting prices
     * @throws HorseRaceBetException
     */
    List<Race> getJoinHorseStarting() throws HorseRaceBetException;

    /**
     * @param raceId
     * @return races of horse starting prices by race id
     * @throws HorseRaceBetException
     */
    List<Race> getJoinHorseStartingPriceById(long raceId) throws HorseRaceBetException;

    /**
     * @return completed races which haven't result
     * @throws HorseRaceBetException
     */
    List<Race> getCompletedRacesNotJoinResult() throws HorseRaceBetException;

    /**
     * @return completed races which have result
     * @throws HorseRaceBetException
     */
    List<Race> getCompletedRacesJoinResult() throws HorseRaceBetException;

    /**
     * @return races which haven't result
     * @throws HorseRaceBetException
     */
    List<Race> getNotJoinResult() throws HorseRaceBetException;
}
