package by.epam.javawebtraining.kukareko.horseracebet.controller;

import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;

/**
 * @author Yulya Kukareko
 * @version 1.0 02 May 2019
 */
public interface GetParams {

    Logger LOGGER = Logger.getLogger("JSONParserLog");

    default String getParam(HttpServletRequest request) {
        StringBuilder jb = new StringBuilder();
        String line;

        try {
            BufferedReader reader = request.getReader();
            while ((line = reader.readLine()) != null)
                jb.append(line);
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());
        }

        return jb.toString();
    }
}
