package by.epam.javawebtraining.kukareko.horseracebet.service;

import by.epam.javawebtraining.kukareko.horseracebet.dao.horse.HorseDAO;
import by.epam.javawebtraining.kukareko.horseracebet.dao.horse.HorseDAOImpl;
import by.epam.javawebtraining.kukareko.horseracebet.model.entity.Horse;

import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Yulya Kukareko
 * @version 1.0 21 Apr 2019
 */
public class HorseService {

    private HorseDAO horseDAO;
    private static HorseService service;
    private static final ReentrantLock LOCK = new ReentrantLock();

    private HorseService() {
        horseDAO = HorseDAOImpl.getInstance();
    }

    public static HorseService getInstance() {
        if (service == null) {
            LOCK.lock();
            if (service == null) {
                service = new HorseService();
            }
            LOCK.unlock();
        }
        return service;
    }

    public boolean save(Horse horse) {
        return horseDAO.save(horse);
    }

    public boolean delete(Horse horse) {
        return horseDAO.delete(horse);
    }

    public boolean update(Horse horse) {
        return horseDAO.update(horse);
    }

    public List<Horse> getAll() {
        return horseDAO.getAll();
    }

    public Horse getById(Long id) {
        return horseDAO.getById(id);
    }

    public List<Horse> getJoinFirstBetAndHorseStartingPrice() {
        return horseDAO.getJoinFirstHorseBetAndHorseStartingPrice();
    }

    public List<Horse> getJoinSecondBetAndHorseStartingPrice() {
        return horseDAO.getJoinSecondHorseBetAndHorseStartingPrice();
    }

    public List<Horse> getJoinHorseStartingPriceByRaceId(Long raceId) {
        return horseDAO.getJoinHorseStartingPriceByRaceId(raceId);
    }

    public List<Horse> getJoinHorseStartingPriceExcludingByRaceId(Long raceId) {
        return horseDAO.getJoinHorseStartingPriceExcludingByRaceId(raceId);
    }

    public List<Horse> getJoinHorseStartingPrice() {
        return horseDAO.getJoinHorseStartingPrice();
    }

    public Horse getJoinHorseStartingPriceByStartingPriceId(Long startingPriceId) {
        return horseDAO.getJoinHorseStartingPriceByStartingPriceId(startingPriceId);
    }
}
