package by.epam.javawebtraining.kukareko.horseracebet.util;

import org.apache.log4j.Logger;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author Yulya Kukareko
 * @version 1.0 03 Apr 2019
 */
public class CryptMD5 {
    private static MessageDigest md;
    private static final Logger LOGGER;

    static {
        LOGGER = Logger.getLogger("CryptMD5Logger");
    }

    public static String cryptWithMD5(String pass) {
        try {
            md = MessageDigest.getInstance("MD5");
            byte[] passBytes = pass.getBytes();

            md.reset();

            byte[] digested = md.digest(passBytes);
            StringBuffer sb = new StringBuffer();

            for (int i = 0; i < digested.length; i++) {
                sb.append(Integer.toHexString(0xff & digested[i]));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException ex) {
            LOGGER.error(ex.getMessage());
        }
        return null;
    }
}
