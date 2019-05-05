import React, {Component} from 'react';
import Config from "../config/Config";
import axios from 'axios';
import EditProfile from '../components/editors/EditProfile';
import {withTranslation} from "react-i18next";

class EditUserProfile extends Component {

    constructor(props) {
        super(props);
        this.state = {
            firstName: props.user.firstName,
            lastName: props.user.lastName,
            login: props.user.login,
            country: props.user.country,
            password: props.user.password,
            balance: props.user.balance
        }
    }

    handleChange = (event) => {
        this.setState({[event.target.name]: event.target.value});
    };

    handleSubmit = () => {
        let updateUser = {
            login: this.state.login,
            firstName: this.state.firstName,
            lastName: this.state.lastName,
            country: this.state.country,
            balance: this.props.user.balance,
            password: this.state.password
        };

        axios.post(Config.UPDATE_USER, updateUser);
    };

    render() {
        const {t} = this.props;
        return (
            <React.Fragment>
                <div className="container">
                    <h1>{t('EDIT_PROFILE')}</h1>
                    <hr/>
                    <div className="row">
                        <div className="col-md-9 personal-info edit_profile">
                            <h3>Personal info</h3>

                            <form className="form-horizontal" role="form" onSubmit={this.handleSubmit}>
                                <EditProfile
                                    data={[{
                                        placeholder: t('FIRST_NAME'),
                                        name: "firstName",
                                        editField: this.state.firstName
                                    },
                                        {placeholder: t('LAST_NAME'), name: "lastName", editField: this.state.lastName},
                                        {placeholder: t('LOGIN'), name: "login", editField: this.state.login},
                                        {placeholder: t('COUNTRY'), name: "country", editField: this.state.country},
                                        {placeholder: t('PASSWORD'), name: "password", editField: this.state.password}]}
                                    handleChange={this.handleChange}/>
                            </form>
                        </div>
                    </div>
                </div>
                <hr/>
            </React.Fragment>
        )
    }
}

export default withTranslation('translation')(EditUserProfile);