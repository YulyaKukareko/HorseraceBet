package by.epam.javawebtraining.kukareko.horseracebet.handler;

import static by.epam.javawebtraining.kukareko.horseracebet.util.constant.ActionConstant.*;
import static by.epam.javawebtraining.kukareko.horseracebet.util.constant.JSONParamConstant.*;

import by.epam.javawebtraining.kukareko.horseracebet.controller.GetAction;
import by.epam.javawebtraining.kukareko.horseracebet.controller.GetParams;
import by.epam.javawebtraining.kukareko.horseracebet.model.entity.Country;
import by.epam.javawebtraining.kukareko.horseracebet.model.exception.HorseRaceBetException;
import by.epam.javawebtraining.kukareko.horseracebet.service.CountryService;
import by.epam.javawebtraining.kukareko.horseracebet.util.ConfigurationManager;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author Yulya Kukareko
 * @version 1.0 07 May 2019
 */
public class CountryCommand implements Command, GetAction, GetParams {

    private ConfigurationManager configurationManager;

    private String responseParamResult;
    private String requestParamId;

    private CountryService service;

    public CountryCommand() {
        this.service = CountryService.getInstance();
        this.configurationManager = ConfigurationManager.getInstance();
        this.responseParamResult = configurationManager.getProperty(CONFIG_JSON_RESULT);
        this.requestParamId = configurationManager.getProperty(PARAMS_ID);
    }

    @Override
    public JSONObject execute(HttpServletRequest request) throws HorseRaceBetException {
        JSONObject result = new JSONObject();
        switch (getAction(request)) {
            case GET_ALL:
                List<Country> countries = service.getAll();

                result.put(responseParamResult, new JSONArray(countries));
                break;
            case GET_BY_ID:
                JSONObject json = new JSONObject(getParam(request));
                long countryId = Long.parseLong(json.get(requestParamId).toString());
                Country country = service.getById(countryId);

                result.put(responseParamResult, new JSONObject(country));
                break;
        }

        return result;
    }
}
