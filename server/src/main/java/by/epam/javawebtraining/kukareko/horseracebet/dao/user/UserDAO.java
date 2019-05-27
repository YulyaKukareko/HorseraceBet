package by.epam.javawebtraining.kukareko.horseracebet.dao.user;

import by.epam.javawebtraining.kukareko.horseracebet.dao.DAO;
import by.epam.javawebtraining.kukareko.horseracebet.model.entity.User;
import by.epam.javawebtraining.kukareko.horseracebet.model.exception.HorseRaceBetException;

import java.math.BigDecimal;

/**
 * @author Yulya Kukareko
 * @version 1.0 13 Apr 2019
 */
public interface UserDAO extends DAO<User, Long> {

    /**
     * @param login
     * @param password
     * @return user by login and password
     * @throws HorseRaceBetException
     */
    User getByLoginAndPassword(String login, String password) throws HorseRaceBetException;

    /**
     * @param email
     * @return whether there is a such email in database
     * @throws HorseRaceBetException
     */
    boolean checkExistsEmail(String email) throws HorseRaceBetException;

    /**
     * @param id
     * @param money
     * @throws HorseRaceBetException
     */
    void updateBalance(Long id, BigDecimal money) throws HorseRaceBetException;
}
