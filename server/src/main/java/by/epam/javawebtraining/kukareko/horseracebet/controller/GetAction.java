package by.epam.javawebtraining.kukareko.horseracebet.controller;

import by.epam.javawebtraining.kukareko.horseracebet.util.ConfigurationManager;
import by.epam.javawebtraining.kukareko.horseracebet.util.constant.RegexpConstant;

import javax.servlet.http.HttpServletRequest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Yulya Kukareko
 * @version 1.0 02 May 2019
 */
public interface GetAction {

    /**
     * @param request
     * @return particular action
     */
    default String getAction(HttpServletRequest request) {
        ConfigurationManager configurationManager = ConfigurationManager.getInstance();
        String defineActionRegExp = configurationManager.getProperty(RegexpConstant.DEFINE_ACTION_REG_EXP);

        Pattern pattern = Pattern.compile(defineActionRegExp);
        Matcher matcher = pattern.matcher(request.getRequestURI());
        if (matcher.find()) {
            return matcher.group(0);
        }
        return null;
    }
}
