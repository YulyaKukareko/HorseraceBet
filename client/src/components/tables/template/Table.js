import React, {Component} from 'react';
import '../../../resources/css/table.css'
import Header from '../template/Header';
import RowTableHorse from '../RowTableHorse';
import RowTableRace from '../RowTableRace'
import RowTableSPHorse from '../RowTableSPHorse';
import RowTableSPRace from '../RowTableSPRace';
import RowTableSP from '../RowTableSP';
import RowTableHorseBet from '../RowTableHorseBet';
import RowTableBet from '../RowTableBetBookmaker';
import RowTableUserBet from "../RowTableUserBet";
import RowTableBetsHistory from "../RowTableBetsHistory";
import RowTableUserResult from "../RowTableUserResult";
import Config from "../../../config/Config";

class Table extends Component {

    constructor(props) {
        super(props);
        debugger;
        this.state = {
            currentData: props.data.length !== 0 ? props.data.slice(0, Config.COUNT_RECORD_TABLE) : [],
            currentStep: 1
        }
    }

    changeStep = (step) => {
        this.setState({currentStep: step});
        this.setState({currentData: this.props.data.slice(Config.COUNT_RECORD_TABLE * (step - 1), Config.COUNT_RECORD_TABLE * step)});
        debugger;
    };

    getSteps = () => {
        let steps = [];
        for (let i = 1; i < (this.props.data.length / Config.COUNT_RECORD_TABLE) + 1; i++) {
            steps.push(<li className="page-item"><a className="page-link" onClick={() => this.changeStep(i)}
                                                    href="#">{i}</a></li>);
        }
        return steps;
    };

    initialState = (secondHorseIndex, indexUserBetHorses, value, currentIndex) => {
        let arr = [];
        if ((value.type === "OPPOSITE") || (value.type === "EXACTA")) {
            secondHorseIndex += 1;
        }
        if (this.props.bets && this.props.bets[currentIndex]) {
            indexUserBetHorses += 1;
        }
        arr.push(secondHorseIndex);
        arr.push(indexUserBetHorses);
        return arr;
    };

    componentWillReceiveProps(nextProps, nextContext) {
        this.setState({currentData: nextProps.data.slice(Config.COUNT_RECORD_TABLE * (this.state.currentStep - 1), Config.COUNT_RECORD_TABLE * this.state.currentStep)});
    }

