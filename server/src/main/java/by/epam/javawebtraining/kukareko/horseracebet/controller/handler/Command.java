package by.epam.javawebtraining.kukareko.horseracebet.controller.handler;

import by.epam.javawebtraining.kukareko.horseracebet.model.exception.HorseRaceBetException;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Yulya Kukareko
 * @version 1.0 13 Apr 2019
 */
public interface Command {

    JSONObject execute(HttpServletRequest request, HttpServletResponse response) throws HorseRaceBetException;
}