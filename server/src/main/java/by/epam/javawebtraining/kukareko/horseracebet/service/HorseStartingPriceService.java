package by.epam.javawebtraining.kukareko.horseracebet.service;

import static by.epam.javawebtraining.kukareko.horseracebet.util.validator.FieldValidator.*;

import by.epam.javawebtraining.kukareko.horseracebet.dao.horsesp.HorseStartingPriceDAO;
import by.epam.javawebtraining.kukareko.horseracebet.dao.horsesp.HorseStartingPriceDAOImpl;
import by.epam.javawebtraining.kukareko.horseracebet.model.entity.HorseStartingPrice;
import by.epam.javawebtraining.kukareko.horseracebet.model.exception.HorseRaceBetException;
import by.epam.javawebtraining.kukareko.horseracebet.model.exception.logical.IncorrectInputParamException;

import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Yulya Kukareko
 * @version 1.0 21 Apr 2019
 */
public class HorseStartingPriceService {

    private static final ReentrantLock LOCK = new ReentrantLock();

    private static HorseStartingPriceService service;

    private HorseStartingPriceDAO spDAO;

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

    /**
     * @param sp
     * @throws HorseRaceBetException
     */
    public void save(HorseStartingPrice sp) throws HorseRaceBetException {
        validateHorseStartingPriceObject(sp);

        spDAO.save(sp);
    }

    /**
     * @param sp
     * @throws HorseRaceBetException
     */
    public void delete(HorseStartingPrice sp) throws HorseRaceBetException {
        validateId(sp.getId());

        spDAO.delete(sp);
    }

    /**
     * @param sp
     * @throws HorseRaceBetException
     */
    public void update(HorseStartingPrice sp) throws HorseRaceBetException {
        validateHorseStartingPriceObject(sp);
        validateId(sp.getId());

        spDAO.update(sp);
    }

    /**
     * @return all horse starting prices
     * @throws HorseRaceBetException
     */
    public List<HorseStartingPrice> getAll() throws HorseRaceBetException {
        return spDAO.getAll();
    }

    /**
     * @param id
     * @return horse starting price by id
     * @throws HorseRaceBetException
     */
    public HorseStartingPrice getById(Long id) throws HorseRaceBetException {
        validateId(id);

        return spDAO.getById(id);
    }

    /**
     * @return horse starting prices of first horses bet
     * @throws HorseRaceBetException
     */
    public List<HorseStartingPrice> getJoinFirstHorsesBet() throws HorseRaceBetException {
        return spDAO.getJoinFirstHorsesBet();
    }

    /**
     * @return horse starting prices of second horses bet
     * @throws HorseRaceBetException
     */
    public List<HorseStartingPrice> getJoinSecondHorsesBet() throws HorseRaceBetException {
        return spDAO.getJoinSecondHorsesBet();
    }

    /**
     * @param raceId
     * @return horse starting prices by race id
     * @throws HorseRaceBetException
     */
    public List<HorseStartingPrice> getByRaceId(Long raceId) throws HorseRaceBetException {
        return spDAO.getByRaceId(raceId);
    }

    /**
     * @param raceId
     * @return count horses participating in race
     * @throws HorseRaceBetException
     */
    public Integer getCountByRaceId(Long raceId) throws HorseRaceBetException {
        return spDAO.getCountByRaceId(raceId);
    }

    private void validateHorseStartingPriceObject(HorseStartingPrice sp) throws IncorrectInputParamException {
        validateId(sp.getRaceId());
        validateId(sp.getHorseId());
        validateCoefficient(sp.getSp());
    }
}