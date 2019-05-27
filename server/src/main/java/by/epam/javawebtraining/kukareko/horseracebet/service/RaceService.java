package by.epam.javawebtraining.kukareko.horseracebet.service;

import static by.epam.javawebtraining.kukareko.horseracebet.util.validator.FieldValidator.*;

import by.epam.javawebtraining.kukareko.horseracebet.dao.race.RaceDAO;
import by.epam.javawebtraining.kukareko.horseracebet.dao.race.RaceDAOImpl;
import by.epam.javawebtraining.kukareko.horseracebet.model.entity.Race;
import by.epam.javawebtraining.kukareko.horseracebet.model.exception.HorseRaceBetException;
import by.epam.javawebtraining.kukareko.horseracebet.model.exception.logical.IncorrectInputParamException;

import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Yulya Kukareko
 * @version 1.0 19 Apr 2019
 */
public class RaceService {

    private static final ReentrantLock LOCK = new ReentrantLock();

    private static RaceService service;

    private RaceDAO raceDAO;

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

    /**
     * @param race
     * @throws HorseRaceBetException
     */
    public void save(Race race) throws HorseRaceBetException {
        validateRaceObject(race);

        raceDAO.save(race);
    }

    /**
     * @param race
     * @throws HorseRaceBetException
     */
    public void delete(Race race) throws HorseRaceBetException {
        validateId(race.getId());

        raceDAO.delete(race);
    }

    /**
     * @param race
     * @throws HorseRaceBetException
     */
    public void update(Race race) throws HorseRaceBetException {
        validateRaceObject(race);

        raceDAO.update(race);
    }

    /**
     * @return all races
     * @throws HorseRaceBetException
     */
    public List<Race> getAll() throws HorseRaceBetException {
        return raceDAO.getAll();
    }

    /**
     * @param id
     * @return race by id
     * @throws HorseRaceBetException
     */
    public Race getById(Long id) throws HorseRaceBetException {
        validateId(id);

        return raceDAO.getById(id);
    }

    /**
     * @return races bets
     * @throws HorseRaceBetException
     */
    public List<Race> getJoinBet() throws HorseRaceBetException {
        return raceDAO.getJoinBet();
    }

    /**
     * @return races of horse starting prices
     * @throws HorseRaceBetException
     */
    public List<Race> getJoinHorseStarting() throws HorseRaceBetException {
        return raceDAO.getJoinHorseStarting();
    }

    /**
     * @param raceId
     * @return races of horse starting prices by race id
     * @throws HorseRaceBetException
     */
    public List<Race> getJoinHorseStartingPriceById(Long raceId) throws HorseRaceBetException {
        validateId(raceId);

        return raceDAO.getJoinHorseStartingPriceById(raceId);
    }

    /**
     * @return races which have results
     * @throws HorseRaceBetException
     */
    public List<Race> getNotJoinResult() throws HorseRaceBetException {
        return raceDAO.getNotJoinResult();
    }

    /**
     * @return completed races which haven't results
     * @throws HorseRaceBetException
     */
    public List<Race> getCompletedRacesNotJoinResult() throws HorseRaceBetException {
        return raceDAO.getCompletedRacesNotJoinResult();
    }

    /**
     * @return completed races which have results
     * @throws HorseRaceBetException
     */
    public List<Race> getCompletedRacesJoinResult() throws HorseRaceBetException {
        return raceDAO.getCompletedRacesJoinResult();
    }

    private void validateRaceObject(Race race) throws IncorrectInputParamException {
        validateName(race.getName());
        validateId(race.getCountryId());
        validateWeightOrDistance((float) race.getDistance());
        validateMoney(race.getPurse());
        validateEnum(race.getType());
        validateRaceTime(race.getTime());
    }
}