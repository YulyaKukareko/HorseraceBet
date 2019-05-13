package by.epam.javawebtraining.kukareko.horseracebet.dao.horsesp;

import by.epam.javawebtraining.kukareko.horseracebet.dao.DAO;
import by.epam.javawebtraining.kukareko.horseracebet.model.entity.HorseStartingPrice;
import by.epam.javawebtraining.kukareko.horseracebet.model.exception.HorseRaceBetException;
import java.util.List;

/**
 * @author Yulya Kukareko
 * @version 1.0 20 Apr 2019
 */
public interface HorseStartingPriceDAO extends DAO<HorseStartingPrice, Long> {

    List<HorseStartingPrice> getJoinFirstHorsesBet() throws HorseRaceBetException;

    List<HorseStartingPrice> getJoinSecondHorsesBet() throws HorseRaceBetException;

    List<HorseStartingPrice> getByRaceId(long raceId) throws HorseRaceBetException;

    Integer getCountByRaceId(long raceId) throws HorseRaceBetException;
}
