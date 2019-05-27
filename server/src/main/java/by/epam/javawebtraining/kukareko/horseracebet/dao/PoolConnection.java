package by.epam.javawebtraining.kukareko.horseracebet.dao;

import static by.epam.javawebtraining.kukareko.horseracebet.util.constant.DatabaseConstant.*;

import by.epam.javawebtraining.kukareko.horseracebet.util.ConfigurationManager;
import by.epam.javawebtraining.kukareko.horseracebet.util.constant.LogConstant;
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

    private String driverDB;
    private String urlDB;
    private String userDB;
    private String passwordDB;
    private int poolConnectionSize;
    private int connectionTimeout;

    private ConfigurationManager configurationManager;

    static {
        LOGGER = Logger.getLogger(LogConstant.POOL_CONNECTION_LOG);
        LOCK = new ReentrantLock();
    }

    private PoolConnection() {
        this.configurationManager = ConfigurationManager.getInstance();
        this.connectionTimeout = Integer.parseInt(configurationManager.getProperty(CONNECT_POOL_TIMEOUT));
        this.poolConnectionSize = Integer.parseInt(configurationManager.getProperty(DB_POOL_CONNECTION_SIZE));
        this.connections = new ArrayBlockingQueue<>(poolConnectionSize);
        this.driverDB = configurationManager.getProperty(DRIVER);
        this.urlDB = configurationManager.getProperty(DB_CONFIG_URL);
        this.userDB = configurationManager.getProperty(DB_CONFIG_USER);
        this.passwordDB = configurationManager.getProperty(DB_CONFIG_PASSWORD);
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
            Class.forName(driverDB);

            for (int i = 0; i < poolConnectionSize; i++) {
                Connection connection = getNewConnection();
                connections.add(connection);
            }
        } catch (ClassNotFoundException ex) {
            LOGGER.error(ex.getMessage());
        }
    }

    /**
     * @return new Connection
     */
    public final Connection getConnection() {
        Connection conn = null;
        try {
            conn = connections.poll(connectionTimeout, TimeUnit.SECONDS);
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
            connection = DriverManager.getConnection(urlDB, userDB, passwordDB);
        } catch (SQLException ex) {
            LOGGER.error(ex.getMessage());
        }
        return connection;
    }

    /**
     * Return connection to pool
     *
     * @param connection
     */
    public void releaseConnection(Connection connection) {
        try {
            connections.put(connection);
        } catch (InterruptedException ex) {
            LOGGER.error(ex.getMessage());
        }
    }
}
