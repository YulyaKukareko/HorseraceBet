package by.epam.javawebtraining.kukareko.horseracebet.controller.handler;

import by.epam.javawebtraining.kukareko.horseracebet.controller.GetParams;
import by.epam.javawebtraining.kukareko.horseracebet.service.UserService;
import by.epam.javawebtraining.kukareko.horseracebet.model.entity.User;
import com.google.gson.Gson;
import org.json.JSONObject;
import javax.servlet.http.HttpServletRequest;

/**
 * @author Yulya Kukareko
 * @version 1.0 15 Apr 2019
 */
public class SignUpCommand implements Command, GetParams {

    private UserService service;

    public SignUpCommand() {
        service = UserService.getInstance();
    }

    @Override
    public JSONObject execute(HttpServletRequest request) {
        User user = new Gson().fromJson(getParam(request), User.class);
        JSONObject result = new JSONObject();

        if (service.save(user)) {
            result.put("result", "success");
        } else {
            result.put("result", "failed");
        }

        return result;
    }
}
