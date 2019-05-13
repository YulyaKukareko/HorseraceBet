import React, {Component} from 'react';
import ChooseButton from './template/ChooseButton'
import {withTranslation} from "react-i18next";

class RowTableUserUpdateBalance extends Component {

    constructor(props) {
        super(props);

        this.state = {
            addingMoney: ""
        }
    }

    handleChange = (event) => {
        this.setState({[event.target.name]: event.target.value});
    };

    componentWillReceiveProps(nextProps, nextContext) {
        this.setState({addingMoney: 0});
    }

    render() {
        let {t} = this.props;

        return (
            <tr>
                <td>{this.props.value.id}</td>
                <td>{this.props.value.email}</td>
                <td>{this.props.value.firstName}</td>
                <td>{this.props.value.lastName}</td>
                <td>{this.props.value.balance}</td>
                <td><input type={"text"} placeholder={t('SUM')} className="form-control"
                           name={"addingMoney"} value={this.state.addingMoney} onChange={this.handleChange}/></td>
                <td><ChooseButton chooseItem={this.props.chooseItem} content={t('UPDATE_BALANCE')}
                                  value={{user: this.props.value, addingMoney: this.state.addingMoney}}/></td>
            </tr>
        )
    }
}

export default withTranslation('translation')(RowTableUserUpdateBalance);