    render() {
        let currentIndex = Config.COUNT_RECORD_TABLE * (this.state.currentStep - 1) - 1;
        let secondHorseIndex = -1;
        let indexUserBetHorses = -1;
        for (let i = 0; i < Config.COUNT_RECORD_TABLE * (this.state.currentStep - 1); i++) {
            let initStateArr = this.initialState(secondHorseIndex, indexUserBetHorses, this.props.data[i], i);
            secondHorseIndex = initStateArr[0];
            indexUserBetHorses = initStateArr[1];
        }
        return (
            <React.Fragment>
                <table className="table table-striped table-invers">
                    <thead>
                    <Header header={this.props.header}/>
                    </thead>
                    <tbody>
                    {this.state.currentData && this.state.currentData.map((value) => {
                        currentIndex++;
                        let initStateArr = this.initialState(secondHorseIndex, indexUserBetHorses, value, currentIndex);
                        secondHorseIndex = initStateArr[0];
                        indexUserBetHorses = initStateArr[1];
                        return (
                            <React.Fragment>
                                {this.props.routerPath === "/horses" ?
                                    <RowTableHorse value={value} editItem={this.props.editItem}
                                                   deleteItem={this.props.deleteItem}/> : null}

                                {this.props.routerPath === "/races" ?
                                    <RowTableRace value={value} editItem={this.props.editItem}
                                                  deleteItem={this.props.deleteItem}/> : null}

                                {this.props.routerPath === "/sphorses" ?
                                    <RowTableSPHorse value={value} chooseItem={this.props.chooseItem}/> : null}

                                {this.props.routerPath === "/spraces" ?
                                    <RowTableSPRace value={value} chooseItem={this.props.chooseItem}/> : null}

                                {this.props.routerPath === "/sp" &&
                                this.props.horses &&
                                this.props.horses.length !== 0 &&
                                this.props.races &&
                                this.props.races.length !== 0 &&
                                this.props.races.length === this.props.horses.length ?
                                    <RowTableSP value={value} editItem={this.props.editItem}
                                                deleteItem={this.props.deleteItem}
                                                horse={this.props.horses[currentIndex]}
                                                race={this.props.races[currentIndex]}/> : null}

                                {this.props.routerPath === "/available_horse_bet" &&
                                this.props.horses &&
                                this.props.horses.length !== 0 &&
                                this.props.horses.length === this.props.data.length ?
                                    <RowTableHorseBet value={value} chooseItem={this.props.chooseItem}
                                                      horse={this.props.horses[currentIndex]}/> : null}

                                {this.props.routerPath === "/bet" &&
                                this.props.firstHorsesBet &&
                                this.props.firstHorsesBet.length !== 0 &&
                                this.props.firstHorseStaringPrices &&
                                this.props.firstHorseStaringPrices.length !== 0 &&
                                this.props.firstHorseStaringPrices.length === this.props.firstHorsesBet.length &&
                                this.props.firstHorsesBet.length === this.props.races.length ?
                                    <RowTableBet value={value} editItem={this.props.editItem}
                                                 deleteItem={this.props.deleteItem}
                                                 firstHorse={this.props.firstHorsesBet[currentIndex]}
                                                 secondHorse={(value.type === "OPPOSITE") || (value.type === "EXACTA") ? this.props.secondHorsesBet[secondHorseIndex] : {
                                                     name: "-",
                                                     jockey: "-"
                                                 }} race={this.props.races[currentIndex]}
                                                 firstHorseStaringPrices={this.props.firstHorseStaringPrices[currentIndex]}
                                                 secondHorseStaringPrices={(value.type === "OPPOSITE") || (value.type === "EXACTA") ? this.props.secondHorseStaringPrices[secondHorseIndex] : {sp: "-"}}/> : null}

                                {this.props.routerPath === "/bet_user" &&
                                this.props.firstHorsesBet &&
                                (this.props.firstHorsesBet.length === this.props.data.length) &&
                                (this.props.secondHorsesBet.length === this.props.data.length) ?
                                    <RowTableUserBet value={value} betMoney={this.props.betMoney}
                                                     firstHorse={this.props.firstHorsesBet[currentIndex]}
                                                     secondHorse={this.props.secondHorsesBet[secondHorseIndex]}
                                                     chooseItem={this.props.chooseItem}
                                                     handleChange={this.props.handleChange}/> : null}

                                {this.props.routerPath === "/history_bets" &&
                                this.props.bets &&
                                (this.props.bets.length === this.props.data.length) &&
                                this.props.firstHorsesBets &&
                                (this.props.firstHorsesBets.length === this.props.races.length) &&
                                this.props.horseStartingPrice.length !== 0 &&
                                (this.props.firstHorsesBets.length === this.props.horseStartingPrice.length) ?
                                    <RowTableBetsHistory value={value}
                                                         firstHorse={this.props.bets[currentIndex] ? this.props.firstHorsesBets[indexUserBetHorses] : {
                                                             name: "-",
                                                             jockey: "-"
                                                         }}
                                                         bet={this.props.bets[currentIndex] ? this.props.bets[currentIndex] : {type: "-"}}
                                                         secondHorse={this.props.secondHorsesBets[indexUserBetHorses] ? this.props.secondHorsesBets[indexUserBetHorses] : {
                                                             name: "-",
                                                             jockey: "-"
                                                         }}
                                                         race={this.props.bets[currentIndex] ? this.props.races[indexUserBetHorses] : {
                                                             location: "-",
                                                             time: "-"
                                                         }}
                                                         horseStatingPrice={this.props.horseStartingPrice[indexUserBetHorses]}/> : null}
                                {this.props.routerPath === "/bet_result" &&
                                this.props.horses.length === this.props.data.length ?
                                    <RowTableUserResult value={value} horse={this.props.horses[currentIndex]}/> : null}
                            </React.Fragment>

                        )
                    })}
                    </tbody>
                </table>
                <nav aria-label="Page navigation example">
                    <ul className="pagination">
                        {this.getSteps()}
                    </ul>
                </nav>
            </React.Fragment>
        )
    }
}

export default Table;