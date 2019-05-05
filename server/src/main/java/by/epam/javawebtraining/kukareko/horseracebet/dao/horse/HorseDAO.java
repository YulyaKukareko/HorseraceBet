package by.epam.javawebtraining.kukareko.horseracebet.dao.horse;

import by.epam.javawebtraining.kukareko.horseracebet.dao.DAO;
import by.epam.javawebtraining.kukareko.horseracebet.model.entity.Horse;

import java.util.List;

/**
 * @author Yulya Kukareko
 * @version 1.0 11 Apr 2019
 */
public interface HorseDAO extends DAO<Horse, Long> {

    List<Horse> getJoinFirstHorseBetAndHorseStartingPrice();

    Horse getJoinHorseStartingPriceByStartingPriceId(long startingPriceId);

    List<Horse> getJoinSecondHorseBetAndHorseStartingPrice();

    List<Horse> getJoinHorseStartingPriceByRaceId(long raceId);

    List<Horse> getJoinHorseStartingPriceExcludingByRaceId(long raceId);

    List<Horse> getJoinHorseStartingPrice();
}
