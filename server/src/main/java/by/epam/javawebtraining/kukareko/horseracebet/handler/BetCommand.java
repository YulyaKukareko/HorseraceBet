package by.epam.javawebtraining.kukareko.horseracebet.handler;

import static by.epam.javawebtraining.kukareko.horseracebet.util.constant.ActionConstant.*;
import static by.epam.javawebtraining.kukareko.horseracebet.util.constant.JSONParamConstant.*;

import by.epam.javawebtraining.kukareko.horseracebet.controller.GetAction;
import by.epam.javawebtraining.kukareko.horseracebet.controller.GetParams;
import by.epam.javawebtraining.kukareko.horseracebet.model.exception.HorseRaceBetException;
import by.epam.javawebtraining.kukareko.horseracebet.service.BetService;
import by.epam.javawebtraining.kukareko.horseracebet.model.entity.*;
import by.epam.javawebtraining.kukareko.horseracebet.util.ConfigurationManager;
import com.google.gson.Gson;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.List;

/**
 * @author Yulya Kukareko
 * @version 1.0 21 Apr 2019
 */
public class BetCommand implements Command, GetAction, GetParams {

    private String responseParamResult;
    private String betIdParam;
    private String requestParamId;
    private String requestParamRaceId;
    private String requestParamBetType;

    private ConfigurationManager configurationManager;

    private BetService service;

    public BetCommand() {
        this.configurationManager = ConfigurationManager.getInstance();
        this.service = BetService.getInstance();
        this.responseParamResult = configurationManager.getProperty(CONFIG_JSON_RESULT);
        this.betIdParam = configurationManager.getProperty(REQUEST_PARAM_BET_ID);
        this.requestParamId = configurationManager.getProperty(PARAMS_ID);
        this.requestParamRaceId = configurationManager.getProperty(REQUEST_PARAM_RACE_ID);
        this.requestParamBetType = configurationManager.getProperty(REQUEST_PARAM_BET_TYPE);
    }

    @Override
    public JSONObject execute(HttpServletRequest request) throws HorseRaceBetException {
        JSONObject result = new JSONObject();

        switch (getAction(request)) {
            case CREATE:
                Bet bet = new Gson().fromJson(getParam(request), Bet.class);

                service.save(bet);
                break;
            case GET_ALL:
                List<Bet> bets = service.getAll();

                result.put(responseParamResult, new JSONArray(bets));
                break;
            case UPDATE:
                bet = new Gson().fromJson(getParam(request), Bet.class);

                service.update(bet);
                break;
            case GET_BY_ID:

                JSONObject json = new JSONObject(getParam(request));
                long betId = Long.parseLong(json.get(betIdParam).toString());

                bet = service.getById(betId);

                result.put(responseParamResult, new JSONObject(bet));
                break;
            case GET_ALL_BY_RACE_ID_AND_BET_TYPE:
                json = new JSONObject(getParam(request));
                HttpSession session = request.getSession();

                long userId = (long) session.getAttribute(requestParamId);
                long raceId = Long.parseLong(json.get(requestParamRaceId).toString());
                BetType betType = BetType.valueOf(json.getString(requestParamBetType));

                bets = service.getByRaceIdAndBetType(raceId, betType, userId);
                result.put(responseParamResult, new JSONArray(bets));
                break;
            case GET_ALL_TYPES:
                List<BetType> types = Arrays.asList(BetType.values());

                result.put(responseParamResult, new JSONArray(types));
                break;
        }

        return result;
    }
}
