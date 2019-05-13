const BASENAME = "/server_war/api/";

const Config = {

    SIGN_IN: BASENAME + "auth/signin",
    SIGN_UP: BASENAME + "signup/ ",

    GET_COUNTRIES: BASENAME + "country/getAll",
    GET_COUNTRY_BY_ID: BASENAME + "country/getById",

    GET_HORSES: BASENAME + "horses/getAll",
    GET_HORSE_BY_ID: BASENAME + "horses/getById",
    DELETE_HORSE: BASENAME + "horses/delete",
    CREATE_HORSE: BASENAME + "horses/create",
    UPDATE_HORSE: BASENAME + "horses/update",

    CREATE_RACE: BASENAME + "races/create",
    UPDATE_RACE: BASENAME + "races/update",
    DELETE_RACE: BASENAME + "races/delete",
    GET_RACES_SP: BASENAME + "races/getRacesStartingPrices",
    GET_COMPLETED_RACES: BASENAME + "races/getCompletedRaces",
    GET_RACES_BETS: BASENAME + "races/getRacesBets",
    GET_RACES: BASENAME + "races/getAll",
    GET_RACES_EXCLUDING_RESULT: BASENAME + "races/getRaceNotJoinResult",
    GET_RACE_BY_ID: BASENAME + "races/getById",
    GET_RACE_TYPES: BASENAME + "races/getAllTypes",
    GET_RACE_IN_RESULT: BASENAME + "races/getCompletedRacesInResult",

    GET_CONT_PLACES: BASENAME + "sphorses/getCountByRaceId",
    GET_SP: BASENAME + "sphorses/getAll",
    GET_SP_BY_ID: BASENAME + "sphorses/getById",
    GET_SP_BY_RACE_ID: BASENAME + "sphorses/getByRaceId",
    DELETE_SP: BASENAME + "sphorses/delete",
    UPDATE_SP: BASENAME + "sphorses/update",
    CREATE_SP: BASENAME + "sphorses/create",
    GET_SP_FIRST_HORSES_BETS: BASENAME + "sphorses/getHorseStartingPriceFirstHorseBets",
    GET_SP_SECOND_HORSES_BETS: BASENAME + "sphorses/getHorseStartingPriceSecondHorseBets",

    GET_HORSES_SP: BASENAME + "horses/getHorsesStartingPrices",
    GET_HORSES_BY_SP_ID: BASENAME + "horses/getHorsesStartingPricesByStartingPriceId",
    GET_HORSES_SP_BY_RACE_ID: BASENAME + "horses/getHorsesStartingPricesByRaceId",
    GET_HORSES_BY_EXCLUDING_RACE_ID: BASENAME + "horses/getHorsesStartingPricesExcludingByRaceId",
    GET_FIRST_HORSES_BETS: BASENAME + "horses/getHorsesFirstBets",
    GET_SECOND_HORSES_BETS: BASENAME + "horses/getHorsesSecondBets",

    UPDATE_BET: BASENAME + "bets/update",
    GET_BETS_TYPE: BASENAME + "bets/getAllTypes",
    GET_BETS: BASENAME + "bets/getAll",
    GET_BET_BY_ID: BASENAME + "bets/getById",
    GET_BETS_BY_RACE_ID_AND_BET_TYPES: BASENAME + "bets/getAllByRaceIdAndBetType",
    CREATE_BET: BASENAME + "bets/create",

    SAVE_RESULT_ROW: BASENAME + "results/create",
    GET_RESULTS_BY_RACE_ID: BASENAME + "results/getByRaceId",

    CREATE_USER_BET: BASENAME + "userbet/create",
    GET_USER_BET: BASENAME + "userbet/getByUserId",

    GET_USER_BY_ID: BASENAME + "user/getUserById",
    UPDATE_USER: BASENAME + "user/update",
    USER_GET_ALL: BASENAME + "user/getAll",
    USER_ADDING_BALANCE_MONEY: BASENAME + "user/updateBalance",

    BET_TYPES: ["Win", "Place", "Show", "Opposite", "Exacta"],
    TRANSACTION_ERROR: "Transaction not completed. Try again",
    INPUT_PARAMETERS_INCORRECT_ERROR: "The value of the entered parameters is incorrect",
    DATABASE_NOT_RESPONDING_ERROR: "Database not responding. Try again later",
    INCORRECT_EMAIL_ERROR: "Incorrect e-mail",
    INCORRECT_PASSWORD_ERROR: "Incorrect password",
    INCORRECT_COEFFICIENT_ERROR: "Incorrect coefficient",
    INCORRECT_SUM_ERROR: "Incorrect sum",
    INCORRECT_PLACE_NUMBER_ERROR: "Incorrect place number",
    INCORRECT_TIME_ERROR: "Incorrect time",
    DUPLICATION_EMAIL_ERROR: "This email is already taken",
    INSUFFICIENT_FOUNDS_ERROR: "Insufficient funds!",

    COUNT_RECORD_TABLE: 3,
    INITIAL_PAGINATION_STEP: 1
};

export default Config;