import SimpleReactValidator from "simple-react-validator";

let createValidator = (translate) => {
    return new SimpleReactValidator({
        className: 'error',
        messages: {
            min: translate('REQUIRED_LENGTH'),
            numeric: translate('NUMERIC'),
            currency: translate('CURRENCY'),
            after_or_equal: translate('AFTER_DATE_MES'),
            required: translate('REQUIRED_MES'),
            email: translate('INVALID_LOGIN')
        }
    });
};

export {createValidator}