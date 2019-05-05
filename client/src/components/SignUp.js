import React from 'react';
import '../resources/css/signup.css';
import {Link} from "react-router-dom";
import Config from '../config/Config.js';
import axios from 'axios';
import {withTranslation} from "react-i18next";
import LanguageSelect from "./template/LanguageSelect";

class SignUp extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            login: '',
            firstName: '',
            lastName: '',
            country: '',
            monthBirth: '',
            dayBirth: '',
            yearBirth: '',
            password: ''
        };
    };

    handleChange = (event) => {
        this.setState({[event.target.name]: event.target.value});
    };

    handleSubmit = (event) => {
        event.preventDefault();
        let json = {
            firstName: this.state.firstName,
            lastName: this.state.lastName,
            country: this.state.country,
            birthDate: new Date(this.state.yearBirth, this.state.monthBirth, this.state.dayBirth),
            login: this.state.login,
            password: this.state.password
        };
        axios.post(Config.SIGNUP_URL, json);
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
        const {t} = this.props;
        return (
            <div>
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
                            <span className="desktop">{t('SIGNIN')}</span>
                        </Link>
                    </div>
                    <form className="form" onSubmit={this.handleSubmit}>
                        <section className="fields">
                            <div className="container">
                                <div className="signup_form">
                                    <div className="form_row">
                                        <div className="form_col-50">
                                            <div className="form_field">
                                                <input placeholder={t('FIRST_NAME')} type="text" name="firstName"
                                                       value={this.state.firstName} onChange={this.handleChange}/>
                                            </div>
                                        </div>
                                        <div className="form_col-50">
                                            <div className="form_field">
                                                <input placeholder={t('LAST_NAME')} type="text" name="lastName"
                                                       value={this.state.lastName} onChange={this.handleChange}/>
                                            </div>
                                        </div>
                                    </div>
                                    <div className="form_row">
                                        <div className="form_col-50">
                                            <div className="form_field">
                                                <input placeholder={t('PASSWORD')} type="password" name="password"
                                                       value={this.state.password} onChange={this.handleChange}/>
                                            </div>
                                        </div>
                                        <div className="form_col-50">
                                            <div className="form_field">
                                                <input placeholder={t('LOGIN')} type="text" name="login"
                                                       value={this.state.login} onChange={this.handleChange}/>
                                            </div>
                                        </div>
                                    </div>
                                    <div className="form_row">
                                        <div className="form_col-50">
                                            <div className="form_field">
                                                <div>
                                                    <input placeholder={t('COUNTRY')} type="text" name="country"
                                                           value={this.state.country} onChange={this.handleChange}/>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div className="form_row">
                                        <label className="form_label">{t('SIGINUP_DATE_BIRTH')}</label>
                                        <div className="form_row form_row-birth">
                                            <div className="form_col-33-3">
                                                <div className="form_field">
                                                    <div className="form_select">
                                                        <select placeholder={t('MONTH')} name="monthBirth" id="month"
                                                                onSelect={this.handleChange}>
                                                            {t('MONTHS', {returnObjects: true}).map((value) => {
                                                                return (
                                                                    <option value={value}>{value}</option>
                                                                )
                                                            })};
                                                        </select>
                                                    </div>
                                                </div>
                                            </div>
                                            <div className="form_col-33-3">
                                                <div className="form_field">
                                                    <div className="form_select">
                                                        <select placeholder={t('DAY')} name="dayBirth" id="day"
                                                                onSelect={this.handleChange}>
                                                            {this.getDates()}
                                                        </select>
                                                    </div>
                                                </div>
                                            </div>
                                            <div className="form_col-33-3">
                                                <div className="form_field">
                                                    <div className="form_select">
                                                        <select placeholder={t('YEAR')} name="yearBirth" id="year"
                                                                onSelect={this.handleChange}>
                                                            {this.getYears()}
                                                        </select>
                                                    </div>
                                                </div>
                                            </div>
                                            <img src={require("../resources/img/age_restrictions.png")}
                                                 className="age_restriction"/>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </section>
                        <section className="infos">
                            <div className="signup_form">
                                <div className="form_btns">
                                    <button type="submit"
                                            className="btn btn-outline-danger form_submit">{t('SIGNUP_CREATE_ACCOUNT')}
                                    </button>
                                </div>
                            </div>
                        </section>
                    </form>
                </main>
            </div>
        );
    }
}

export default withTranslation('translation')(SignUp);
