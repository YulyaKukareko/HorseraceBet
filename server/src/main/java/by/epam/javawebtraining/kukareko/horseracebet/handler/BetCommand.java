package by.epam.javawebtraining.kukareko.horseracebet.controller.handler;

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

    private ConfigurationManager configurationManager;

    private BetService service;

    public BetCommand() {
        this.responseParamResult = configurationManager.getProperty("configJSON.result");
        service = BetService.getInstance();
        configurationManager = ConfigurationManager.getInstance();
    }

    @Override
    public JSONObject execute(HttpServletRequest request) throws HorseRaceBetException {
        String responseParamResult = configurationManager.getProperty("configJSON.result");

        JSONObject result = new JSONObject();

        switch (getAction(request)) {
            case "create":
                Bet bet = new Gson().fromJson(getParam(request), Bet.class);

                service.save(bet);
                break;
            case "getAll":
                List<Bet> bets = service.getAll();

                result.put(responseParamResult, new JSONArray(bets));
                break;
            case "update":
                bet = new Gson().fromJson(getParam(request), Bet.class);

                service.update(bet);
                break;
            case "getById":
                String betIdParam = configurationManager.getProperty("requestParam.betId");

                JSONObject json = new JSONObject(getParam(request));
                long betId = Long.parseLong(json.get(betIdParam).toString());

                bet = service.getById(betId);

                result.put(responseParamResult, new JSONObject(bet));
                break;
            case "getAllByRaceIdAndBetType":
                String requestParamId = configurationManager.getProperty("params.id");
                String requestParamRaceId = configurationManager.getProperty("requestParam.raceId");
                String requestParamBetType = configurationManager.getProperty("requestParam.betType");

                json = new JSONObject(getParam(request));
                HttpSession session = request.getSession();

                long userId = (long) session.getAttribute(requestParamId);
                long raceId = Long.parseLong(json.get(requestParamRaceId).toString());
                BetType betType = BetType.valueOf(json.getString(requestParamBetType));

                bets = service.getByRaceIdAndBetType(raceId, betType, userId);
                result.put(responseParamResult, new JSONArray(bets));
                break;
            case "getAllTypes":
                List<BetType> types = Arrays.asList(BetType.values());

                result.put(responseParamResult, new JSONArray(types));
                break;
        }

        return result;
    }
}
