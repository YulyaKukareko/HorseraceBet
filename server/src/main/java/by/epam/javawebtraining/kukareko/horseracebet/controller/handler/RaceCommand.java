package by.epam.javawebtraining.kukareko.horseracebet.controller.handler;

import by.epam.javawebtraining.kukareko.horseracebet.controller.GetAction;
import by.epam.javawebtraining.kukareko.horseracebet.controller.GetParams;
import by.epam.javawebtraining.kukareko.horseracebet.model.exception.HorseRaceBetException;
import by.epam.javawebtraining.kukareko.horseracebet.service.RaceService;
import by.epam.javawebtraining.kukareko.horseracebet.model.entity.Race;
import by.epam.javawebtraining.kukareko.horseracebet.model.entity.RaceType;
import by.epam.javawebtraining.kukareko.horseracebet.util.ConfigurationManager;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;

/**
 * @author Yulya Kukareko
 * @version 1.0 19 Apr 2019
 */
public class RaceCommand implements Command, GetAction, GetParams {

    private static ConfigurationManager configurationManager;

    private RaceService service;

    public RaceCommand() {
        service = RaceService.getInstance();
        configurationManager = ConfigurationManager.getInstance();
    }

    @Override
    public JSONObject execute(HttpServletRequest request) throws HorseRaceBetException {
        String responseParamResult = configurationManager.getProperty("configJSON.result");
        String dataFormat = configurationManager.getProperty("dataFormat");

        JSONObject result = new JSONObject();
        Gson gson = new GsonBuilder()
                .setDateFormat(dataFormat)
                .create();

        switch (getAction(request)) {
            case "create":
                Race race = gson.fromJson(getParam(request), Race.class);

                service.save(race);
                break;
            case "delete":
                race = gson.fromJson(getParam(request), Race.class);

                service.delete(race);
                break;
            case "getAll":
                List<Race> races = service.getAll();

                result.put(responseParamResult, new JSONArray(races));
                break;
            case "update":
                race = gson.fromJson(getParam(request), Race.class);

                service.update(race);
                break;
            case "getAllTypes":
                List<RaceType> types = Arrays.asList(RaceType.values());

                result.put(responseParamResult, new JSONArray(types));
                break;
            case "getRaceNotJoinResult":
                races = service.getNotJoinResult();

                result.put(responseParamResult, new JSONArray(races));
                break;
            case "getRacesStartingPrices":
                races = service.getJoinHorseStarting();

                result.put(responseParamResult, new JSONArray(races));
                break;
            case "getRacesStartingPricesByRaceId":
                String requestParamRaceId = configurationManager.getProperty("requestParam.raceId");

                JSONObject json = new JSONObject(getParam(request));
                long raceId = Long.parseLong(json.get(requestParamRaceId).toString());

                races = service.getJoinHorseStartingPriceById(raceId);

                result.put(responseParamResult, new JSONArray(races));
                break;
            case "getRacesBets":
                races = service.getJoinBet();

                result.put(responseParamResult, new JSONArray(races));
                break;
            case "getById":
                String requestParamId = configurationManager.getProperty("params.id");
                String requestParamNoResult = configurationManager.getProperty("responseParam.noResult");

                json = new JSONObject(getParam(request));
                raceId = Long.parseLong(json.get(requestParamId).toString());
                race = service.getById(raceId);

                if (race != null) {
                    result.put(responseParamResult, new JSONObject(race));
                } else {
                    result.put(responseParamResult, requestParamNoResult);
                }
                break;
            case "getCompletedRacesInResult":
                races = service.getCompletedRacesJoinResult();

                result.put(responseParamResult, new JSONArray(races));
                break;
            case "getCompletedRaces":
                races = service.getCompletedRacesNotJoinResult();

                result.put(responseParamResult, new JSONArray(races));
                break;
        }

        return result;
    }
}
