package by.epam.javawebtraining.kukareko.horseracebet.controller.helper;import static by.epam.javawebtraining.kukareko.horseracebet.util.constant.HelperConstant.*;import by.epam.javawebtraining.kukareko.horseracebet.handler.*;import by.epam.javawebtraining.kukareko.horseracebet.util.ConfigurationManager;import by.epam.javawebtraining.kukareko.horseracebet.util.constant.RegexpConstant;import javax.servlet.http.HttpServletRequest;import java.util.HashMap;import java.util.Map;import java.util.concurrent.locks.ReentrantLock;import java.util.regex.Matcher;import java.util.regex.Pattern;/** * @author Yulya Kukareko * @version 1.0 13 Apr 2019 */public class RequestHelper {    private static final ReentrantLock LOCK = new ReentrantLock();    private static RequestHelper instance;    private static ConfigurationManager configurationManager;    private String defineControllerRegExp;    private Map<String, Command> commands = new HashMap<>();    private RequestHelper() {        this.configurationManager = ConfigurationManager.getInstance();        this.commands.put(AUTH_HELPER, new SignInCommand());        this.commands.put(SIGN_UP_HELPER, new SignUpCommand());        this.commands.put(HORSES_HELPER, new HorseCommand());        this.commands.put(RACES_HELPER, new RaceCommand());        this.commands.put(SP_HORSES_HELPER, new HorseStartingPriceCommand());        this.commands.put(BETS_HELPER, new BetCommand());        this.commands.put(RESULTS_HELPER, new ResultCommand());        this.commands.put(USER_BET_HELPER, new UserBetCommand());        this.commands.put(USER_HELPER, new UserCommand());        this.commands.put(COUNTRY_HELPER, new CountryCommand());        this.defineControllerRegExp = configurationManager.getProperty(RegexpConstant.DEFINE_CONTROLLER_REG_EXP);    }    public static RequestHelper getInstance() {        if (instance == null) {            LOCK.lock();            if (instance == null) {                instance = new RequestHelper();            }            LOCK.unlock();        }        return instance;    }    public Command getCommand(HttpServletRequest request) {        Pattern pattern = Pattern.compile(defineControllerRegExp);        Matcher matcher = pattern.matcher(request.getRequestURL());        Command command = null;        if (matcher.find()) {            command = commands.get(matcher.group(0));        }        return command;    }}