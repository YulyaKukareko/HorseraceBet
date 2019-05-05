package by.epam.javawebtraining.kukareko.horseracebet.controller.handler;

import by.epam.javawebtraining.kukareko.horseracebet.controller.GetAction;
import by.epam.javawebtraining.kukareko.horseracebet.controller.GetParams;
import by.epam.javawebtraining.kukareko.horseracebet.service.RaceService;
import by.epam.javawebtraining.kukareko.horseracebet.model.entity.Race;
import by.epam.javawebtraining.kukareko.horseracebet.model.entity.RaceType;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.json.JSONArray;
import org.json.JSONObject;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

/**
 * @author Yulya Kukareko
 * @version 1.0 19 Apr 2019
 */
public class RaceCommand implements Command, GetAction, GetParams {

    private RaceService service;

    public RaceCommand() {
        service = RaceService.getInstance();
    }

    @Override
    public JSONObject execute(HttpServletRequest request) {
        JSONObject result = new JSONObject();
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd HH:mm:ss.s")
                .create();

        switch (getAction(request)) {
            case "create":
                Race race = gson.fromJson(getParam(request), Race.class);

                addStatusResponse(result, service.save(race));
                break;
            case "delete":
                race = gson.fromJson(getParam(request), Race.class);

                addStatusResponse(result, service.delete(race));
                break;
            case "getAll":
                List<Race> races = service.getAll();

                result.put("result", new JSONArray(races));
                break;
            case "update":
                race = gson.fromJson(getParam(request), Race.class);

                addStatusResponse(result, service.update(race));
                break;
            case "getAllTypes":
                List<RaceType> types = Arrays.asList(RaceType.values());

                result.put("result", new JSONArray(types));
                break;
            case "getRaceNotJoinResult":
                races = service.getNotJoinResult();

                result.put("result", new JSONArray(races));
                break;
            case "getRacesStartingPrices":
                races = service.getJoinHorseStarting();

                result.put("result", new JSONArray(races));
                break;
            case "getRacesStartingPricesByRaceId":
                JSONObject json = new JSONObject(getParam(request));
                long raceId = Long.parseLong(json.get("raceId").toString());
                races = service.getJoinHorseStartingPriceById(raceId);

                result.put("result", new JSONArray(races));
                break;
            case "getRacesBets":
                races = service.getJoinBet();

                result.put("result", new JSONArray(races));
                break;
            case "getById":
                json = new JSONObject(getParam(request));
                raceId = Long.parseLong(json.get("id").toString());
                race = service.getById(raceId);

                if (race != null) {
                    result.put("result", new JSONObject(race));
                } else {
                    result.put("result", "no result");
                }
                break;
            case "getCompletedRacesInResult":
                races = service.getCompletedRacesJoinResult();

                result.put("result", new JSONArray(races));
                break;
            case "getCompletedRaces":
                races = service.getCompletedRacesNotJoinResult();

                result.put("result", new JSONArray(races));
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
