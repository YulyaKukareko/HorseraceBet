package by.epam.javawebtraining.kukareko.horseracebet.dao.userbet;

import by.epam.javawebtraining.kukareko.horseracebet.dao.DAO;
import by.epam.javawebtraining.kukareko.horseracebet.model.entity.UserBet;
import by.epam.javawebtraining.kukareko.horseracebet.model.exception.HorseRaceBetException;

import java.util.List;

/**
 * @author Yulya Kukareko
 * @version 1.0 28 Apr 2019
 */
public interface UserBetDAO extends DAO<UserBet, Long> {
    List<UserBet> getByUserId(long userId) throws HorseRaceBetException;
}
