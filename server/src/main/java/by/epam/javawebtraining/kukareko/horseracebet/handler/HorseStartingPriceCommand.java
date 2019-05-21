package by.epam.javawebtraining.kukareko.horseracebet.handler;

import static by.epam.javawebtraining.kukareko.horseracebet.util.constant.ActionConstant.*;
import static by.epam.javawebtraining.kukareko.horseracebet.util.constant.JSONParamConstant.*;

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
import java.util.List;

/**
 * @author Yulya Kukareko
 * @version 1.0 20 Apr 2019
 */
public class HorseStartingPriceCommand implements Command, GetAction, GetParams {

    private ConfigurationManager configurationManager;

    private String requestParamRaceId;
    private String responseParamResult;
    private String requestParamNoResult;
    private String requestParamId;

    private HorseStartingPriceService service;

    public HorseStartingPriceCommand() {
        this.configurationManager = ConfigurationManager.getInstance();
        this.service = HorseStartingPriceService.getInstance();
        this.requestParamRaceId = configurationManager.getProperty(REQUEST_PARAM_RACE_ID);
        this.responseParamResult = configurationManager.getProperty(CONFIG_JSON_RESULT);
        this.requestParamNoResult = configurationManager.getProperty(RESPONSE_PARAM_NO_RESULT);
        this.requestParamId = configurationManager.getProperty(PARAMS_ID);
    }

    @Override
    public JSONObject execute(HttpServletRequest request) throws HorseRaceBetException {
        JSONObject result = new JSONObject();
        List<HorseStartingPrice> horseStartingPrices;

        switch (getAction(request)) {
            case CREATE:
                HorseStartingPrice race = new Gson().fromJson(getParam(request), HorseStartingPrice.class);

                service.save(race);
                break;
            case DELETE:
                race = new Gson().fromJson(getParam(request), HorseStartingPrice.class);

                service.delete(race);
                break;
            case GET_ALL:
                horseStartingPrices = service.getAll();

                result.put(responseParamResult, new JSONArray(horseStartingPrices));
                break;
            case UPDATE:
                race = new Gson().fromJson(getParam(request), HorseStartingPrice.class);

                service.update(race);
                break;
            case GET_HORSE_STARTING_PRICE_FIRST_HORSE_BETS:
                horseStartingPrices = service.getJoinFirstHorsesBet();

                result.put(responseParamResult, new JSONArray(horseStartingPrices));
                break;
            case GET_HORSE_STARTING_PRICE_SECOND_HORSE_BETS:
                horseStartingPrices = service.getJoinSecondHorsesBet();

                result.put(responseParamResult, new JSONArray(horseStartingPrices));
                break;
            case GET_BY_RACE_ID:
                JSONObject json = new JSONObject(getParam(request));
                long raceId = Long.parseLong(json.get(requestParamRaceId).toString());

                horseStartingPrices = service.getByRaceId(raceId);

                result.put(responseParamResult, new JSONArray(horseStartingPrices));
                break;
            case GET_COUNT_BY_RACE_ID:
                json = new JSONObject(getParam(request));
                raceId = Long.parseLong(json.get(requestParamRaceId).toString());

                Integer countRow = service.getCountByRaceId(raceId);

                if (countRow != null) {
                    result.put(responseParamResult, countRow);
                } else {
                    result.put(responseParamResult, requestParamNoResult);
                }
                break;
            case GET_BY_ID:
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
