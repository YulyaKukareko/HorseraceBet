import React from 'react';
import '../resources/css/signup.css';
import {Link, Redirect} from "react-router-dom";
import Config from '../config/Config.js';
import {withTranslation} from "react-i18next";
import LanguageSelect from "./template/LanguageSelect";
import axios from 'axios';
import getLocalizationErrorMessage from '../common/APIUtils';

class SignUp extends React.Component {

    constructor(props) {
        super(props);

        this.state = {
            email: '',
            firstName: '',
            lastName: '',
            country: '',
            monthBirth: '',
            dayBirth: '',
            yearBirth: '',
            password: '',
            countries: [],
            redirect: false,
            ajaxError: ""
        };
        this.props.validator.hideMessages();
        this.props.validator.purgeFields();
    };

    componentWillMount() {
        axios.get(Config.GET_COUNTRIES)
            .then((resp) => {
                this.setState({countries: resp.data.result});
            })
    }

    handleChange = (event) => {
        this.setState({[event.target.name]: event.target.value});
    };

    handleSubmit = (event) => {
        event.preventDefault();

        this.props.validator.showMessages();
        if (this.props.validator.allValid()) {
            let birthDay = new Date(this.state.yearBirth, this.state.monthBirth, this.state.dayBirth);

            if (moment().diff(moment(birthDay), 'years') >= 18) {
                let userInfo = {
                    firstName: this.state.firstName,
                    lastName: this.state.lastName,
                    countryId: this.state.country,
                    email: this.state.email,
                    password: this.state.password
                };

                axios.post(Config.SIGN_UP, userInfo)
                    .then((resp) => {
                        if (resp.data.status === "success") {
                            this.setState({redirect: true});
                        } else {
                            this.setState({ajaxError: getLocalizationErrorMessage(resp.data.errorMes)});
                        }
                    });
            } else {
                this.setState({ajaxError: this.props.t('AGE_RESTRICTION')});
            }
        } else {
            this.forceUpdate();
        }
    };

    getDates = () => {
        let days = [];

        for (let i = 1; i < 32; i++) {
            days.push(<option value={i}>{i}</option>);
        }
        return days;
    };

    getYears = () => {
        let years = [];

        for (let i = 1950; i < 2002; i++) {
            years.push(<option value={i}>{i}</option>);
        }
        return years;
    };

