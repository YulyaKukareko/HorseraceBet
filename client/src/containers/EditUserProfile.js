import React, {Component} from 'react';
import Config from "../config/Config";
import axios from 'axios';
import EditProfile from '../components/editors/EditProfile';
import {withTranslation} from "react-i18next";
import getLocalizationErrorMessage from '../common/APIUtils';

class EditUserProfile extends Component {

    constructor(props) {
        super(props);

        this.state = {
            firstName: props.user.firstName, lastName: props.user.lastName, login: props.user.email,
            balance: props.user.balance, countries: [], ajaxError: "", countryId: props.user.countryId, translate: this.props.t
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

    handleSubmit = () => {
        this.props.validator.showMessages();

        if (this.props.validator.allValid()) {
            let updateUser = {
                email: this.state.login,
                firstName: this.state.firstName,
                lastName: this.state.lastName,
                countryId: this.state.countryId,
                balance: this.props.user.balance
            };

            axios.post(Config.UPDATE_USER, updateUser)
                .then((resp) => {
                    if (resp.data.status === "failed") {
                        this.setState({ajaxError: getLocalizationErrorMessage(this.state.translate, resp.data.errorMes)});
                    } else {
                        this.setState({ajaxError: ""});
                    }
                });
        } else {
            this.forceUpdate();
        }
    };

    render() {
        const t = this.state.translate;

        return (
            <React.Fragment>
                <div className="container">
                    <h1>{t('EDIT_PROFILE')}</h1>
                    <hr/>
                    <div className="row">
                        <div className="col-md-9 personal-info edit_profile">
                            <h3>{t('PERSONAL_INFO')}</h3>

                            <form className="form-horizontal" role="form" onSubmit={this.handleSubmit}>
                                <label className="col-lg-3 control-label">{t('COUNTRY')}</label>
                                {this.state.countries.length !== 0 && <select name="countryId"
                                                                              onChange={this.handleChange}
                                                                              className={"form-control user_edit_country_select"}
                                                                              value={this.state.countryId}>
                                    {this.state.countries.map((value) => {
                                        return (
                                            <option value={value.id}>{value.name}</option>
                                        )
                                    })};
                                </select>}
                                <EditProfile
                                    data={[{
                                        placeholder: t('FIRST_NAME'),
                                        name: "firstName",
                                        editField: this.state.firstName
                                    },
                                        {
                                            placeholder: t('LAST_NAME'),
                                            name: "lastName",
                                            editField: this.state.lastName
                                        },
                                        {
                                            placeholder: t('EMAIL'), name: "login", editField: this.state.login,
                                            disabled: true
                                        }]}
                                    handleChange={this.handleChange} contentButton={t('SAVE_CHANGES')}/>
                            </form>
                            {this.props.validator.messageWhenPresent(this.state.ajaxError, {
                                element: message => <div className="ajax_error">{message}</div>
                            })}
                        </div>
                    </div>
                </div>
                <hr/>
            </React.Fragment>
        )
    }
}

export default withTranslation('translation')(EditUserProfile);