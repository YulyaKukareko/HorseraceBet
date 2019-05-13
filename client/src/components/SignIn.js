import React, {Component} from 'react';
import '../resources/css/signin.css';
import axios from 'axios';
import Config from '../config/Config';
import {Link, Redirect} from 'react-router-dom';
import {withTranslation} from "react-i18next";
import LanguageSelect from "./template/LanguageSelect";
import getLocalizationErrorMessage from '../common/APIUtils';

class SignIn extends Component {

    constructor(props) {
        super(props);

        this.state = {
            email: "",
            password: "",
            userRole: "",
            ajaxError: ""
        };
        this.props.validator.hideMessages();
        this.props.validator.purgeFields();
    }

    componentWillMount() {
        document.body.className = "body_class"
    }


    handleChange = (event) => {
        this.setState({[event.target.name]: event.target.value});
    };

    handleSubmit = (event) => {
        event.preventDefault();
        this.props.validator.showMessages();

        if (this.props.validator.allValid()) {
            let json = {
                login: this.state.email,
                password: this.state.password
            };

            axios.post(Config.SIGN_IN, json)
                .then((resp) => {
                    if (resp.data.status === "success") {
                        if (resp.data.authorization === "success") {
                            this.setState({userRole: resp.data.role.toLowerCase()});
                        } else {
                            this.setState({ajaxError: this.props.t('INCORRECT_EMAIL_OR_PASSWORD_MESSAGE')});
                        }
                    } else {
                        this.setState({ajaxError: getLocalizationErrorMessage(resp.data.errorMes)});
                    }
                });
        } else {
            this.forceUpdate();
        }
    };

    render() {
        let {t} = this.props;

        if (this.state.userRole !== "") {
            if (this.state.userRole === "user") {
                return (
                    <Redirect push to={"/auth/user"}/>
                )
            }
            if (this.state.userRole === "bookmaker") {
                return (
                    <Redirect push to={"/auth/bookmaker"}/>
                )
            }
            if (this.state.userRole === "admin") {
                return (
                    <Redirect push to={"/auth/admin"}/>
                )
            }
        }

        return (
            <React.Fragment>
                <div className="betuslogo"/>
                <div className={"select_language_sign_in"}>
                    <LanguageSelect defaultFlag={this.props.defaultFlag}
                                    changeDefaultFlag={this.props.changeDefaultFlag}/>
                </div>
                <form onSubmit={this.handleSubmit}>
                    <div className="login">
                        <div className="login_top">
                            <img src={require("../resources/img/logo.png")} alt="bet usracing" className="login_logo"/>
                            <h1 className="login_title">{t('SIGN_IN_IN_ENTER_FIELDS')}</h1>
                            <div className="login_field">
                                <input placeholder={t('EMAIL')} type="text" name="email"
                                       value={this.state.login}
                                       onChange={this.handleChange}/>

                                {
                                    this.props.validator.message(t('EMAIL'), this.state.email, 'required|email')
                                }
                            </div>
                            <div className="login_field">
                                <input placeholder={t('PASSWORD')} type="password" name="password"
                                       value={this.state.password}
                                       onChange={this.handleChange}/>

                                {
                                    this.props.validator.message(t('PASSWORD'), this.state.password, 'required|min:8')
                                }
                            </div>
                        </div>

                        {
                            this.props.validator.messageWhenPresent(this.state.ajaxError, {
                                element: message => <div className="ajax_error">{message}</div>
                            })}
                        <div className="login_bottom">
                            <button className="login_btn login_btn-blue">
                                {t('LOGIN')}
                            </button>
                            <span className="login_or">
                            <span className="text">
                                {t('OR')}
                            </span>
                        </span>
                            <span className="login_text">
                                {t('SIGN_IN_HAVE_ACCOUNT')}
                            </span>
                            <Link to="/signup"
                                  className="login_btn login_btn-red">{t('SIGN_IN_JOIN_NOW')}</Link>
                        </div>
                    </div>
                </form>
            </React.Fragment>
        );
    }
}

export default withTranslation('translation')(SignIn);
