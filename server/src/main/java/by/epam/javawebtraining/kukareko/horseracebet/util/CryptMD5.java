package by.epam.javawebtraining.kukareko.horseracebet.util;

import org.apache.log4j.Logger;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author Yulya Kukareko
 * @version 1.0 03 Apr 2019
 */
public class CryptMD5 {

    private static final Logger LOGGER;
    private static ConfigurationManager configurationManager;

    static {
        LOGGER = Logger.getLogger("CryptMD5Log");
        configurationManager = ConfigurationManager.getInstance();
    }

    public static String cryptWithMD5(String pass) {
        StringBuilder sb = new StringBuilder();

        try {
            MessageDigest md;

            String cryptoAlgorithm = configurationManager.getProperty("cryptoAlgorithm");
            md = MessageDigest.getInstance(cryptoAlgorithm);
            byte[] passBytes = pass.getBytes();

            md.reset();

            byte[] digested = md.digest(passBytes);

            for (byte item : digested) {
                sb.append(Integer.toHexString(0xff & item));
            }
        } catch (NoSuchAlgorithmException ex) {
            LOGGER.error(ex.getMessage());
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        System.out.println(cryptWithMD5("Hello"));
    }
}
