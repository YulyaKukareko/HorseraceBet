package by.epam.javawebtraining.kukareko.horseracebet.util;


import java.util.ResourceBundle;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Yulya Kukareko
 * @version 1.0 11 Apr 2019
 */
public class ConfigurationManager {

    private static final String BUNDLE_NAME = "config";
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
                instance.resourceBundle = ResourceBundle.getBundle(BUNDLE_NAME);
            }
            LOCK.unlock();
        }
        return instance;
    }

    public String getProperty(String key) {
        return (String) resourceBundle.getObject(key);
    }
}