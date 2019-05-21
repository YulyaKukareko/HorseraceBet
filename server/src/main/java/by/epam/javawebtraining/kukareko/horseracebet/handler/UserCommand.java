package by.epam.javawebtraining.kukareko.horseracebet.controller.handler;

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
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author Yulya Kukareko
 * @version 1.0 28 Apr 2019
 */
public class UserCommand implements Command, GetAction, GetParams {

    private ConfigurationManager configurationManager;

    private UserService service;

    public UserCommand() {
        service = UserService.getInstance();
        configurationManager = ConfigurationManager.getInstance();
    }

    @Override
    public JSONObject execute(HttpServletRequest request) throws HorseRaceBetException {
        String requestParamId = configurationManager.getProperty("params.id");

        JSONObject result = new JSONObject();
        User user;

        switch (getAction(request)) {
            case "getUserById":

                HttpSession session = request.getSession();
                long userId = (long) session.getAttribute(requestParamId);
                user = service.getUserById(userId);

                result.put("result", new JSONObject(user));
                break;
            case "update":
                user = new Gson().fromJson(getParam(request), User.class);
                session = request.getSession();
                user.setId((long) session.getAttribute(requestParamId));

                service.update(user);
                break;
            case "getAll":
                String responseParamResult = configurationManager.getProperty("configJSON.result");
                String responseParamNoResult = configurationManager.getProperty("responseParam.noResult");

                List<User> userBets = service.getAll();

                if (userBets != null) {
                    result.put(responseParamResult, new JSONArray(userBets));
                } else {
                    result.put(responseParamResult, responseParamNoResult);
                }
                break;
            case "updateBalance":
                String requestParamAddingMoney = configurationManager.getProperty("requestParam.addingMoney");

                JSONObject json = new JSONObject(getParam(request));
                long addingSum = Long.parseLong(json.get(requestParamAddingMoney).toString());
                userId = Long.parseLong(json.get(requestParamId).toString());

                service.addUserBalanceMoney(userId, BigDecimal.valueOf(addingSum));
        }

        return result;
    }
}