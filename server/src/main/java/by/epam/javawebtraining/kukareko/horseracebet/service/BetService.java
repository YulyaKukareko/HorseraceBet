package by.epam.javawebtraining.kukareko.horseracebet.service;

import by.epam.javawebtraining.kukareko.horseracebet.dao.bet.BetDAO;
import by.epam.javawebtraining.kukareko.horseracebet.dao.bet.BetDAOImpl;
import by.epam.javawebtraining.kukareko.horseracebet.model.entity.Bet;
import by.epam.javawebtraining.kukareko.horseracebet.model.entity.BetType;

import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Yulya Kukareko
 * @version 1.0 21 Apr 2019
 */
public class BetService {

    private BetDAO betDAO;
    private static BetService service;
    private static final ReentrantLock LOCK = new ReentrantLock();

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

    public boolean save(Bet bet) {
        return betDAO.save(bet);
    }

    public boolean delete(Bet bet) {
        return betDAO.delete(bet);
    }

    public boolean update(Bet bet) {
        return betDAO.update(bet);
    }

    public List<Bet> getAll() {
        return betDAO.getAll();
    }

    public List<Bet> getByRaceIdAndBetType(long raceId, BetType type, Long userId) {
        return betDAO.getByRaceIdAndBetType(raceId, type, userId);
    }

    public Bet getById(Long id) {
        return betDAO.getById(id);
    }
}
