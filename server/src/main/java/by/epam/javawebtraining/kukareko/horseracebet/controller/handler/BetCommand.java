package by.epam.javawebtraining.kukareko.horseracebet.controller.handler;

import by.epam.javawebtraining.kukareko.horseracebet.controller.GetAction;
import by.epam.javawebtraining.kukareko.horseracebet.controller.GetParams;
import by.epam.javawebtraining.kukareko.horseracebet.service.BetService;
import by.epam.javawebtraining.kukareko.horseracebet.model.entity.*;
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

    private BetService service;

    public BetCommand() {
        service = BetService.getInstance();
    }

    @Override
    public JSONObject execute(HttpServletRequest request) {
        JSONObject result = new JSONObject();

        switch (getAction(request)) {
            case "create":
                Bet bet = new Gson().fromJson(getParam(request), Bet.class);

                addStatusResponse(result, service.save(bet));
                break;
            case "delete":
                bet = new Gson().fromJson(getParam(request), Bet.class);

                addStatusResponse(result, service.delete(bet));
                break;
            case "getAll":
                List<Bet> bets = service.getAll();

                result.put("result", new JSONArray(bets));
                break;
            case "update":
                bet = new Gson().fromJson(getParam(request), Bet.class);

                addStatusResponse(result, service.update(bet));
                break;
            case "getById":
                JSONObject json = new JSONObject(getParam(request));
                long betId = Long.parseLong(json.get("betId").toString());

                bet = service.getById(betId);

                result.put("result", new JSONObject(bet));
                break;
            case "getAllByRaceIdAndBetType":
                json = new JSONObject(getParam(request));
                HttpSession session = request.getSession();
                long userId = (long) session.getAttribute("id");
                long raceId = Long.parseLong(json.get("raceId").toString());
                BetType betType = BetType.valueOf(json.getString("betType"));

                bets = service.getByRaceIdAndBetType(raceId, betType, userId);
                result.put("result", new JSONArray(bets));
                break;
            case "getAllTypes":
                List<BetType> types = Arrays.asList(BetType.values());

                result.put("result", new JSONArray(types));
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
