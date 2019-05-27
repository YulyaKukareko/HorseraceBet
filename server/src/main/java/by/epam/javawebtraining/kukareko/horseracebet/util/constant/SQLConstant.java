package by.epam.javawebtraining.kukareko.horseracebet.util.constant;

/**
 * @author Yulya Kukareko
 * @version 1.0 20 May 2019
 */
public class SQLConstant {

    public static final String NULL_VALUE = "NULL";

    // Bet entity SQL operations
    public static final String SQL_DELETE_BET = "SQL.deleteBet";
    public static final String SQL_UPDATE_BET = "SQL.updateBet";
    public static final String SQL_INSERT_BET = "SQL.insertBet";
    public static final String SQL_SELECT_BET_BY_RACE_ID_AND_BET_TYPE = "SQL.selectBetByRaceIdAndBetType";
    public static final String SQL_SELECT_BET = "SQL.selectBet";
    public static final String SQL_SELECT_BET_BY_ID = "SQL.selectBetById";

    // Country entity SQL operations
    public static final String SQL_SELECT_COUNTRY = "SQL.selectCountry";
    public static final String SQL_INSERT_COUNTRY = "SQL.insertCountry";
    public static final String SQL_UPDATE_COUNTRY = "SQL.updateCountry";
    public static final String SQL_DELETE_COUNTRY = "SQL.deleteCountry";
    public static final String SQL_SELECT_COUNTRY_BY_ID = "SQL.selectCountryById";

    // Horse entity SQL operations
    public static final String SQL_SELECT_HORSE_BY_ID = "SQL.selectHorseById";
    public static final String SQL_SELECT_HORSE = "SQL.selectHorse";
    public static final String SQL_INSERT_HORSE = "SQL.insertHorse";
    public static final String SQL_UPDATE_HORSE = "SQL.updateHorse";
    public static final String SQL_DELETE_HORSE = "SQL.deleteHorse";
    public static final String SQL_SELECT_HORSES_JOIN_HORSE_STARTING_PRICE = "SQL.selectHorsesJoinHorseStartingPrice";
    public static final String SQL_SELECT_HORSES_JOIN_STARTING_PRICE_AND_FIRST_HORSE_BET = "SQL.selectHorsesJoinStartingPriceAndFirstHorseBet";
    public static final String SQL_SELECT_HORSE_JOIN_STARTING_PRICE_AND_FIRST_HORSE_BY_SPID = "SQL.selectHorseJoinStartingPriceAndFirstHorseBySPId";
    public static final String SQL_SELECT_HORSES_JOIN_STARTING_PRICE_AND_SECOND_HORSE_BET = "SQL.selectHorsesJoinStartingPriceAndSecondHorseBet";
    public static final String SQL_SELECT_HORSES_JOIN_HORSE_STARTING_PRICE_BY_RACE_ID = "SQL.selectHorsesJoinHorseStartingPriceByRaceId";
    public static final String SQL_SELECT_HORSES_JOIN_HORSE_STARTING_PRICE_EXCLUDING_BY_RACE_ID = "SQL.selectHorsesJoinHorseStartingPriceExcludingByRaceId";

    // Horse starting price entity SQL operations
    public static final String SQL_SELECT_HORSE_STARTING_PRICE_BY_ID = "SQL.selectHorseStartingPriceById";
    public static final String SQL_SELECT_STARTING_PRICE = "SQL.selectStartingPrice";
    public static final String SQL_INSERT_HORSE_STARTING_PRICE = "SQL.insertHorseStartingPrice";
    public static final String SQL_UPDATE_HORSE_STARTING_PRICE = "SQL.updateHorseStartingPrice";
    public static final String SQL_DELETE_HORSE_STARTING_PRICE = "SQL.deleteHorseStartingPrice";
    public static final String SQL_SELECT_HORSE_STARTING_PRICES_JOIN_FIRST_HORSE_BET = "SQL.selectHorseStartingPricesJoinFirstHorseBet";
    public static final String SQL_SELECT_HORSE_STARTING_PRICES_JOIN_SECOND_HORSE_BET = "SQL.selectHorseStartingPricesJoinSecondHorseBet";
    public static final String SQL_SELECT_STARTING_PRICE_BY_RACE_ID = "SQL.selectStartingPriceByRaceId";
    public static final String SQL_SELECT_STARTING_PRICE_COUNT_BY_RACE_ID = "SQL.selectStartingPriceCountByRaceId";

    // Race entity SQL operations
    public static final String SQL_SELECT_RACE_BY_ID = "SQL.selectRaceById";
    public static final String SQL_SELECT_RACE = "SQL.selectRace";
    public static final String SQL_INSERT_RACE = "SQL.insertRace";
    public static final String SQL_UPDATE_RACE = "SQL.updateRace";
    public static final String SQL_DELETE_RACE = "SQL.deleteRace";
    public static final String SQL_SELECT_RACE_JOIN_HORSE_STARTING_PRICE = "SQL.selectRaceJoinHorseStartingPrice";
    public static final String SQL_SELECT_RACE_JOIN_BET = "SQL.selectRaceJoinBet";
    public static final String SQL_SELECT_COMPLETED_RACE = "SQL.selectCompletedRace";
    public static final String SQL_SELECT_RACE_JOIN_HORSE_STARTING_PRICE_BY_ID = "SQL.selectRaceJoinHorseStartingPriceById";
    public static final String SQL_SELECT_RACE_NOT_JOIN_RESULT = "SQL.selectRaceNotJoinResult";
    public static final String SQL_SELECT_COMPLETED_RACE_JOIN_RESULT = "SQL.selectCompletedRaceJoinResult";

    // Result entity SQL operations
    public static final String SQL_SELECT_RESULT_BY_ID = "SQL.selectResultById";
    public static final String SQL_SELECT_RESULT = "SQL.selectResult";
    public static final String SQL_INSERT_RESULT = "SQL.insertResult";
    public static final String SQL_UPDATE_RESULT = "SQL.updateResult";
    public static final String SQL_DELETE_RESULT = "SQL.deleteResult";
    public static final String SQL_SELECT_RESULT_BY_RACE_ID = "SQL.selectResultByRaceId";

    // User entity SQL operations
    public static final String SQL_SELECT_USER_BY_ID = "SQL.selectUserById";
    public static final String SQL_INSERT_USER = "SQL.insertUser";
    public static final String SQL_SELECT_USER = "SQL.selectUser";
    public static final String SQL_UPDATE_USER = "SQL.updateUser";
    public static final String SQL_DELETE_USER = "SQL.deleteUser";
    public static final String SQL_CHECK_USER_BY_LOGIN_AND_PASSWORD = "SQL.checkUserByLoginAndPassword";
    public static final String SQL_CHECK_EXISTS_EMAIL = "SQL.checkExistsEmail";
    public static final String SQL_ADDING_USER_BALANCE_MONEY = "SQL.addingUserBalanceMoney";

    // User bet entity SQL operations
    public static final String SQL_SELECT_USER_BET_BY_ID = "SQL.selectUserBetById";
    public static final String SQL_SELECT_USER_BET = "SQL.selectUserBet";
    public static final String SQL_INSERT_USER_BET = "SQL.insertUserBet";
    public static final String SQL_SELECT_USER_BET_BY_USER_ID = "SQL.selectUserBetByUserId";
    public static final String SQL_UPDATE_USER_BET = "SQL.updateUserBet";
    public static final String SQL_DELETE_USER_BET = "SQL.deleteUserBet";
}
