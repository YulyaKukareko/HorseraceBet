import React, {Component} from 'react';
import '../resources/css/table.css'
import Table from "../components/tables/template/Table";
import Config from "../config/Config";
import axios from 'axios';
import {withTranslation} from "react-i18next";
import getLocalizationErrorMessage from '../common/APIUtils';

class EditTableUserUpdateBalance extends Component {

    constructor(props) {
        super(props);

        this.state = {
            users: [], ajaxError: "", translate: this.props.t
        };

        this.props.validator.hideMessages();
        this.props.validator.purgeFields();
    };

    componentWillMount() {
        this.getUsers();
    };

    getUsers = () => {
        axios.get(Config.USER_GET_ALL)
            .then((resp) => {
                this.setState({users: resp.data.result});
            });
    };

    saveUserBalance = (obj) => {
        this.props.validator.showMessages();
        if (this.props.validator.allValid()) {
            axios.post(Config.USER_ADDING_BALANCE_MONEY, {addingMoney: obj.addingMoney, id: obj.user.id})
                .then((resp) => {
                    if (resp.data.status === "success") {
                        this.setState({ajaxError: "", users: []});
                        this.getUsers();
                    } else {
                        this.setState({ajaxError: getLocalizationErrorMessage(this.state.translate, resp.data.errorMes)});
                    }
                });
        } else {
            this.forceUpdate();
        }
    };

    handleChange = (event) => {
        this.setState({[event.target.name]: event.target.value});
    };

    render() {
        const t = this.state.translate;

        return (
            <React.Fragment>
                <div className={"result_block"}>
                    <div className="result_sub_component">
                        <Table routerPath={"/users_balances"}
                               header={t('TABLE_UPDATE_USER_BALANCE_COLUMNS', {returnObjects: true})}
                               data={this.state.users} chooseItem={this.saveUserBalance}/>
                    </div>
                </div>
                {
                    this.props.validator.messageWhenPresent(this.state.ajaxError, {
                        element: message => <div className="ajax_error_update_user_balance">{message}</div>
                    })
                }
            </React.Fragment>
        )
    }
}

export default withTranslation('translation')(EditTableUserUpdateBalance);