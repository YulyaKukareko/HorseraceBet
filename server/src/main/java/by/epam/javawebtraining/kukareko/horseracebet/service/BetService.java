package by.epam.javawebtraining.kukareko.horseracebet.service;

import static by.epam.javawebtraining.kukareko.horseracebet.util.validator.FieldValidator.*;

import by.epam.javawebtraining.kukareko.horseracebet.dao.bet.BetDAO;
import by.epam.javawebtraining.kukareko.horseracebet.dao.bet.BetDAOImpl;
import by.epam.javawebtraining.kukareko.horseracebet.model.entity.Bet;
import by.epam.javawebtraining.kukareko.horseracebet.model.entity.BetType;
import by.epam.javawebtraining.kukareko.horseracebet.model.exception.HorseRaceBetException;
import by.epam.javawebtraining.kukareko.horseracebet.model.exception.logical.IncorrectInputParamException;

import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Yulya Kukareko
 * @version 1.0 21 Apr 2019
 */
public class BetService {

    private static final ReentrantLock LOCK = new ReentrantLock();

    private static BetService service;

    private BetDAO betDAO;

    private BetService() {
        betDAO = BetDAOImpl.getInstance();
    }

    public static BetService getInstance() {
        if (service == null) {
            LOCK.lock();
            if (service == null) {
                service = new BetService();
            }
            LOCK.unlock();
        }
        return service;
    }

    /**
     * @param bet
     * @throws HorseRaceBetException
     */
    public void save(Bet bet) throws HorseRaceBetException {
        validateBetObject(bet);

        betDAO.save(bet);
    }

    /**
     * @param bet
     * @throws HorseRaceBetException
     */
    public void delete(Bet bet) throws HorseRaceBetException {
        validateId(bet.getId());

        betDAO.delete(bet);
    }

    /**
     * @param bet
     * @throws HorseRaceBetException
     */
    public void update(Bet bet) throws HorseRaceBetException {
        validateBetObject(bet);
        validateId(bet.getId());

        betDAO.update(bet);
    }

    /**
     * @return
     * @throws HorseRaceBetException
     */
    public List<Bet> getAll() throws HorseRaceBetException {
        return betDAO.getAll();
    }

    /**
     * @param raceId
     * @param type
     * @param userId
     * @return
     * @throws HorseRaceBetException
     */
    public List<Bet> getByRaceIdAndBetType(long raceId, BetType type, Long userId) throws HorseRaceBetException {
        validateId(raceId);
        validateId(userId);

        return betDAO.getByRaceIdAndBetType(raceId, type, userId);
    }

    /**
     * @param id
     * @return bet by id
     * @throws HorseRaceBetException
     */
    public Bet getById(Long id) throws HorseRaceBetException {
        validateId(id);

        return betDAO.getById(id);
    }

    private void validateBetObject(Bet bet) throws IncorrectInputParamException {
        validateId(bet.getFirstStartingPriceHorseId());
        validateCoefficient(bet.getCoefficient());
        validateEnum(bet.getType());
        validateSecondHorseId(bet.getSecondStartingPriceHorseId());
    }
}