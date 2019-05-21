package by.epam.javawebtraining.kukareko.horseracebet.controller;

import static by.epam.javawebtraining.kukareko.horseracebet.util.constant.JSONParamConstant.*;

import by.epam.javawebtraining.kukareko.horseracebet.handler.Command;
import by.epam.javawebtraining.kukareko.horseracebet.controller.helper.RequestHelper;
import by.epam.javawebtraining.kukareko.horseracebet.util.ConfigurationManager;
import by.epam.javawebtraining.kukareko.horseracebet.util.constant.GeneralConstants;
import by.epam.javawebtraining.kukareko.horseracebet.util.constant.LogConstant;
import org.apache.log4j.Logger;
import org.json.JSONObject;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Yulya Kukareko
 * @version 1.0 14 Apr 2019
 */
public class Controller extends HttpServlet {

    private static final Logger LOGGER;

    private String statusResp;
    private String statusRespSuccess;
    private String statusRespFailed;
    private String errorMessResp;
    private String contentTypeResp;
    private String encodingResp;

    private ConfigurationManager configurationManager;
    private RequestHelper requestHelper;

    static {
        LOGGER = Logger.getLogger(LogConstant.LOGIN_CONTROLLER_LOG);
    }

    public Controller() {
        this.requestHelper = RequestHelper.getInstance();
        this.configurationManager = ConfigurationManager.getInstance();
        this.statusResp = configurationManager.getProperty(RESPONSE_PARAM_STATUS);
        this.statusRespSuccess = configurationManager.getProperty(RESPONSE_PARAM_STATUS_SUCCESS);
        this.contentTypeResp = configurationManager.getProperty(GeneralConstants.CONTENT_TYPE_RESPONSE);
        this.encodingResp = configurationManager.getProperty(GeneralConstants.ENCODING);
        this.statusRespFailed = configurationManager.getProperty(RESPONSE_PARAM_STATUS_FAILED);
        this.errorMessResp = configurationManager.getProperty(RESPONSE_PARAM_ERROR_MES);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        processRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        processRequest(req, resp);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) {

        JSONObject result = new JSONObject();

        try {
            Command command = requestHelper.getCommand(request);

            if (command != null) {
                result = command.execute(request);
            } else {
                request.getRequestDispatcher(GeneralConstants.WEB_INF_VIEWS_ERROR_404_JSP).forward(request, response);
            }

            result.put(statusResp, statusRespSuccess);

        } catch (Exception ex) {

            result.put(statusResp, statusRespFailed);
            result.put(errorMessResp, ex.getMessage());
        }

        response.setContentType(contentTypeResp);
        response.setCharacterEncoding(encodingResp);

        try {
            response.getWriter().println(result);
        } catch (IOException ex) {
            LOGGER.error(ex.getMessage());
        }
    }
}
