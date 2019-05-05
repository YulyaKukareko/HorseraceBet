package by.epam.javawebtraining.kukareko.horseracebet.controller.handler;

import by.epam.javawebtraining.kukareko.horseracebet.controller.GetAction;
import by.epam.javawebtraining.kukareko.horseracebet.controller.GetParams;
import by.epam.javawebtraining.kukareko.horseracebet.service.HorseService;
import by.epam.javawebtraining.kukareko.horseracebet.model.entity.Horse;
import com.google.gson.Gson;
import org.json.JSONArray;
import org.json.JSONObject;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author Yulya Kukareko
 * @version 1.0 19 Apr 2019
 */
public class HorseCommand implements Command, GetParams, GetAction {

    private HorseService service;

    public HorseCommand() {
        service = HorseService.getInstance();
    }

    @Override
    public JSONObject execute(HttpServletRequest request) {
        JSONObject result = new JSONObject();
        Horse horse;

        switch (getAction(request)) {
            case "create":
                horse = new Gson().fromJson(getParam(request), Horse.class);

                addStatusResponse(result, service.save(horse));
                break;
            case "delete":
                horse = new Gson().fromJson(getParam(request), Horse.class);

                addStatusResponse(result, service.delete(horse));
                break;
            case "update":
                horse = new Gson().fromJson(getParam(request), Horse.class);

                addStatusResponse(result, service.update(horse));
                break;
            case "getAll":
                List<Horse> horses = service.getAll();

                result.put("result", new JSONArray(horses));
                break;
            case "getHorsesStartingPrices":
                horses = service.getJoinHorseStartingPrice();

                result.put("result", new JSONArray(horses));
                break;
            case "getHorsesStartingPricesExcludingByRaceId":
                JSONObject json = new JSONObject(getParam(request));
                long raceId = Long.parseLong(json.get("raceId").toString());
                horses = service.getJoinHorseStartingPriceExcludingByRaceId(raceId);

                result.put("result", new JSONArray(horses));
                break;
            case "getHorsesStartingPricesByRaceId":
                json = new JSONObject(getParam(request));
                raceId = Long.parseLong(json.get("raceId").toString());
                horses = service.getJoinHorseStartingPriceByRaceId(raceId);

                result.put("result", new JSONArray(horses));
                break;
            case "getHorsesFirstBets":
                horses = service.getJoinFirstBetAndHorseStartingPrice();

                result.put("result", new JSONArray(horses));
                break;
            case "getHorsesSecondBets":
                horses = service.getJoinSecondBetAndHorseStartingPrice();

                result.put("result", new JSONArray(horses));
                break;
            case "getHorsesStartingPricesByStartingPriceId":
                json = new JSONObject(getParam(request));
                long startingPriceId = Long.parseLong(json.get("startingPriceId").toString());
                horse = service.getJoinHorseStartingPriceByStartingPriceId(startingPriceId);

                if (horse != null) {
                    result.put("result", new JSONObject(horse));
                } else {
                    result.put("result", "no result");
                }
                break;
            case "getById":
                json = new JSONObject(getParam(request));
                long horseId = Long.parseLong(json.get("id").toString());
                horse = service.getById(horseId);

                if (horse != null) {
                    result.put("result", new JSONObject(horse));
                } else {
                    result.put("result", "no result");
                }
                break;
        }
        return result;
    }

    private void addStatusResponse(JSONObject json, boolean isSuccess) {
        if (isSuccess) {
            json.put("result", "success");
        } else {
            json.put("result", "failed");
        }
    }
}
