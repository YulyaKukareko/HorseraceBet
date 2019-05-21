package by.epam.javawebtraining.kukareko.horseracebet.controller.handler;

import by.epam.javawebtraining.kukareko.horseracebet.controller.GetAction;
import by.epam.javawebtraining.kukareko.horseracebet.controller.GetParams;
import by.epam.javawebtraining.kukareko.horseracebet.model.exception.HorseRaceBetException;
import by.epam.javawebtraining.kukareko.horseracebet.service.HorseService;
import by.epam.javawebtraining.kukareko.horseracebet.model.entity.Horse;
import by.epam.javawebtraining.kukareko.horseracebet.util.ConfigurationManager;
import com.google.gson.Gson;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author Yulya Kukareko
 * @version 1.0 19 Apr 2019
 */
public class HorseCommand implements Command, GetParams, GetAction {

    private ConfigurationManager configurationManager;

    private HorseService service;

    public HorseCommand() {
        service = HorseService.getInstance();
        configurationManager = ConfigurationManager.getInstance();
    }

    @Override
    public JSONObject execute(HttpServletRequest request) throws HorseRaceBetException {
        String responseParamResult = configurationManager.getProperty("configJSON.result");
        String requestParamRaceId = configurationManager.getProperty("requestParam.raceId");
        String requestParamNoResult = configurationManager.getProperty("responseParam.noResult");

        JSONObject result = new JSONObject();

        switch (getAction(request)) {
            case "create":
                Horse horse;
                horse = new Gson().fromJson(getParam(request), Horse.class);

                service.save(horse);
                break;
            case "delete":
                    horse = new Gson().fromJson(getParam(request), Horse.class);

                service.delete(horse);
                break;
            case "update":
                horse = new Gson().fromJson(getParam(request), Horse.class);

                service.update(horse);
                break;
            case "getAll":
                List<Horse> horses = service.getAll();

                result.put(responseParamResult, new JSONArray(horses));
                break;
            case "getHorsesStartingPrices":
                horses = service.getJoinHorseStartingPrice();

                result.put(responseParamResult, new JSONArray(horses));
                break;
            case "getHorsesStartingPricesExcludingByRaceId":
                JSONObject json = new JSONObject(getParam(request));
                long raceId = Long.parseLong(json.get(requestParamRaceId).toString());

                horses = service.getJoinHorseStartingPriceExcludingByRaceId(raceId);

                result.put(responseParamResult, new JSONArray(horses));
                break;
            case "getHorsesStartingPricesByRaceId":
                json = new JSONObject(getParam(request));
                raceId = Long.parseLong(json.get(requestParamRaceId).toString());

                horses = service.getJoinHorseStartingPriceByRaceId(raceId);

                result.put(responseParamResult, new JSONArray(horses));
                break;
            case "getHorsesFirstBets":
                horses = service.getJoinFirstBetAndHorseStartingPrice();

                result.put(responseParamResult, new JSONArray(horses));
                break;
            case "getHorsesSecondBets":
                horses = service.getJoinSecondBetAndHorseStartingPrice();

                result.put(responseParamResult, new JSONArray(horses));
                break;
            case "getHorsesStartingPricesByStartingPriceId":
                String requestStartingSP = configurationManager.getProperty("responseParam.startingPriceId");

                json = new JSONObject(getParam(request));
                long startingPriceId = Long.parseLong(json.get(requestStartingSP).toString());

                horse = service.getJoinHorseStartingPriceByStartingPriceId(startingPriceId);

                if (horse != null) {
                    result.put(responseParamResult, new JSONObject(horse));
                } else {
                    result.put(responseParamResult, requestParamNoResult);
                }
                break;
            case "getById":
                String requestParamId = configurationManager.getProperty("params.id");

                json = new JSONObject(getParam(request));
                long horseId = Long.parseLong(json.get(requestParamId).toString());

                horse = service.getById(horseId);

                if (horse != null) {
                    result.put(responseParamResult, new JSONObject(horse));
                } else {
                    result.put(responseParamResult, requestParamNoResult);
                }
                break;
        }
        return result;
    }
}
