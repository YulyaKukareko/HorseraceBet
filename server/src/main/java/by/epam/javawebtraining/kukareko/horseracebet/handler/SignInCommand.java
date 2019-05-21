package by.epam.javawebtraining.kukareko.horseracebet.handler;

import static by.epam.javawebtraining.kukareko.horseracebet.util.constant.JSONParamConstant.*;

import by.epam.javawebtraining.kukareko.horseracebet.controller.GetParams;
import by.epam.javawebtraining.kukareko.horseracebet.model.exception.HorseRaceBetException;
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

    private ConfigurationManager configurationManager;

    private String requestParamLogin;
    private String requestParamPassword;
    private String responseParamAuthorization;
    private String statusRespSuccess;
    private String sessionParamId;
    private String responseParamRole;
    private String statusRespFailed;

    private UserService service;

    public SignInCommand() {
        this.service = UserService.getInstance();
        this.configurationManager = ConfigurationManager.getInstance();
        this.requestParamLogin = configurationManager.getProperty(REQUEST_PARAM_LOGIN);
        this.requestParamPassword = configurationManager.getProperty(REQUEST_PARAM_PASSWORD);
        this.responseParamAuthorization = configurationManager.getProperty(RESPONSE_PARAM_AUTHORIZATION);
        this.statusRespSuccess = configurationManager.getProperty(RESPONSE_PARAM_STATUS_SUCCESS);
        this.sessionParamId = configurationManager.getProperty(PARAMS_ID);
        this.responseParamRole = configurationManager.getProperty(RESPONSE_PARAM_ROLE);
        this.statusRespFailed = configurationManager.getProperty(RESPONSE_PARAM_STATUS_FAILED);
    }

    @Override
    public JSONObject execute(HttpServletRequest request) throws HorseRaceBetException {
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

            result.put(responseParamAuthorization, statusRespSuccess);

            newSession.setAttribute(sessionParamId, user.getId());
            result.put(responseParamRole, user.getRole());
        } else {
            result.put(responseParamAuthorization, statusRespFailed);
        }

        return result;
    }
}
