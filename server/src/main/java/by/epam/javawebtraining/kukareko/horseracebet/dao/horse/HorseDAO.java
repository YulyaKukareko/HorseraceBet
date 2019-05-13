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

    List<Horse> getJoinFirstHorseBetAndHorseStartingPrice() throws HorseRaceBetException;

    Horse getJoinHorseStartingPriceByStartingPriceId(long startingPriceId) throws HorseRaceBetException;

    List<Horse> getJoinSecondHorseBetAndHorseStartingPrice() throws HorseRaceBetException;

    List<Horse> getJoinHorseStartingPriceByRaceId(long raceId) throws HorseRaceBetException;

    List<Horse> getJoinHorseStartingPriceExcludingByRaceId(long raceId) throws HorseRaceBetException;

    List<Horse> getJoinHorseStartingPrice() throws HorseRaceBetException;
}
