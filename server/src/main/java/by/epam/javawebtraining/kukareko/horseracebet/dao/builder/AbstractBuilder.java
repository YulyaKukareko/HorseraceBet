package by.epam.javawebtraining.kukareko.horseracebet.dao.builder;

import org.apache.log4j.Logger;

import java.sql.ResultSet;

/**
 * @author Yulya Kukareko
 * @version 1.0 14 Apr 2019
 */
public abstract class AbstractBuilder<T> {

    protected static final Logger LOGGER;

    static {
        LOGGER = Logger.getLogger("BuilderLog");
    }

    public abstract T getEntity(ResultSet rs);
}
