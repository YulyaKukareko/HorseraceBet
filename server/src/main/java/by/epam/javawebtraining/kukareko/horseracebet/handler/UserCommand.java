package by.epam.javawebtraining.kukareko.horseracebet.handler;

import static by.epam.javawebtraining.kukareko.horseracebet.util.constant.ActionConstant.*;
import static by.epam.javawebtraining.kukareko.horseracebet.util.constant.JSONParamConstant.*;

import by.epam.javawebtraining.kukareko.horseracebet.controller.GetAction;
import by.epam.javawebtraining.kukareko.horseracebet.controller.GetParams;
import by.epam.javawebtraining.kukareko.horseracebet.model.exception.HorseRaceBetException;
import by.epam.javawebtraining.kukareko.horseracebet.service.UserService;
import by.epam.javawebtraining.kukareko.horseracebet.model.entity.User;
import by.epam.javawebtraining.kukareko.horseracebet.util.ConfigurationManager;
import com.google.gson.Gson;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author Yulya Kukareko
 * @version 1.0 28 Apr 2019
 */
public class UserCommand implements Command, GetAction, GetParams {

    private ConfigurationManager configurationManager;

    private String requestParamId;
    private String responseParamResult;
    private String responseParamNoResult;
    private String requestParamAddingMoney;

    private UserService service;

    public UserCommand() {
        this.service = UserService.getInstance();
        this.configurationManager = ConfigurationManager.getInstance();
        this.requestParamId = configurationManager.getProperty(PARAMS_ID);
        this.responseParamResult = configurationManager.getProperty(CONFIG_JSON_RESULT);
        this.responseParamNoResult = configurationManager.getProperty(RESPONSE_PARAM_NO_RESULT);
        this.requestParamAddingMoney = configurationManager.getProperty(REQUEST_PARAM_ADDING_MONEY);
    }

    @Override
    public JSONObject execute(HttpServletRequest request) throws HorseRaceBetException {
        JSONObject result = new JSONObject();
        User user;

        switch (getAction(request)) {
            case GET_BY_ID:

                HttpSession session = request.getSession();
                long userId = (long) session.getAttribute(requestParamId);
                user = service.getUserById(userId);

                result.put(responseParamResult, new JSONObject(user));
                break;
            case UPDATE:
                user = new Gson().fromJson(getParam(request), User.class);
                session = request.getSession();
                user.setId((long) session.getAttribute(requestParamId));

                service.update(user);
                break;
            case GET_ALL:
                List<User> userBets = service.getAll();

                if (userBets != null) {
                    result.put(responseParamResult, new JSONArray(userBets));
                } else {
                    result.put(responseParamResult, responseParamNoResult);
                }
                break;
            case UPDATE_BALANCE:
                JSONObject json = new JSONObject(getParam(request));
                long addingSum = Long.parseLong(json.get(requestParamAddingMoney).toString());
                userId = Long.parseLong(json.get(requestParamId).toString());

                service.addUserBalanceMoney(userId, BigDecimal.valueOf(addingSum));
        }

        return result;
    }
}