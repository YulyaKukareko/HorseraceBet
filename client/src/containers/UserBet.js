import React, {Component} from 'react';
import RacesList from "../components/tables/template/RacesList";
import RaceInfo from "../components/tables/template/RaceInfo";
import HeaderRaceInfo from "../components/tables/template/HeaderRaceInfo";
import BetsTypeList from "../components/tables/template/BetsTypeList";
import Config from "../config/Config";
import axios from 'axios';
import Table from "../components/tables/template/Table";
import {withTranslation} from "react-i18next";

class UserBet extends Component {

    constructor(props) {
        super(props);
        this.state = {
            races: [],
            currentRace: null,
            currentBetType: "",
            bets: [],
            firstHorsesBet: [],
            secondHorsesBet: [],
            betMoney: ""
        }
    }

    handleChange = (event) => {
        this.setState({[event.target.name]: event.target.value});
    };

    chooseBet = (obj) => {
        let bet = {
            betId: obj.betId,
            haveSp: obj.sp !== 0,
            betMoney: this.state.betMoney,
            coefficient: obj.coefficient
        };

        axios.post(Config.CREATE_USER_BET, bet)
            .then(() => {
                let index = this.state.bets.findIndex(item => item.id === bet.id);
                let middleArray = this.state.bets;
                middleArray.splice(index, 1);
                this.setState({bets: middleArray});
                middleArray = this.state.firstHorsesBet;
                middleArray.splice(index, 1);
                this.setState({firstHorsesBet: middleArray});
                middleArray = this.state.secondHorsesBet;
                middleArray.splice(index, 1);
                this.setState({secondHorsesBet: middleArray});
                this.props.getCurrentUserState();
            });
    };

    componentDidMount() {
        axios.get(Config.GET_RACES_EXCLUDING_RESULT)
            .then((resp) => {
                this.setState({races: resp.data.result});
            })
    }

    chooseLocation = (race) => {
        this.setState({currentRace: race});
    };

    chooseBetType = (event) => {
        this.setState({firstHorsesBet: [], secondHorsesBet: [], bets: []});
        this.setState({currentBetType: event.target.value}, () => {
            let params = {
                raceId: this.state.currentRace.id,
                betType: this.state.currentBetType.toUpperCase()
            };

            axios.post(Config.GET_BETS_BY_RACE_ID_AND_BET_TYPES, params)
                .then((resp) => {
                    this.setState({bets: resp.data.result}, () => {
                        if (this.state.bets && this.state.bets.length !== 0) {
                            this.state.bets.map((value) => {
                                let firstHorseStartingPriceId = {
                                    startingPriceId: value.firstStartingPriceHorseId
                                };

                                let secondHorseStartingPriceId = {
                                    startingPriceId: value.secondStartingPriceHorseId
                                };

                                axios.post(Config.GET_HORSES_BY_SP_ID, firstHorseStartingPriceId)
                                    .then((resp) => {
                                        this.setState({firstHorsesBet: [...this.state.firstHorsesBet, resp.data.result]});
                                    });
                                axios.post(Config.GET_HORSES_BY_SP_ID, secondHorseStartingPriceId)
                                    .then((resp) => {
                                        this.setState({secondHorsesBet: [...this.state.secondHorsesBet, resp.data.result]});
                                    });
                            });
                        }
                    });
                });
        });
    };


    render() {
        const {t} = this.props;
        return (
            <React.Fragment>
                <RacesList content={t('RACES')} races={this.state.races} chooseLocation={this.chooseLocation}/>

                {this.state.currentRace && <HeaderRaceInfo date={t('DATE')} race={this.state.currentRace}/>}
                {this.state.currentRace && <RaceInfo location={t('LOCATION')} purse={t('PURSE')} type={t('RACE_TYPE')} race={this.state.currentRace}/>}

                <BetsTypeList chooseBetType={this.chooseBetType}/>
                <div className={"user_bets"}>
                    {this.state.bets.length !== 0 ? (
                        <Table routerPath={"/bet_user"} header={this.state.currentBetType === "Opposite" ||
                        this.state.currentBetType === "Exacta" ? t('TABLE_BET_DOUBLE', {returnObjects: true}) : t('TABLE_BET_SINGLE', {returnObjects: true})}
                               data={this.state.bets} firstHorsesBet={this.state.firstHorsesBet}
                               chooseItem={this.chooseBet}
                               secondHorsesBet={this.state.secondHorsesBet} betMoney={this.state.betMoney}
                               handleChange={this.handleChange}
                        />) : null}
                </div>
            </React.Fragment>
        )
    }
}

export default withTranslation('translation')(UserBet);