package by.epam.javawebtraining.kukareko.horseracebet.handler;

import static by.epam.javawebtraining.kukareko.horseracebet.util.constant.JSONParamConstant.*;

import by.epam.javawebtraining.kukareko.horseracebet.controller.GetParams;
import by.epam.javawebtraining.kukareko.horseracebet.model.exception.HorseRaceBetException;
import by.epam.javawebtraining.kukareko.horseracebet.service.UserService;
import by.epam.javawebtraining.kukareko.horseracebet.model.entity.User;
import by.epam.javawebtraining.kukareko.horseracebet.util.ConfigurationManager;
import by.epam.javawebtraining.kukareko.horseracebet.util.constant.GeneralConstants;
import com.google.gson.Gson;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;

/**
 * @author Yulya Kukareko
 * @version 1.0 15 Apr 2019
 */
public class SignUpCommand implements Command, GetParams {

    private ConfigurationManager configurationManager;

    private String sessionParamId;
    private int maxInactiveIntervalSession;
    private int initialUserBalance;

    private UserService service;

    public SignUpCommand() {
        this.service = UserService.getInstance();
        this.configurationManager = ConfigurationManager.getInstance();
        this.sessionParamId = configurationManager.getProperty(PARAMS_ID);
        this.maxInactiveIntervalSession = Integer.parseInt(configurationManager.getProperty(GeneralConstants.MAX_INACTIVE_INTERVAL_SESSION));
        this.initialUserBalance = Integer.parseInt(configurationManager.getProperty(GeneralConstants.INITIAL_USER_BALANCE));
    }

    @Override
    public JSONObject execute(HttpServletRequest request) throws HorseRaceBetException {
        User user = new Gson().fromJson(getParam(request), User.class);
        user.setBalance(new BigDecimal(initialUserBalance));
        JSONObject result = new JSONObject();

        service.save(user);

        HttpSession newSession = request.getSession(true);
        newSession.setMaxInactiveInterval(maxInactiveIntervalSession);

        newSession.setAttribute(sessionParamId, user.getId());

        return result;
    }
}
