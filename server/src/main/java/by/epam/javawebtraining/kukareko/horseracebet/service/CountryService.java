package by.epam.javawebtraining.kukareko.horseracebet.service;

import static by.epam.javawebtraining.kukareko.horseracebet.util.validator.FieldValidator.*;

import by.epam.javawebtraining.kukareko.horseracebet.dao.country.CountryDAO;
import by.epam.javawebtraining.kukareko.horseracebet.dao.country.CountryDAOImpl;
import by.epam.javawebtraining.kukareko.horseracebet.model.entity.Country;
import by.epam.javawebtraining.kukareko.horseracebet.model.exception.HorseRaceBetException;

import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Yulya Kukareko
 * @version 1.0 07 May 2019
 */
public class CountryService {

    private static final ReentrantLock LOCK = new ReentrantLock();

    private static CountryService service;

    private CountryDAO countryDAO;

    private CountryService() {
        countryDAO = CountryDAOImpl.getInstance();
    }

    public static CountryService getInstance() {
        if (service == null) {
            LOCK.lock();
            if (service == null) {
                service = new CountryService();
            }
            LOCK.unlock();
        }
        return service;
    }

    /**
     * @return all countries
     * @throws HorseRaceBetException
     */
    public List<Country> getAll() throws HorseRaceBetException {
        return countryDAO.getAll();
    }

    /**
     *
     * @param id
     * @return country by id
     * @throws HorseRaceBetException
     */
    public Country getById(Long id) throws HorseRaceBetException {
        validateId(id);

        return countryDAO.getById(id);
    }
}