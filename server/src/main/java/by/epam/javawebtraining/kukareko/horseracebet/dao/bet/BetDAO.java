package by.epam.javawebtraining.kukareko.horseracebet.dao.bet;

import by.epam.javawebtraining.kukareko.horseracebet.dao.DAO;
import by.epam.javawebtraining.kukareko.horseracebet.model.entity.Bet;
import by.epam.javawebtraining.kukareko.horseracebet.model.entity.BetType;
import by.epam.javawebtraining.kukareko.horseracebet.model.exception.HorseRaceBetException;

import java.util.List;

/**
 * @author Yulya Kukareko
 * @version 1.0 21 Apr 2019
 */
public interface BetDAO extends DAO<Bet, Long> {

    /**
     * @param raceId
     * @param type
     * @param userId
     * @return bets by certain race and bet type
     * @throws HorseRaceBetException
     */
    List<Bet> getByRaceIdAndBetType(long raceId, BetType type, long userId) throws HorseRaceBetException;
}
