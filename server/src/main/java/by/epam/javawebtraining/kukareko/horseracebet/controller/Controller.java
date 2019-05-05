package by.epam.javawebtraining.kukareko.horseracebet.controller;

import by.epam.javawebtraining.kukareko.horseracebet.controller.handler.Command;
import by.epam.javawebtraining.kukareko.horseracebet.controller.helper.RequestHelper;
import org.apache.log4j.Logger;
import org.json.JSONObject;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Yulya Kukareko
 * @version 1.0 14 Apr 2019
 */
public class Controller extends HttpServlet {

    private RequestHelper requestHelper;
    private static final Logger LOGGER;

    static {
        LOGGER = Logger.getLogger("LoginControllerLog");
    }

    public Controller() {
        requestHelper = RequestHelper.getInstance();
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
        try {
            Command command = requestHelper.getCommand(request);

            JSONObject result = command.execute(request);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().println(result);

        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());
        }
    }
}
