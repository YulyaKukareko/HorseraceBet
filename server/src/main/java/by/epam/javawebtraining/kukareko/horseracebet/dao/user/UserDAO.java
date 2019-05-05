package by.epam.javawebtraining.kukareko.horseracebet.dao.user;

import by.epam.javawebtraining.kukareko.horseracebet.dao.DAO;
import by.epam.javawebtraining.kukareko.horseracebet.model.entity.User;

import java.math.BigDecimal;

/**
 * @author Yulya Kukareko
 * @version 1.0 13 Apr 2019
 */
public interface UserDAO extends DAO<User, Long> {

    User getByLoginAndPassword(String login, String password);

    boolean makeBet(long id, BigDecimal betMoney);
}
