import React, {Component} from 'react';
import ChooseButton from "./template/ChooseButton";

class RowTableUserBet extends Component {

    constructor(props) {
        super(props);
    }

    render() {
        return (
            <tr>
                <td><input type={"text"} placeholder={"Bet sum"} className="form-control" name={"betMoney"}
                           value={this.props.betMoney} onChange={this.props.handleChange}/></td>
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
                    sp: this.props.value.sp
                }}/>
            </tr>
        )
    }
}


export default RowTableUserBet;