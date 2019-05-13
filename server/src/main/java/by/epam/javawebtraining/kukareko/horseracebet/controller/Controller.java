package by.epam.javawebtraining.kukareko.horseracebet.controller;

import by.epam.javawebtraining.kukareko.horseracebet.controller.handler.Command;
import by.epam.javawebtraining.kukareko.horseracebet.controller.helper.RequestHelper;
import by.epam.javawebtraining.kukareko.horseracebet.util.ConfigurationManager;
import org.apache.log4j.Logger;
import org.json.JSONObject;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Yulya Kukareko
 * @version 1.0 14 Apr 2019
 */
public class Controller extends HttpServlet {

    private static final Logger LOGGER;

    private ConfigurationManager configurationManager;
    private RequestHelper requestHelper;

    static {
        LOGGER = Logger.getLogger("LoginControllerLog");
    }

    public Controller() {
        requestHelper = RequestHelper.getInstance();
        configurationManager = ConfigurationManager.getInstance();
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
        String statusResp = configurationManager.getProperty("responseParam.status");

        JSONObject result = new JSONObject();

        try {
            Command command = requestHelper.getCommand(request);

            if (command != null) {
                result = command.execute(request, response);
            } else {
                request.getRequestDispatcher("/WEB-INF/views/error404.jsp").forward(request, response);
            }
            String statusRespSuccess = configurationManager.getProperty("responseParam.status.success");

            result.put(statusResp, statusRespSuccess);

        } catch (Exception ex) {
            String statusRespFailed = configurationManager.getProperty("responseParam.status.failed");
            String errorMessResp = configurationManager.getProperty("responseParam.errorMes");

            result.put(statusResp, statusRespFailed);
            result.put(errorMessResp, ex.getMessage());
        }
        String contentTypeResp = configurationManager.getProperty("contentTypeResponse");
        String encodingResp = configurationManager.getProperty("encodingResponse");

        response.setContentType(contentTypeResp);
        response.setCharacterEncoding(encodingResp);

        try {
            response.getWriter().println(result);
        } catch (IOException ex) {
            LOGGER.error(ex.getMessage());
        }
    }
}
