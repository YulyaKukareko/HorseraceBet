package by.epam.javawebtraining.kukareko.horseracebet.handler;

import static by.epam.javawebtraining.kukareko.horseracebet.util.constant.JSONParamConstant.*;
import static by.epam.javawebtraining.kukareko.horseracebet.util.constant.ActionConstant.*;

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
import java.util.List;

/**
 * @author Yulya Kukareko
 * @version 1.0 19 Apr 2019
 */
public class HorseCommand implements Command, GetParams, GetAction {


    private ConfigurationManager configurationManager;

    private String responseParamResult;
    private String requestParamRaceId;
    private String requestParamNoResult;
    private String requestParamId;
    private String requestStartingSP;

    private HorseService service;

    public HorseCommand() {
        this.service = HorseService.getInstance();
        this.configurationManager = ConfigurationManager.getInstance();
        this.responseParamResult = configurationManager.getProperty(CONFIG_JSON_RESULT);
        this.requestParamRaceId = configurationManager.getProperty(REQUEST_PARAM_RACE_ID);
        this.requestParamNoResult = configurationManager.getProperty(RESPONSE_PARAM_NO_RESULT);
        this.requestParamId = configurationManager.getProperty(PARAMS_ID);
        this.requestStartingSP = configurationManager.getProperty(RESPONSE_PARAM_STARTING_PRICE_ID);
    }

    @Override
    public JSONObject execute(HttpServletRequest request) throws HorseRaceBetException {

        JSONObject result = new JSONObject();

        switch (getAction(request)) {
            case CREATE:
                Horse horse;
                horse = new Gson().fromJson(getParam(request), Horse.class);

                service.save(horse);
                break;
            case DELETE:
                horse = new Gson().fromJson(getParam(request), Horse.class);

                service.delete(horse);
                break;
            case UPDATE:
                horse = new Gson().fromJson(getParam(request), Horse.class);

                service.update(horse);
                break;
            case GET_ALL:
                List<Horse> horses = service.getAll();

                result.put(responseParamResult, new JSONArray(horses));
                break;
            case GET_HORSES_STARTING_PRICES:
                horses = service.getJoinHorseStartingPrice();

                result.put(responseParamResult, new JSONArray(horses));
                break;
            case GET_HORSES_STARTING_PRICES_EXCLUDING_BY_RACE_ID:
                JSONObject json = new JSONObject(getParam(request));
                long raceId = Long.parseLong(json.get(requestParamRaceId).toString());

                horses = service.getJoinHorseStartingPriceExcludingByRaceId(raceId);

                result.put(responseParamResult, new JSONArray(horses));
                break;
            case GET_HORSES_STARTING_PRICES_BY_RACE_ID:
                json = new JSONObject(getParam(request));
                raceId = Long.parseLong(json.get(requestParamRaceId).toString());

                horses = service.getJoinHorseStartingPriceByRaceId(raceId);

                result.put(responseParamResult, new JSONArray(horses));
                break;
            case GET_HORSES_FIRST_BETS:
                horses = service.getJoinFirstBetAndHorseStartingPrice();

                result.put(responseParamResult, new JSONArray(horses));
                break;
            case GET_HORSES_SECOND_BETS:
                horses = service.getJoinSecondBetAndHorseStartingPrice();

                result.put(responseParamResult, new JSONArray(horses));
                break;
            case GET_HORSES_STARTING_PRICES_BY_STARTING_PRICE_ID:

                json = new JSONObject(getParam(request));
                long startingPriceId = Long.parseLong(json.get(requestStartingSP).toString());

                horse = service.getJoinHorseStartingPriceByStartingPriceId(startingPriceId);

                if (horse != null) {
                    result.put(responseParamResult, new JSONObject(horse));
                } else {
                    result.put(responseParamResult, requestParamNoResult);
                }
                break;
            case GET_BY_ID:

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
