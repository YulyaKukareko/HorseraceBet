import React, {Component} from 'react';
import EditButtons from "./template/EditButtons";

class RowTableHorseBet extends Component {

    constructor(props) {
        super(props);
    }

    render() {
        let formattedTime = this.props.race.time.substring(0, this.props.race.time.length - 5);
        let formattedBetType = this.props.value.type.charAt(0) + this.props.value.type.slice(1).toLowerCase();

        return (
            <tr>
                <td>{formattedBetType}</td>
                <td>{this.props.race.name}</td>
                <td>{formattedTime}</td>
                <td>{this.props.firstHorse.name}</td>
                <td>{this.props.firstHorse.jockey}</td>
                <td>{this.props.firstHorseStaringPrices.sp}</td>
                <td>{this.props.secondHorse.name}</td>
                <td>{this.props.secondHorse.jockey}</td>
                <td>{this.props.secondHorseStaringPrices.sp}</td>
                <td>{this.props.value.coefficient}</td>
                <EditButtons disableDeleted={true} value={{
                    id: this.props.value.id,
                    type: this.props.value.type,
                    currentRace: {name: this.props.race.name, time: this.props.race.time, id: this.props.race.id},
                    firstHorseName: this.props.firstHorse.name,
                    firstHorseJockey: this.props.firstHorse.jockey,
                    firstHorseSP: this.props.firstHorseStaringPrices.sp,
                    secondHorseName: this.props.secondHorse.name,
                    secondHorseJockey: this.props.secondHorse.jockey,
                    secondHorseSP: this.props.secondHorseStaringPrices.sp,
                    coefficient: this.props.value.coefficient,
                    firstHorseStartingPriceId: this.props.value.firstStartingPriceHorseId,
                    secondHorseStartingPriceId: this.props.value.secondStartingPriceHorseId
                }} editItem={this.props.editItem} deleteItem={this.props.deleteItem}/>
            </tr>
        )
    }
}

export default RowTableHorseBet;