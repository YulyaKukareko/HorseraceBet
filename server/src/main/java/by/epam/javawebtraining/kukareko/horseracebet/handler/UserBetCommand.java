package by.epam.javawebtraining.kukareko.horseracebet.handler;

import static by.epam.javawebtraining.kukareko.horseracebet.util.constant.ActionConstant.*;
import static by.epam.javawebtraining.kukareko.horseracebet.util.constant.JSONParamConstant.*;

import by.epam.javawebtraining.kukareko.horseracebet.controller.GetAction;
import by.epam.javawebtraining.kukareko.horseracebet.controller.GetParams;
import by.epam.javawebtraining.kukareko.horseracebet.model.exception.HorseRaceBetException;
import by.epam.javawebtraining.kukareko.horseracebet.service.UserBetService;
import by.epam.javawebtraining.kukareko.horseracebet.model.entity.UserBet;
import by.epam.javawebtraining.kukareko.horseracebet.util.ConfigurationManager;
import com.google.gson.Gson;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author Yulya Kukareko
 * @version 1.0 28 Apr 2019
 */
public class UserBetCommand implements Command, GetAction, GetParams {

    private ConfigurationManager configurationManager;

    private String requestParamId;
    private String responseParamResult;
    private String responseParamNoResult;

    private UserBetService service;

    public UserBetCommand() {
        this.service = UserBetService.getInstance();
        this.configurationManager = ConfigurationManager.getInstance();
        this.requestParamId = configurationManager.getProperty(PARAMS_ID);
        this.responseParamResult = configurationManager.getProperty(CONFIG_JSON_RESULT);
        this.responseParamNoResult = configurationManager.getProperty(RESPONSE_PARAM_NO_RESULT);
    }

    @Override
    public JSONObject execute(HttpServletRequest request) throws HorseRaceBetException {
        JSONObject result = new JSONObject();
        UserBet userBet;

        switch (getAction(request)) {
            case CREATE:
                userBet = new Gson().fromJson(getParam(request), UserBet.class);

                HttpSession session = request.getSession();
                long userId = (long) session.getAttribute(requestParamId);
                userBet.setUserId(userId);

                service.save(userBet);
                break;
            case GET_BY_USER_ID:
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
