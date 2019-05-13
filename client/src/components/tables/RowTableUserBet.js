import React, {Component} from 'react';
import ChooseButton from "./template/ChooseButton";
import {withTranslation} from "react-i18next";

class RowTableUserBet extends Component {

    constructor(props) {
        super(props);

        this.state = {
            betMoney: ""
        }
    }

    handleChange = (event) => {
        this.setState({[event.target.name]: event.target.value});
    };

    render() {
        let {t} = this.props;

        return (
            <tr>
                <td>
                    <input type={"text"} placeholder={t('SUM')} className="form-control" name={"betMoney"}
                           value={this.state.betMoney} onChange={this.handleChange}/>
                </td>
                <td>{this.props.firstHorse.name}</td>
                <td>{this.props.firstHorse.jockey}</td>
                <td>{this.props.firstHorse.trainer}</td>
                <td>{this.props.firstHorse.weight}</td>
                {
                    (this.props.value.type === "OPPOSITE") || (this.props.value.type === "EXACTA") ?
                        <React.Fragment>
                            <td>{this.props.secondHorse.name}</td>
                            <td>{this.props.secondHorse.jockey}</td>
                            <td>{this.props.secondHorse.trainer}</td>
                            <td>{this.props.secondHorse.weight}</td>
                        </React.Fragment> : null
                }
                <td>{this.props.value.coefficient}</td>
                <ChooseButton chooseItem={this.props.chooseItem} value={{
                    betId: this.props.value.id,
                    coefficient: this.props.value.coefficient,
                    sp: this.props.startingPrice.sp,
                    betMoney: this.state.betMoney,
                }} content={t('MAKE_BET')}/>
            </tr>
        )
    }
}

export default withTranslation('translation')(RowTableUserBet);