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

    public void save(Race race) throws HorseRaceBetException {
        validateRaceObject(race);

        raceDAO.save(race);
    }

    public void delete(Race race) throws HorseRaceBetException {
        validateId(race.getId());

        raceDAO.delete(race);
    }

    public void update(Race race) throws HorseRaceBetException {
        validateRaceObject(race);

        raceDAO.update(race);
    }

    public List<Race> getAll() throws HorseRaceBetException {
        return raceDAO.getAll();
    }

    public Race getById(Long id) throws HorseRaceBetException {
        validateId(id);

        return raceDAO.getById(id);
    }

    public List<Race> getJoinBet() throws HorseRaceBetException {
        return raceDAO.getJoinBet();
    }

    public List<Race> getJoinHorseStarting() throws HorseRaceBetException {
        return raceDAO.getJoinHorseStarting();
    }

    public List<Race> getJoinHorseStartingPriceById(Long raceId) throws HorseRaceBetException {
        validateId(raceId);

        return raceDAO.getJoinHorseStartingPriceById(raceId);
    }

    public List<Race> getNotJoinResult() throws HorseRaceBetException {
        return raceDAO.getNotJoinResult();
    }

    public List<Race> getCompletedRacesNotJoinResult() throws HorseRaceBetException {
        return raceDAO.getCompletedRacesNotJoinResult();
    }

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