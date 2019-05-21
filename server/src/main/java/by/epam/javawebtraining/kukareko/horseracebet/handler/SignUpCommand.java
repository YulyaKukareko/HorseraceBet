package by.epam.javawebtraining.kukareko.horseracebet.controller.handler;

import by.epam.javawebtraining.kukareko.horseracebet.controller.GetParams;
import by.epam.javawebtraining.kukareko.horseracebet.model.exception.HorseRaceBetException;
import by.epam.javawebtraining.kukareko.horseracebet.service.UserService;
import by.epam.javawebtraining.kukareko.horseracebet.model.entity.User;
import by.epam.javawebtraining.kukareko.horseracebet.util.ConfigurationManager;
import com.google.gson.Gson;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;

/**
 * @author Yulya Kukareko
 * @version 1.0 15 Apr 2019
 */
public class SignUpCommand implements Command, GetParams {

    private ConfigurationManager configurationManager;

    private UserService service;

    public SignUpCommand() {
        service = UserService.getInstance();
        configurationManager = ConfigurationManager.getInstance();
    }

    @Override
    public JSONObject execute(HttpServletRequest request) throws HorseRaceBetException {
        User user = new Gson().fromJson(getParam(request), User.class);
        user.setBalance(new BigDecimal(2000));
        JSONObject result = new JSONObject();

        service.save(user);

        HttpSession newSession = request.getSession(true);
        int maxInactiveIntervalSession = -1;
        newSession.setMaxInactiveInterval(maxInactiveIntervalSession);

        String sessionParamId = configurationManager.getProperty("params.id");

        newSession.setAttribute(sessionParamId, user.getId());

        return result;
    }
}
