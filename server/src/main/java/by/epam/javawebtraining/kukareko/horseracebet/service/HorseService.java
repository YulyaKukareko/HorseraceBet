package by.epam.javawebtraining.kukareko.horseracebet.service;

import static by.epam.javawebtraining.kukareko.horseracebet.util.validator.FieldValidator.*;

import by.epam.javawebtraining.kukareko.horseracebet.dao.horse.HorseDAO;
import by.epam.javawebtraining.kukareko.horseracebet.dao.horse.HorseDAOImpl;
import by.epam.javawebtraining.kukareko.horseracebet.model.entity.Horse;
import by.epam.javawebtraining.kukareko.horseracebet.model.exception.HorseRaceBetException;
import by.epam.javawebtraining.kukareko.horseracebet.model.exception.logical.IncorrectInputParamException;

import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Yulya Kukareko
 * @version 1.0 21 Apr 2019
 */
public class HorseService {

    private static final ReentrantLock LOCK = new ReentrantLock();

    private static HorseService service;

    private HorseDAO horseDAO;

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

    public void save(Horse horse) throws HorseRaceBetException {
        validateHorseObject(horse);

        horseDAO.save(horse);
    }

    public void delete(Horse horse) throws HorseRaceBetException {
        validateId(horse.getId());

        horseDAO.delete(horse);
    }

    public void update(Horse horse) throws HorseRaceBetException {
        validateHorseObject(horse);
        validateId(horse.getId());

        horseDAO.update(horse);
    }

    public List<Horse> getAll() throws HorseRaceBetException {
        return horseDAO.getAll();
    }

    public Horse getById(Long id) throws HorseRaceBetException {
        validateId(id);

        return horseDAO.getById(id);
    }

    public List<Horse> getJoinFirstBetAndHorseStartingPrice() throws HorseRaceBetException {
        return horseDAO.getJoinFirstHorseBetAndHorseStartingPrice();
    }

    public List<Horse> getJoinSecondBetAndHorseStartingPrice() throws HorseRaceBetException {
        return horseDAO.getJoinSecondHorseBetAndHorseStartingPrice();
    }

    public List<Horse> getJoinHorseStartingPriceByRaceId(Long raceId) throws HorseRaceBetException {
        validateId(raceId);

        return horseDAO.getJoinHorseStartingPriceByRaceId(raceId);
    }

    public List<Horse> getJoinHorseStartingPriceExcludingByRaceId(Long raceId) throws HorseRaceBetException {
        validateId(raceId);

        return horseDAO.getJoinHorseStartingPriceExcludingByRaceId(raceId);
    }

    public List<Horse> getJoinHorseStartingPrice() throws HorseRaceBetException {
        return horseDAO.getJoinHorseStartingPrice();
    }

    public Horse getJoinHorseStartingPriceByStartingPriceId(Long startingPriceId) throws HorseRaceBetException {
        validateId(startingPriceId);

        return horseDAO.getJoinHorseStartingPriceByStartingPriceId(startingPriceId);
    }

    private void validateHorseObject(Horse horse) throws IncorrectInputParamException {
        validateName(horse.getName());
        validateName(horse.getTrainer());
        validateName(horse.getJockey());
        validateWeightOrDistance(horse.getWeight());
    }
}
