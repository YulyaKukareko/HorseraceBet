package by.epam.javawebtraining.kukareko.horseracebet.controller.handler;

import by.epam.javawebtraining.kukareko.horseracebet.controller.GetAction;
import by.epam.javawebtraining.kukareko.horseracebet.controller.GetParams;
import by.epam.javawebtraining.kukareko.horseracebet.model.exception.HorseRaceBetException;
import by.epam.javawebtraining.kukareko.horseracebet.service.HorseStartingPriceService;
import by.epam.javawebtraining.kukareko.horseracebet.model.entity.HorseStartingPrice;
import by.epam.javawebtraining.kukareko.horseracebet.util.ConfigurationManager;
import com.google.gson.Gson;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author Yulya Kukareko
 * @version 1.0 20 Apr 2019
 */
public class HorseStartingPriceCommand implements Command, GetAction, GetParams {

    private static ConfigurationManager configurationManager;

    private HorseStartingPriceService service;

    public HorseStartingPriceCommand() {
        configurationManager = ConfigurationManager.getInstance();
        service = HorseStartingPriceService.getInstance();
    }

    @Override
    public JSONObject execute(HttpServletRequest request, HttpServletResponse response) throws HorseRaceBetException {
        String requestParamRaceId = configurationManager.getProperty("requestParam.raceId");
        String responseParamResult = configurationManager.getProperty("configJSON.result");
        String requestParamNoResult = configurationManager.getProperty("responseParam.noResult");

        JSONObject result = new JSONObject();
        List<HorseStartingPrice> horseStartingPrices;

        switch (getAction(request)) {
            case "create":
                HorseStartingPrice race = new Gson().fromJson(getParam(request), HorseStartingPrice.class);

                service.save(race);
                break;
            case "delete":
                race = new Gson().fromJson(getParam(request), HorseStartingPrice.class);

                service.delete(race);
                break;
            case "getAll":
                horseStartingPrices = service.getAll();

                result.put(responseParamResult, new JSONArray(horseStartingPrices));
                break;
            case "update":
                race = new Gson().fromJson(getParam(request), HorseStartingPrice.class);

                service.update(race);
                break;
            case "getHorseStartingPriceFirstHorseBets":
                horseStartingPrices = service.getJoinFirstHorsesBet();

                result.put(responseParamResult, new JSONArray(horseStartingPrices));
                break;
            case "getHorseStartingPriceSecondHorseBets":
                horseStartingPrices = service.getJoinSecondHorsesBet();

                result.put(responseParamResult, new JSONArray(horseStartingPrices));
                break;
            case "getByRaceId":
                JSONObject json = new JSONObject(getParam(request));
                long raceId = Long.parseLong(json.get(requestParamRaceId).toString());

                horseStartingPrices = service.getByRaceId(raceId);

                result.put(responseParamResult, new JSONArray(horseStartingPrices));
                break;
            case "getCountByRaceId":
                json = new JSONObject(getParam(request));
                raceId = Long.parseLong(json.get(requestParamRaceId).toString());

                Integer countRow = service.getCountByRaceId(raceId);

                if (countRow != null) {
                    result.put(responseParamResult, countRow);
                } else {
                    result.put(responseParamResult, requestParamNoResult);
                }
                break;
            case "getById":
                String requestParamId = configurationManager.getProperty("params.id");

                json = new JSONObject(getParam(request));
                long horseStartingPriceId = Long.parseLong(json.get(requestParamId).toString());

                HorseStartingPrice horseStartingPrice = service.getById(horseStartingPriceId);

                if (horseStartingPrice != null) {
                    result.put(responseParamResult, new JSONObject(horseStartingPrice));
                } else {
                    result.put(responseParamResult, requestParamNoResult);
                }
                break;
        }
        return result;
    }
}
