package by.epam.javawebtraining.kukareko.horseracebet.controller.handler;

import by.epam.javawebtraining.kukareko.horseracebet.controller.GetAction;
import by.epam.javawebtraining.kukareko.horseracebet.controller.GetParams;
import by.epam.javawebtraining.kukareko.horseracebet.service.UserBetService;
import by.epam.javawebtraining.kukareko.horseracebet.service.UserService;
import by.epam.javawebtraining.kukareko.horseracebet.model.entity.UserBet;
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

    private UserBetService service;
    private UserService userService;

    public UserBetCommand() {
        service = UserBetService.getInstance();
        userService = UserService.getInstance();
    }

    @Override
    public JSONObject execute(HttpServletRequest request) {

        JSONObject result = new JSONObject();
        UserBet userBet;

        switch (getAction(request)) {
            case "create":
                userBet = new Gson().fromJson(getParam(request), UserBet.class);

                HttpSession session = request.getSession();
                long userId = (long) session.getAttribute("id");
                userBet.setUserId(userId);
                userService.makeBet(userId, userBet.getBetMoney());

                addStatusResponse(result, service.save(userBet));
                break;
            case "getByUserId":
                session = request.getSession();
                userId = (long) session.getAttribute("id");
                List<UserBet> userBets = service.getByUserId(userId);

                if (userBets != null) {
                    result.put("result", new JSONArray(userBets));
                } else {
                    result.put("result", "no result");
                }
                break;
        }

        return result;
    }

    private void addStatusResponse(JSONObject json, boolean isSuccess) {
        if (isSuccess) {
            json.put("result", "success");
        } else {
            json.put("result", "failed");
        }
    }
}
