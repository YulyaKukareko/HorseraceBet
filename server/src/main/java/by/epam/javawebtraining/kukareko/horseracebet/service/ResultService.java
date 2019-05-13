package by.epam.javawebtraining.kukareko.horseracebet.service;

import static by.epam.javawebtraining.kukareko.horseracebet.util.validator.FieldValidator.*;

import by.epam.javawebtraining.kukareko.horseracebet.dao.result.ResultDAO;
import by.epam.javawebtraining.kukareko.horseracebet.dao.result.ResultDAOImpl;
import by.epam.javawebtraining.kukareko.horseracebet.model.entity.Result;
import by.epam.javawebtraining.kukareko.horseracebet.model.exception.HorseRaceBetException;
import by.epam.javawebtraining.kukareko.horseracebet.model.exception.logical.IncorrectInputParamException;

import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Yulya Kukareko
 * @version 1.0 24 Apr 2019
 */
public class ResultService {

    private static final ReentrantLock LOCK = new ReentrantLock();

    private static ResultService service;

    private ResultDAO resultDAO;

    private ResultService() {
        resultDAO = ResultDAOImpl.getInstance();
    }

    public static ResultService getInstance() {
        if (service == null) {
            LOCK.lock();
            if (service == null) {
                service = new ResultService();
            }
            LOCK.unlock();
        }
        return service;
    }

    public void save(Result result) throws HorseRaceBetException {
        validateResultObj(result);

        resultDAO.save(result);
    }

    public List<Result> getByRaceId(Long raceId) throws HorseRaceBetException {
        validateId(raceId);

        return resultDAO.getByRaceId(raceId);
    }

    private void validateResultObj(Result result) throws IncorrectInputParamException {
        validateId(result.getRaceId());
        validateId(result.getRaceId());
        validatePlace(result.getPlace());
    }
}