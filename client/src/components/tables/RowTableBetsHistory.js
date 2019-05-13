import React, {Component} from 'react';

class RowTableBetsHistory extends Component {

    constructor(props) {
        super(props);
    }

    render() {
        let formattedTime = this.props.bet.type !== "-" ? this.props.race.time.substring(0, this.props.race.time.length - 5) : "-";
        let formattedBetType = this.props.bet.type !== "-" ? this.props.bet.type.charAt(0) + this.props.bet.type.slice(1).toLowerCase() : "-";

        return (
            <tr>
                <td>{formattedBetType}</td>
                <td>{this.props.race.name}</td>
                <td>{this.props.country.name}</td>
                <td>{formattedTime}</td>
                <td>{this.props.firstHorse.name}</td>
                <td>{this.props.firstHorse.jockey}</td>
                <td>{this.props.secondHorse.name}   </td>
                <td>{this.props.secondHorse.jockey}</td>
                {
                    (this.props.bet.type === "OPPOSITE") || (this.props.bet.type === "EXACTA") ||
                    (this.props.value.haveSp === false) || (this.props.bet.type === "-") ?
                        <td>-</td> : <td>{this.props.horseStatingPrice.sp}</td>
                }
                <td>{this.props.value.betMoney}</td>
                <td>{this.props.value.coefficient}</td>
                <td>{this.props.value.status}</td>
            </tr>
        )
    }
}

export default RowTableBetsHistory;