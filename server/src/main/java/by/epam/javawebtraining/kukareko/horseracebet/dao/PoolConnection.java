package by.epam.javawebtraining.kukareko.horseracebet.dao;

import by.epam.javawebtraining.kukareko.horseracebet.util.ConfigurationManager;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Yulya Kukareko
 * @version 1.0 10 Apr 2019
 */
public class PoolConnection {

    private static final Logger LOGGER;
    private static final ReentrantLock LOCK;

    private static PoolConnection instance;
    private static BlockingQueue<Connection> connections;

    private ConfigurationManager configurationManager;

    static {
        LOGGER = Logger.getLogger("PoolConnectionLog");
        LOCK = new ReentrantLock();
    }

    private PoolConnection() {
        this.configurationManager = ConfigurationManager.getInstance();
        connections = new ArrayBlockingQueue<>(10);
        init();
    }

    public static PoolConnection getInstance() {
        if (instance == null) {
            LOCK.lock();
            if (instance == null) {
                instance = new PoolConnection();
            }
            LOCK.unlock();
        }
        return instance;
    }

    private void init() {
        try {
            Class.forName(configurationManager.getProperty("driver"));

            for (int i = 0; i < 10; i++) {
                Connection connection = getNewConnection();
                connections.add(connection);
            }
        } catch (ClassNotFoundException ex) {
            LOGGER.error(ex.getMessage());
        }
    }

    public final Connection getConnection() {
        Connection conn = null;
        try {
            conn = connections.poll(5, TimeUnit.SECONDS);
            if (conn == null) {
                conn = getNewConnection();
                connections.offer(conn);
            }
        } catch (InterruptedException ex) {
            LOGGER.error(ex.getMessage());
        }
        return conn;
    }

    private Connection getNewConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(configurationManager.getProperty("dbConfig.url"), configurationManager.getProperty("dbConfig.user"),
                    configurationManager.getProperty("dbConfig.password"));
        } catch (SQLException ex) {
            LOGGER.error(ex.getMessage());
        }
        return connection;
    }

    public void releaseConnection(Connection connection) {
        try {
            connections.put(connection);
        } catch (InterruptedException ex) {
            LOGGER.error(ex.getMessage());
        }
    }
}
