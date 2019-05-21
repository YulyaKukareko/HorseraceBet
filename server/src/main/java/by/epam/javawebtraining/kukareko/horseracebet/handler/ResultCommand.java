package by.epam.javawebtraining.kukareko.horseracebet.handler;

import static by.epam.javawebtraining.kukareko.horseracebet.util.constant.ActionConstant.*;
import static by.epam.javawebtraining.kukareko.horseracebet.util.constant.JSONParamConstant.*;

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
import java.util.List;

/**
 * @author Yulya Kukareko
 * @version 1.0 24 Apr 2019
 */
public class ResultCommand implements Command, GetAction, GetParams {

    private ConfigurationManager configurationManager;

    private String responseParamResult;
    private String requestParamRaceId;

    private ResultService service;

    public ResultCommand() {
        this.configurationManager = ConfigurationManager.getInstance();
        this.service = ResultService.getInstance();
        this.responseParamResult = configurationManager.getProperty(CONFIG_JSON_RESULT);
        this.requestParamRaceId = configurationManager.getProperty(REQUEST_PARAM_RACE_ID);
    }

    @Override
    public JSONObject execute(HttpServletRequest request) throws HorseRaceBetException {
        JSONObject resultJSON = new JSONObject();

        switch (getAction(request)) {
            case CREATE:
                Result result = new Gson().fromJson(getParam(request), Result.class);

                service.save(result);
                break;
            case GET_BY_RACE_ID:
                JSONObject json = new JSONObject(getParam(request));
                long raceId = Long.parseLong(json.get(requestParamRaceId).toString());

                List<Result> results = service.getByRaceId(raceId);

                resultJSON.put(responseParamResult, new JSONArray(results));
                break;
        }

        return resultJSON;
    }
}
