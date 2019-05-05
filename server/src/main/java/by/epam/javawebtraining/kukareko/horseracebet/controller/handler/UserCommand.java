package by.epam.javawebtraining.kukareko.horseracebet.controller.handler;

import by.epam.javawebtraining.kukareko.horseracebet.controller.GetAction;
import by.epam.javawebtraining.kukareko.horseracebet.controller.GetParams;
import by.epam.javawebtraining.kukareko.horseracebet.service.UserService;
import by.epam.javawebtraining.kukareko.horseracebet.model.entity.User;
import com.google.gson.Gson;
import org.json.JSONObject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author Yulya Kukareko
 * @version 1.0 28 Apr 2019
 */
public class UserCommand implements Command, GetAction, GetParams {

    private UserService service;

    public UserCommand() {
        service = UserService.getInstance();
    }

    @Override
    public JSONObject execute(HttpServletRequest request) {
        JSONObject result = new JSONObject();
        User user;

        switch (getAction(request)) {
            case "getUserById":
                HttpSession session = request.getSession();
                long userId = (long) session.getAttribute("id");
                user = service.getUserById(userId);

                result.put("result", new JSONObject(user));
                break;
            case "update":
                user = new Gson().fromJson(getParam(request), User.class);
                session = request.getSession();
                user.setId((long) session.getAttribute("id"));

                addStatusResponse(result, service.update(user));
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
