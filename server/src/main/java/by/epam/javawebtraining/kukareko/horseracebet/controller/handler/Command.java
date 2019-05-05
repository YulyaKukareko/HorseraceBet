package by.epam.javawebtraining.kukareko.horseracebet.controller.handler;

import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Yulya Kukareko
 * @version 1.0 13 Apr 2019
 */
public interface Command {

    JSONObject execute(HttpServletRequest request);
}