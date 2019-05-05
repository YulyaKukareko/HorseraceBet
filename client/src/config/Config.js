const BASENAME = "/server_war/ctrl/";

const Config = {
    SIGNIN: BASENAME + "auth/signin",
    SIGNUP: BASENAME + "signup",

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
    DELETE_BET: BASENAME + "bets/delete",
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

    MONTHS: ["January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"],
    BOOKMAKER_MENU_ITEMS: [{path: "/auth/bookmaker/horse", menuHeader: "Add horse"}, {
        path: "/auth/bookmaker/race",
        menuHeader: "Add race"
    },
        {path: "/auth/bookmaker/sphorse", menuHeader: "Add starting price"}, {
            path: "/auth/bookmaker/bet",
            menuHeader: "Add bet"
        }],
    ADMIN_MENU_ITEMS: [{path: "/auth/admin/result", menuHeader: "Add result"}],
    USER_MENU_ITEMS: [{path: "/auth/user/bet", menuHeader: "Bets"}, {
        path: "/auth/user/history",
        menuHeader: "History bets"
    },
        {path: "/auth/user/result", menuHeader: "Results"}],
    TABLE_EDIT_HORSE_COLUMNS: ["Name", "Jockey", "Trainer", "Weight"],
    TABLE_RACE_COLUMNS: ["Location", "Race type", "Distance", "Purse", "Time"],
    TABLE_TYPE_RACE_COLUMNS: ["Choose", "Type"],
    TABLE_SP_COLUMNS_HORSE: ["Id", "Horse name", "Jockey name"],
    TABLE_SP_COLUMNS_RACES: ["Id", "Location", "Distance"],
    TABLE_SP_COLUMNS: ["Race Id", "Horse id", "Horse name", "Race id", "Location", "Starting price"],
    TABLE_BET_AVAILABLE_HORSE_COLUMNS: ["Horse name", "Jockey", "Trainer", "Weight", "Starting price"],
    TABLE_BET_COLUMNS: ["Bet type", "Race location", "Race time", "First horse name", "Jockey", "SP", "Second horse name", "Jockey", "SP", "Coefficient"],
    TABLE_USER_BET_COLUMNS: ["Bet type", "Race location", "Date", "First horse name", "Jockey", "Second horse name", "Jockey", "SP", "Bet sum", "Coefficient", "Status"],
    TABLE_BET_SINGLE: ["Bet money", "Horse name", "Jockey", "Trainer", "Weight", "Coefficient"],
    TABLE_BET_DOUBLE: ["Bet money", "Horse name", "Jockey", "Trainer", "Weight", "Horse name", "Jockey", "Trainer", "Weight", "Coefficient"],
    TABLE_RESULT_COLUMNS: ["Place", "Horse name", "Horse jockey"],
    TABLE_RESULT_USER_COLUMNS: ["Place", "Horse name", "Jockey", "Trainer"],

    COUNT_RECORD_TABLE: 3
};

export default Config;