package by.epam.javawebtraining.kukareko.horseracebet.controller.handler;

import by.epam.javawebtraining.kukareko.horseracebet.controller.GetAction;
import by.epam.javawebtraining.kukareko.horseracebet.controller.GetParams;
import by.epam.javawebtraining.kukareko.horseracebet.model.exception.HorseRaceBetException;
import by.epam.javawebtraining.kukareko.horseracebet.service.UserBetService;
import by.epam.javawebtraining.kukareko.horseracebet.service.UserService;
import by.epam.javawebtraining.kukareko.horseracebet.model.entity.UserBet;
import by.epam.javawebtraining.kukareko.horseracebet.util.ConfigurationManager;
import com.google.gson.Gson;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author Yulya Kukareko
 * @version 1.0 28 Apr 2019
 */
public class UserBetCommand implements Command, GetAction, GetParams {

    private static ConfigurationManager configurationManager;

    private UserBetService service;

    public UserBetCommand() {
        service = UserBetService.getInstance();
        configurationManager = ConfigurationManager.getInstance();
    }

    @Override
    public JSONObject execute(HttpServletRequest request) throws HorseRaceBetException {
        String requestParamId = configurationManager.getProperty("params.id");

        JSONObject result = new JSONObject();
        UserBet userBet;

        switch (getAction(request)) {
            case "create":
                userBet = new Gson().fromJson(getParam(request), UserBet.class);

                HttpSession session = request.getSession();
                long userId = (long) session.getAttribute(requestParamId);
                userBet.setUserId(userId);

                service.save(userBet);
                break;
            case "getByUserId":
                String responseParamResult = configurationManager.getProperty("configJSON.result");
                String responseParamNoResult = configurationManager.getProperty("responseParam.noResult");

                session = request.getSession();
                userId = (long) session.getAttribute(requestParamId);
                List<UserBet> userBets = service.getByUserId(userId);

                if (userBets != null) {
                    result.put(responseParamResult, new JSONArray(userBets));
                } else {
                    result.put(responseParamResult, responseParamNoResult);
                }
                break;
        }

        return result;
    }
}
