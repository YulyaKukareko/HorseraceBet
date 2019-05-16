package by.epam.javawebtraining.kukareko.horseracebet.controller.handler;

import by.epam.javawebtraining.kukareko.horseracebet.controller.GetAction;
import by.epam.javawebtraining.kukareko.horseracebet.controller.GetParams;
import by.epam.javawebtraining.kukareko.horseracebet.model.entity.Country;
import by.epam.javawebtraining.kukareko.horseracebet.model.exception.HorseRaceBetException;
import by.epam.javawebtraining.kukareko.horseracebet.service.CountryService;
import by.epam.javawebtraining.kukareko.horseracebet.util.ConfigurationManager;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author Yulya Kukareko
 * @version 1.0 07 May 2019
 */
public class CountryCommand implements Command, GetAction, GetParams {

    private static ConfigurationManager configurationManager;

    private CountryService service;

    public CountryCommand() {
        service = CountryService.getInstance();
        configurationManager = ConfigurationManager.getInstance();
    }

    @Override
    public JSONObject execute(HttpServletRequest request) throws HorseRaceBetException {
        String responseParamResult = configurationManager.getProperty("configJSON.result");

        JSONObject result = new JSONObject();
        switch (getAction(request)) {
            case "getAll":
                List<Country> countries = service.getAll();

                result.put(responseParamResult, new JSONArray(countries));
                break;
            case "getById":
                String requestParamId = configurationManager.getProperty("params.id");

                JSONObject json = new JSONObject(getParam(request));
                long countryId = Long.parseLong(json.get(requestParamId).toString());
                Country country = service.getById(countryId);

                result.put(responseParamResult, new JSONObject(country));
                break;
        }

        return result;
    }
}
