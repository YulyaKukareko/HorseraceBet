package by.epam.javawebtraining.kukareko.horseracebet.dao.horse;

import by.epam.javawebtraining.kukareko.horseracebet.dao.DAO;
import by.epam.javawebtraining.kukareko.horseracebet.model.entity.Horse;
import by.epam.javawebtraining.kukareko.horseracebet.model.exception.HorseRaceBetException;

import java.util.List;

/**
 * @author Yulya Kukareko
 * @version 1.0 11 Apr 2019
 */
public interface HorseDAO extends DAO<Horse, Long> {

    /**
     * @return first horses of bets
     * @throws HorseRaceBetException
     */
    List<Horse> getJoinFirstHorseBetAndHorseStartingPrice() throws HorseRaceBetException;

    /**
     * @param startingPriceId
     * @return first horses of bets by starting price id
     * @throws HorseRaceBetException
     */
    Horse getJoinHorseStartingPriceByStartingPriceId(long startingPriceId) throws HorseRaceBetException;

    /**
     * @return second horses of bets
     * @throws HorseRaceBetException
     */
    List<Horse> getJoinSecondHorseBetAndHorseStartingPrice() throws HorseRaceBetException;

    /**
     * @param raceId
     * @return horses of horse starting price by race id
     * @throws HorseRaceBetException
     */
    List<Horse> getJoinHorseStartingPriceByRaceId(long raceId) throws HorseRaceBetException;

    /**
     * @param raceId
     * @return
     * @throws HorseRaceBetException
     */
    List<Horse> getJoinHorseStartingPriceExcludingByRaceId(long raceId) throws HorseRaceBetException;

    /**
     * @return horses of horse starting price
     * @throws HorseRaceBetException
     */
    List<Horse> getJoinHorseStartingPrice() throws HorseRaceBetException;
}
