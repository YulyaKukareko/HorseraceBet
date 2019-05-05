package by.epam.javawebtraining.kukareko.horseracebet.service;

import by.epam.javawebtraining.kukareko.horseracebet.dao.result.ResultDAO;
import by.epam.javawebtraining.kukareko.horseracebet.dao.result.ResultDAOImpl;
import by.epam.javawebtraining.kukareko.horseracebet.model.entity.Result;

import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Yulya Kukareko
 * @version 1.0 24 Apr 2019
 */
public class ResultService {

    private ResultDAO resultDAO;
    private static ResultService service;
    private static final ReentrantLock LOCK = new ReentrantLock();

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

    public boolean save(Result result) {
        return resultDAO.save(result);
    }

    public List<Result> getByRaceId(Long raceId){
        return resultDAO.getByRaceId(raceId);
    }
}
