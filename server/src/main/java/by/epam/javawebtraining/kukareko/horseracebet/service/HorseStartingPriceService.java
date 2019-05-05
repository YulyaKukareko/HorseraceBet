package by.epam.javawebtraining.kukareko.horseracebet.service;

import by.epam.javawebtraining.kukareko.horseracebet.dao.horsesp.HorseStartingPriceDAO;
import by.epam.javawebtraining.kukareko.horseracebet.dao.horsesp.HorseStartingPriceDAOImpl;
import by.epam.javawebtraining.kukareko.horseracebet.model.entity.HorseStartingPrice;

import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Yulya Kukareko
 * @version 1.0 21 Apr 2019
 */
public class HorseStartingPriceService {

    private HorseStartingPriceDAO spDAO;
    private static HorseStartingPriceService service;
    private static final ReentrantLock LOCK = new ReentrantLock();

    private HorseStartingPriceService() {
        spDAO = HorseStartingPriceDAOImpl.getInstance();
    }

    public static HorseStartingPriceService getInstance() {
        if (service == null) {
            LOCK.lock();
            if (service == null) {
                service = new HorseStartingPriceService();
            }
            LOCK.unlock();
        }
        return service;
    }

    public boolean save(HorseStartingPrice sp) {
        return spDAO.save(sp);
    }

    public boolean delete(HorseStartingPrice race) {
        return spDAO.delete(race);
    }

    public boolean update(HorseStartingPrice race) {
        return spDAO.update(race);
    }

    public List<HorseStartingPrice> getAll() {
        return spDAO.getAll();
    }

    public HorseStartingPrice getById(Long id) {
        return spDAO.getById(id);
    }

    public List<HorseStartingPrice> getJoinFirstHorsesBet() {
        return spDAO.getJoinFirstHorsesBet();
    }

    public List<HorseStartingPrice> getJoinSecondHorsesBet() {
        return spDAO.getJoinSecondHorsesBet();
    }

    public List<HorseStartingPrice> getByRaceId(Long raceId) {
        return spDAO.getByRaceId(raceId);
    }

    public Integer getCountByRaceId(Long raceId) {
        return spDAO.getCountByRaceId(raceId);
    }
}
