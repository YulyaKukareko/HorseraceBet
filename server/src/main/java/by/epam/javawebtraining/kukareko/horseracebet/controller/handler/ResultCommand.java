package by.epam.javawebtraining.kukareko.horseracebet.controller.handler;

import by.epam.javawebtraining.kukareko.horseracebet.controller.GetAction;
import by.epam.javawebtraining.kukareko.horseracebet.controller.GetParams;
import by.epam.javawebtraining.kukareko.horseracebet.service.ResultService;
import by.epam.javawebtraining.kukareko.horseracebet.model.entity.Result;
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

    ResultService service;

    public ResultCommand() {
        service = ResultService.getInstance();
    }

    @Override
    public JSONObject execute(HttpServletRequest request) {
        JSONObject resultJSON = new JSONObject();

        switch (getAction(request)) {
            case "create":
                Result result = new Gson().fromJson(getParam(request), Result.class);

                addStatusResponse(resultJSON, service.save(result));
                break;
            case "getByRaceId":
                JSONObject json = new JSONObject(getParam(request));
                long raceId = Long.parseLong(json.get("raceId").toString());
                List<Result> results = service.getByRaceId(raceId);

                resultJSON.put("result", new JSONArray(results));
                break;
        }

        return resultJSON;
    }

    private void addStatusResponse(JSONObject json, boolean isSuccess) {
        if (isSuccess) {
            json.put("result", "success");
        } else {
            json.put("result", "failed");
        }
    }
}