    render() {
        let {t} = this.props;

        if (this.state.redirect === true) {
            return (
                <Redirect push to={"/auth/user"}/>
            )
        }

        return (
            <React.Fragment>
                <main className="signup_main">
                    <div className={"select_language_sign_up"}>
                        <LanguageSelect defaultFlag={this.props.defaultFlag}
                                        changeDefaultFlag={this.props.changeDefaultFlag}/>
                    </div>
                    <div className="signup_header">
                        <img src={require("../resources/img/logo.png")} className="signup_logo"
                             alt="Online Horse Betting"/>
                        <h1 className="signup_title">{t('GET_STARTED')}</h1>
                        <Link to="/" className="signup_btn-login">
                            <span className="desktop">
                                {t('SIGN_IN')}
                            </span>
                        </Link>
                    </div>
                    <form className="form" onSubmit={this.handleSubmit}>
                        <section className="fields">
                            <div className="container">
                                <div className="signup_form">
                                    <div className="form_row">
                                        <div className="form_col-50">
                                            <div className="form_field">
                                                <input placeholder={t('FIRST_NAME')} type="text"
                                                       name="firstName"
                                                       value={this.state.firstName} onChange={this.handleChange}/>
                                                {
                                                    this.props.validator.message(t('FIRST_NAME'), this.state.firstName, 'required')
                                                }
                                            </div>
                                        </div>
                                        <div className="form_col-50">
                                            <div className="form_field">
                                                <input placeholder={t('LAST_NAME')} type="text"
                                                       name="lastName"
                                                       value={this.state.lastName} onChange={this.handleChange}/>

                                                {
                                                    this.props.validator.message(t('LAST_NAME'), this.state.lastName, 'required')
                                                }
                                            </div>
                                        </div>
                                    </div>
                                    <div className="form_row">
                                        <div className="form_col-50">
                                            <div className="form_field">
                                                <input placeholder={t('PASSWORD')} type="password"
                                                       name="password"
                                                       value={this.state.password} onChange={this.handleChange}/>

                                                {
                                                    this.props.validator.message(t('PASSWORD'), this.state.password, 'required|min:8')
                                                }
                                            </div>
                                        </div>
                                        <div className="form_col-50">
                                            <div className="form_field">
                                                <input placeholder={t('EMAIL')} type="text"
                                                       name="email"
                                                       value={this.state.email} onChange={this.handleChange}/>

                                                {
                                                    this.props.validator.message(t('EMAIL'), this.state.email, 'required|email')
                                                }
                                            </div>
                                        </div>
                                    </div>
                                    <div className="form_row">
                                        <div className="form_col-50">
                                            <div className="form_field">
                                                <div className="form_select">
                                                    {
                                                        this.state.countries.length !== 0 ? (
                                                            <select name="country"
                                                                    onChange={this.handleChange}
                                                                    value={this.state.country}>
                                                                <option defaultChecked={""}>{t('COUNTRY')}...</option>
                                                                {
                                                                    this.state.countries.map((value) => {
                                                                            return (
                                                                                <option
                                                                                    value={value.id}>{value.name}</option>
                                                                            )
                                                                        }
                                                                    )};
                                                            </select>) : null}
                                                </div>

                                                {
                                                    this.props.validator.message('country', this.state.country, 'required')
                                                }
                                            </div>
                                        </div>
                                    </div>
                                    <div className="form_row">
                                        <label
                                            className="form_label">{t('SIGN_UP_DATE_BIRTH')}</label>
                                        <div className="form_row form_row-birth">
                                            <div className="form_col-33-3">
                                                <div className="form_field">
                                                    <div className="form_select">
                                                        <select placeholder={t('MONTH')}
                                                                name="monthBirth" id="month"
                                                                onChange={this.handleChange}>
                                                            {t('MONTHS', {returnObjects: true}).map((value, index) => {
                                                                return (
                                                                    <option value={index}>{value}</option>
                                                                )
                                                            })};
                                                        </select>
                                                    </div>
                                                    {
                                                        this.props.validator.message(t('MONTH'), this.state.monthBirth, 'required')
                                                    }
                                                </div>
                                            </div>
                                            <div className="form_col-33-3">
                                                <div className="form_field">
                                                    <div className="form_select">
                                                        <select placeholder={t('DAY')}
                                                                name="dayBirth" id="day"
                                                                onChange={this.handleChange}>
                                                            {this.getDates()}
                                                        </select>
                                                    </div>
                                                    {
                                                        this.props.validator.message(t('DAY'), this.state.dayBirth, 'required')
                                                    }
                                                </div>
                                            </div>
                                            <div className="form_col-33-3">
                                                <div className="form_field">
                                                    <div className="form_select">
                                                        <select placeholder={t('YEAR')}
                                                                name="yearBirth" id="year"
                                                                onChange={this.handleChange}>
                                                            {this.getYears()}
                                                        </select>
                                                    </div>
                                                    {
                                                        this.props.validator.message(t('YEAR'), this.state.yearBirth, 'required')
                                                    }
                                                </div>
                                            </div>
                                            <img src={require("../resources/img/age_restrictions.png")}
                                                 className="age_restriction"/>
                                        </div>
                                    </div>
                                </div>
                                {
                                    this.props.validator.messageWhenPresent(this.state.ajaxError, {
                                        element: message => <div className="ajax_error">{message}</div>
                                    })
                                }
                            </div>
                        </section>
                        <section className="infos">
                            <div className="signup_form">
                                <div className="form_btns">
                                    <button type="submit"
                                            className="btn btn-outline-danger form_submit">{t('SIGN_UP_CREATE_ACCOUNT')}
                                    </button>
                                </div>
                            </div>
                        </section>
                    </form>
                </main>
            </React.Fragment>
        );
    }
}

export default withTranslation('translation')(SignUp);
