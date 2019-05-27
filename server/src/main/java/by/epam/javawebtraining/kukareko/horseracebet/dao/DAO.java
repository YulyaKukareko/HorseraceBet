package by.epam.javawebtraining.kukareko.horseracebet.dao;

import by.epam.javawebtraining.kukareko.horseracebet.model.exception.HorseRaceBetException;

import java.util.List;

/**
 * @author Yulya Kukareko
 * @version 1.0 11 Apr 2019
 */
public interface DAO<T, K> {

    /**
     * @param id
     * @return
     * @throws HorseRaceBetException
     */
    T getById(K id) throws HorseRaceBetException;

    /**
     * @return
     * @throws HorseRaceBetException
     */
    List<T> getAll() throws HorseRaceBetException;

    /**
     * @param entity
     * @throws HorseRaceBetException
     */
    void save(T entity) throws HorseRaceBetException;

    /**
     * @param entity
     * @throws HorseRaceBetException
     */
    void update(T entity) throws HorseRaceBetException;

    /**
     * @param entity
     * @throws HorseRaceBetException
     */
    void delete(T entity) throws HorseRaceBetException;
}