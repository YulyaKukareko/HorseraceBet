package by.epam.javawebtraining.kukareko.horseracebet.controller.handler;

import by.epam.javawebtraining.kukareko.horseracebet.controller.GetParams;
import by.epam.javawebtraining.kukareko.horseracebet.model.exception.HorseRaceBetException;
import by.epam.javawebtraining.kukareko.horseracebet.service.UserService;
import by.epam.javawebtraining.kukareko.horseracebet.model.entity.User;
import by.epam.javawebtraining.kukareko.horseracebet.util.ConfigurationManager;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author Yulya Kukareko
 * @version 1.0 13 Apr 2019
 */
public class SignInCommand implements Command, GetParams {

    private static ConfigurationManager configurationManager;

    private UserService service;

    public SignInCommand() {
        service = UserService.getInstance();
        configurationManager = ConfigurationManager.getInstance();
    }

    @Override
    public JSONObject execute(HttpServletRequest request) throws HorseRaceBetException {
        String requestParamLogin = configurationManager.getProperty("requestParam.login");
        String requestParamPassword = configurationManager.getProperty("requestParam.password");
        String responseParamAuthorization = configurationManager.getProperty("responseParam.authorization");

        JSONObject userDetails = new JSONObject(getParam(request));
        String login = userDetails.getString(requestParamLogin);
        String password = userDetails.getString(requestParamPassword);
        JSONObject result = new JSONObject();

        User user = service.checkUser(login, password);

        if (user != null) {

            HttpSession oldSession = request.getSession(false);
            if (oldSession != null) {
                oldSession.invalidate();
            }

            HttpSession newSession = request.getSession(true);
            int maxInactiveIntervalSession = -1;
            newSession.setMaxInactiveInterval(maxInactiveIntervalSession);

            String statusRespSuccess = configurationManager.getProperty("responseParam.status.success");

            result.put(responseParamAuthorization, statusRespSuccess);

            String sessionParamId = configurationManager.getProperty("params.id");
            String responseParamRole = configurationManager.getProperty("responseParam.role");

            newSession.setAttribute(sessionParamId, user.getId());
            result.put(responseParamRole, user.getRole());
        } else {
            String statusRespFailed = configurationManager.getProperty("responseParam.status.failed");

            result.put(responseParamAuthorization, statusRespFailed);
        }

        return result;
    }
}
