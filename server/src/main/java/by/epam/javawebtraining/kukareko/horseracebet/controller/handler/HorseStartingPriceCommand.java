package by.epam.javawebtraining.kukareko.horseracebet.controller.handler;

import by.epam.javawebtraining.kukareko.horseracebet.controller.GetAction;
import by.epam.javawebtraining.kukareko.horseracebet.controller.GetParams;
import by.epam.javawebtraining.kukareko.horseracebet.service.HorseStartingPriceService;
import by.epam.javawebtraining.kukareko.horseracebet.model.entity.HorseStartingPrice;
import com.google.gson.Gson;
import org.json.JSONArray;
import org.json.JSONObject;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author Yulya Kukareko
 * @version 1.0 20 Apr 2019
 */
public class HorseStartingPriceCommand implements Command, GetAction, GetParams {

    private HorseStartingPriceService service;

    public HorseStartingPriceCommand() {
        service = HorseStartingPriceService.getInstance();
    }

    @Override
    public JSONObject execute(HttpServletRequest request) {
        JSONObject result = new JSONObject();
        List<HorseStartingPrice> horseStartingPrices;

        switch (getAction(request)) {
            case "create":
                HorseStartingPrice race = new Gson().fromJson(getParam(request), HorseStartingPrice.class);

                addStatusResponse(result, service.save(race));
                break;
            case "delete":
                race = new Gson().fromJson(getParam(request), HorseStartingPrice.class);

                addStatusResponse(result, service.delete(race));
                break;
            case "getAll":
                horseStartingPrices = service.getAll();

                result.put("result", new JSONArray(horseStartingPrices));
                break;
            case "update":
                race = new Gson().fromJson(getParam(request), HorseStartingPrice.class);

                addStatusResponse(result, service.update(race));
                break;
            case "getHorseStartingPriceFirstHorseBets":
                horseStartingPrices = service.getJoinFirstHorsesBet();

                result.put("result", new JSONArray(horseStartingPrices));
                break;
            case "getHorseStartingPriceSecondHorseBets":
                horseStartingPrices = service.getJoinSecondHorsesBet();

                result.put("result", new JSONArray(horseStartingPrices));
                break;
            case "getByRaceId":
                JSONObject json = new JSONObject(getParam(request));
                long raceId = Long.parseLong(json.get("raceId").toString());
                horseStartingPrices = service.getByRaceId(raceId);

                result.put("result", new JSONArray(horseStartingPrices));
                break;
            case "getCountByRaceId":
                json = new JSONObject(getParam(request));
                raceId = Long.parseLong(json.get("raceId").toString());
                Integer countRow = service.getCountByRaceId(raceId);

                if (countRow != null) {
                    result.put("result", countRow);
                } else {
                    result.put("result", "no result");
                }
                break;
            case "getById":
                json = new JSONObject(getParam(request));
                long horseStartingPriceId = Long.parseLong(json.get("id").toString());
                HorseStartingPrice horseStartingPrice = service.getById(horseStartingPriceId);

                if (horseStartingPrice != null) {
                    result.put("result", new JSONObject(horseStartingPrice));
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
