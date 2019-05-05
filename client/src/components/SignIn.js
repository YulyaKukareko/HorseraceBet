import React, {Component} from 'react';
import '../resources/css/signin.css';
import axios from 'axios';
import Config from '../config/Config';
import {Link, Redirect} from 'react-router-dom';
import {withTranslation} from "react-i18next";
import LanguageSelect from "./template/LanguageSelect";

class SignIn extends Component {

    constructor(props) {
        super(props);
        this.state = {
            login: "",
            password: "",
            userRole: ""
        };
    }

    componentDidMount() {
        document.body.className = "body_class"
    }


    handleChange = (event) => {
        this.setState({[event.target.name]: event.target.value});
    };

    handleSubmit = (event) => {
        event.preventDefault();
        let json = {
            login: this.state.login,
            password: this.state.password
        };
        axios.post(Config.SIGNIN, json, {
            headers: {"Content-Type": "application/json"}
        })
            .then((resp) => {
                this.setState({userRole: resp.data.role.toLowerCase()});
            });
    };

    render() {
        const {t} = this.props;
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
            <div>
                <div className="betuslogo"/>
                <div className={"select_language_sign_in"}>
                    <LanguageSelect defaultFlag={this.props.defaultFlag}
                                    changeDefaultFlag={this.props.changeDefaultFlag}/>
                </div>
                <form onSubmit={this.handleSubmit}>
                    <div className="login">
                        <div className="login_top">
                            <img src={require("../resources/img/logo.png")} alt="bet usracing" className="login_logo"/>
                            <h1 className="login_title">{t('SIGNIN_IN_ENTER_FIELDS')}</h1>
                            <div className="login_field">
                                <input placeholder={t('LOGIN')} type="text" name="login" value={this.state.login}
                                       onChange={this.handleChange}/>
                            </div>
                            <div className="login_field">
                                <input placeholder={t('PASSWORD')} type="password" name="password"
                                       value={this.state.password}
                                       onChange={this.handleChange}/>
                            </div>
                        </div>
                        <div className="login_bottom">
                            <button className="login_btn login_btn-blue">{t('LOGIN')}</button>
                            <span className="login_or">
                            <span className="text">{t('OR')}</span>
                        </span>
                            <span className="login_text">{t('SIGINI_HAVE_ACCOUNT')}</span>
                            <Link to="/signup" className="login_btn login_btn-red">{t('SIGIN_IN_JOIN_NOW')}</Link>
                        </div>
                    </div>
                </form>
            </div>
        );
    }
}

export default withTranslation('translation')(SignIn);
