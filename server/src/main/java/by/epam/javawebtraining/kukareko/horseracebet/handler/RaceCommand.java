package by.epam.javawebtraining.kukareko.horseracebet.handler;

import static by.epam.javawebtraining.kukareko.horseracebet.util.constant.ActionConstant.*;
import static by.epam.javawebtraining.kukareko.horseracebet.util.constant.JSONParamConstant.*;

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
import java.util.Arrays;
import java.util.List;

/**
 * @author Yulya Kukareko
 * @version 1.0 19 Apr 2019
 */
public class RaceCommand implements Command, GetAction, GetParams {

    private ConfigurationManager configurationManager;

    private String responseParamResult;
    private String dataFormat;
    private String requestParamId;
    private String requestParamNoResult;
    private String requestParamRaceId;

    private RaceService service;

    public RaceCommand() {
        this.service = RaceService.getInstance();
        this.configurationManager = ConfigurationManager.getInstance();
        this.responseParamResult = configurationManager.getProperty(CONFIG_JSON_RESULT);
        this.dataFormat = configurationManager.getProperty(DATA_FORMAT);
        this.requestParamId = configurationManager.getProperty(PARAMS_ID);
        this.requestParamNoResult = configurationManager.getProperty(RESPONSE_PARAM_NO_RESULT);
        this.requestParamRaceId = configurationManager.getProperty(REQUEST_PARAM_RACE_ID);
    }

    @Override
    public JSONObject execute(HttpServletRequest request) throws HorseRaceBetException {
        JSONObject result = new JSONObject();
        Gson gson = new GsonBuilder()
                .setDateFormat(dataFormat)
                .create();

        switch (getAction(request)) {
            case CREATE:
                Race race = gson.fromJson(getParam(request), Race.class);

                service.save(race);
                break;
            case DELETE:
                race = gson.fromJson(getParam(request), Race.class);

                service.delete(race);
                break;
            case GET_ALL:
                List<Race> races = service.getAll();

                result.put(responseParamResult, new JSONArray(races));
                break;
            case UPDATE:
                race = gson.fromJson(getParam(request), Race.class);

                service.update(race);
                break;
            case GET_ALL_TYPES:
                List<RaceType> types = Arrays.asList(RaceType.values());

                result.put(responseParamResult, new JSONArray(types));
                break;
            case GET_RACE_NOT_JOIN_RESULT:
                races = service.getNotJoinResult();

                result.put(responseParamResult, new JSONArray(races));
                break;
            case GET_RACES_STARTING_PRICES:
                races = service.getJoinHorseStarting();

                result.put(responseParamResult, new JSONArray(races));
                break;
            case GET_RACES_STARTING_PRICES_BY_RACE_ID:
                JSONObject json = new JSONObject(getParam(request));
                long raceId = Long.parseLong(json.get(requestParamRaceId).toString());

                races = service.getJoinHorseStartingPriceById(raceId);

                result.put(responseParamResult, new JSONArray(races));
                break;
            case GET_RACES_BETS:
                races = service.getJoinBet();

                result.put(responseParamResult, new JSONArray(races));
                break;
            case GET_BY_ID:
                ;
                json = new JSONObject(getParam(request));
                raceId = Long.parseLong(json.get(requestParamId).toString());
                race = service.getById(raceId);

                if (race != null) {
                    result.put(responseParamResult, new JSONObject(race));
                } else {
                    result.put(responseParamResult, requestParamNoResult);
                }
                break;
            case GET_COMPLETED_RACES_IN_RESULT:
                races = service.getCompletedRacesJoinResult();

                result.put(responseParamResult, new JSONArray(races));
                break;
            case GET_COMPLETED_RACES:
                races = service.getCompletedRacesNotJoinResult();

                result.put(responseParamResult, new JSONArray(races));
                break;
        }

        return result;
    }
}
