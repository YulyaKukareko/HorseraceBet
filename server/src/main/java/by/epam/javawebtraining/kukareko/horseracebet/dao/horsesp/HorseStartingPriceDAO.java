package by.epam.javawebtraining.kukareko.horseracebet.dao.horsesp;

import by.epam.javawebtraining.kukareko.horseracebet.dao.DAO;
import by.epam.javawebtraining.kukareko.horseracebet.model.entity.HorseStartingPrice;

import java.util.List;

/**
 * @author Yulya Kukareko
 * @version 1.0 20 Apr 2019
 */
public interface HorseStartingPriceDAO extends DAO<HorseStartingPrice, Long> {

    List<HorseStartingPrice> getJoinFirstHorsesBet();

    List<HorseStartingPrice> getJoinSecondHorsesBet();

    List<HorseStartingPrice> getByRaceId(long raceId);

    Integer getCountByRaceId(long raceId);
}
