package by.epam.javawebtraining.kukareko.horseracebet.util;


import by.epam.javawebtraining.kukareko.horseracebet.util.constant.GeneralConstants;

import java.util.ResourceBundle;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Yulya Kukareko
 * @version 1.0 11 Apr 2019
 */
public class ConfigurationManager {

    private static ConfigurationManager instance;
    private static final ReentrantLock LOCK = new ReentrantLock();

    private ResourceBundle resourceBundle;

    private ConfigurationManager() {
    }

    public static ConfigurationManager getInstance() {
        if (instance == null) {
            LOCK.lock();
            if (instance == null) {
                instance = new ConfigurationManager();
                instance.resourceBundle = ResourceBundle.getBundle(GeneralConstants.CONFIG);
            }
            LOCK.unlock();
        }
        return instance;
    }

    public String getProperty(String key) {
        return (String) resourceBundle.getObject(key);
    }
}