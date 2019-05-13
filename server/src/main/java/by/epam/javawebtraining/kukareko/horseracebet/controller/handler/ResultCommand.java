package by.epam.javawebtraining.kukareko.horseracebet.controller.handler;

import by.epam.javawebtraining.kukareko.horseracebet.controller.GetAction;
import by.epam.javawebtraining.kukareko.horseracebet.controller.GetParams;
import by.epam.javawebtraining.kukareko.horseracebet.model.exception.HorseRaceBetException;
import by.epam.javawebtraining.kukareko.horseracebet.service.ResultService;
import by.epam.javawebtraining.kukareko.horseracebet.model.entity.Result;
import by.epam.javawebtraining.kukareko.horseracebet.util.ConfigurationManager;
import com.google.gson.Gson;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author Yulya Kukareko
 * @version 1.0 24 Apr 2019
 */
public class ResultCommand implements Command, GetAction, GetParams {

    private static ConfigurationManager configurationManager;

    private ResultService service;

    public ResultCommand() {
        configurationManager = ConfigurationManager.getInstance();
        service = ResultService.getInstance();
    }

    @Override
    public JSONObject execute(HttpServletRequest request, HttpServletResponse response) throws HorseRaceBetException {
        JSONObject resultJSON = new JSONObject();

        switch (getAction(request)) {
            case "create":
                Result result = new Gson().fromJson(getParam(request), Result.class);

                service.save(result);
                break;
            case "getByRaceId":
                String responseParamResult = configurationManager.getProperty("configJSON.result");
                String requestParamRaceId = configurationManager.getProperty("requestParam.raceId");

                JSONObject json = new JSONObject(getParam(request));
                long raceId = Long.parseLong(json.get(requestParamRaceId).toString());

                List<Result> results = service.getByRaceId(raceId);

                resultJSON.put(responseParamResult, new JSONArray(results));
                break;
        }

        return resultJSON;
    }
}
