import React from 'react';
import Config from '../config/Config';

function getLocalizationErrorMessage(translate, serverErrorMes) {
    let errorMes;

    switch (serverErrorMes) {
        case Config.TRANSACTION_ERROR:
            errorMes = translate('TRANSACTION_ERROR');
            break;
        case Config.INPUT_PARAMETERS_INCORRECT_ERROR:
            errorMes = translate('INPUT_PARAMETERS_INCORRECT_ERROR');
            break;
        case Config.DATABASE_NOT_RESPONDING_ERROR:
            errorMes = translate('DATABASE_NOT_RESPONDING_ERROR');
            break;
        case Config.INCORRECT_PASSWORD_ERROR:
            errorMes = translate('INCORRECT_PASSWORD_ERROR');
            break;
        case Config.INCORRECT_SUM_ERROR:
            errorMes = translate('INCORRECT_SUM_ERROR');
            break;
        case Config.INCORRECT_PLACE_NUMBER_ERROR:
            errorMes = translate('INCORRECT_PLACE_NUMBER_ERROR');
            break;
        case Config.INCORRECT_TIME_ERROR:
            errorMes = translate('INCORRECT_TIME_ERROR');
            break;
        case Config.DUPLICATION_EMAIL_ERROR:
            errorMes = translate('DUPLICATION_EMAIL_ERROR');
            break;
        case Config.INSUFFICIENT_FOUNDS_ERROR:
            errorMes = translate('INSUFFICIENT_FOUNDS_ERROR');
            break;
    }

    return errorMes;
}

export default getLocalizationErrorMessage;