package by.epam.javawebtraining.kukareko.horseracebet.controller.handler;

import by.epam.javawebtraining.kukareko.horseracebet.controller.GetAction;
import by.epam.javawebtraining.kukareko.horseracebet.controller.GetParams;
import by.epam.javawebtraining.kukareko.horseracebet.service.UserService;
import by.epam.javawebtraining.kukareko.horseracebet.model.entity.User;
import by.epam.javawebtraining.kukareko.horseracebet.util.ConfigurationManager;
import org.json.JSONObject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author Yulya Kukareko
 * @version 1.0 13 Apr 2019
 */
public class SignInCommand implements Command, GetParams {

    private UserService service;

    public SignInCommand() {
        service = UserService.getInstance();
    }

    private static final ConfigurationManager configurationManager = ConfigurationManager.getInstance();
    private static final String LOGIN_FIELD_REQUEST = configurationManager.getProperty("login");
    private static final String PASSWORD_FIELD_REQUEST = configurationManager.getProperty("password");

    @Override
    public JSONObject execute(HttpServletRequest request) {
        JSONObject userDetails = new JSONObject(getParam(request));
        String login = userDetails.getString(LOGIN_FIELD_REQUEST);
        String password = userDetails.getString(PASSWORD_FIELD_REQUEST);

        User user = service.checkUser(login, password);

        JSONObject result = new JSONObject();
        if (user != null) {

            HttpSession oldSession = request.getSession(false);
            if (oldSession != null) {
                oldSession.invalidate();
            }

            HttpSession newSession = request.getSession(true);
            int maxInactiveIntervalSession = -1;
            newSession.setMaxInactiveInterval(maxInactiveIntervalSession);

            newSession.setAttribute("id", user.getId());
            result.put("authorization", "success");
            result.put("role", user.getRole());
        } else {
            result.put("authorization", "failed");
        }

        return result;
    }
}
