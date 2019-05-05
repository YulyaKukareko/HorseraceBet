package by.epam.javawebtraining.kukareko.horseracebet.controller;

import javax.servlet.http.HttpServletRequest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Yulya Kukareko
 * @version 1.0 02 May 2019
 */
public interface GetAction {

    default String getAction(HttpServletRequest request){
        Pattern pattern = Pattern.compile("([^/]+)$");
        Matcher matcher = pattern.matcher(request.getRequestURI());
        if (matcher.find()) {
            return matcher.group(0);
        }
        return null;
    }
}
