package by.epam.javawebtraining.kukareko.horseracebet.dao;

import java.util.List;

/**
 * @author Yulya Kukareko
 * @version 1.0 11 Apr 2019
 */
public interface DAO<T, K> {

    T getById(K id);

    List<T> getAll();

    boolean save(T entity);

    boolean update(T entity);

    boolean delete(T entity);
}
