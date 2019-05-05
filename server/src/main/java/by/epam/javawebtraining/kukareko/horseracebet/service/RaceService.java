package by.epam.javawebtraining.kukareko.horseracebet.service;

import by.epam.javawebtraining.kukareko.horseracebet.dao.race.RaceDAO;
import by.epam.javawebtraining.kukareko.horseracebet.dao.race.RaceDAOImpl;
import by.epam.javawebtraining.kukareko.horseracebet.model.entity.Race;

import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Yulya Kukareko
 * @version 1.0 19 Apr 2019
 */
public class RaceService {

    private RaceDAO raceDAO;
    private static RaceService service;
    private static final ReentrantLock LOCK = new ReentrantLock();

    private RaceService() {
        raceDAO = RaceDAOImpl.getInstance();
    }

    public static RaceService getInstance() {
        if (service == null) {
            LOCK.lock();
            if (service == null) {
                service = new RaceService();
            }
            LOCK.unlock();
        }
        return service;
    }

    public boolean save(Race race) {
        return raceDAO.save(race);
    }

    public boolean delete(Race race) {
        return raceDAO.delete(race);
    }

    public boolean update(Race race) {
        return raceDAO.update(race);
    }

    public List<Race> getAll() {
        return raceDAO.getAll();
    }

    public Race getById(Long id) {
        return raceDAO.getById(id);
    }

    public List<Race> getJoinBet() {
        return raceDAO.getJoinBet();
    }

    public List<Race> getJoinHorseStarting() {
        return raceDAO.getJoinHorseStarting();
    }

    public List<Race> getJoinHorseStartingPriceById(Long raceId) {
        return raceDAO.getJoinHorseStartingPriceById(raceId);
    }

    public List<Race> getNotJoinResult() {
        return raceDAO.getNotJoinResult();
    }

    public List<Race> getCompletedRacesNotJoinResult() {
        return raceDAO.getCompletedRacesNotJoinResult();
    }

    public List<Race> getCompletedRacesJoinResult() {
        return raceDAO.getCompletedRacesJoinResult();
    }
}
