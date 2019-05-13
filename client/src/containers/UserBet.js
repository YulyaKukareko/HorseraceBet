import React, {Component} from 'react';
import RacesList from "../components/tables/template/RacesList";
import RaceInfo from "../components/tables/template/RaceInfo";
import HeaderRaceInfo from "../components/tables/template/HeaderRaceInfo";
import BetsTypeList from "../components/tables/template/BetsTypeList";
import Config from "../config/Config";
import axios from 'axios';
import Table from "../components/tables/template/Table";
import {withTranslation} from "react-i18next";
import getLocalizationErrorMessage from '../common/APIUtils';

class UserBet extends Component {

    constructor(props) {
        super(props);

        this.state = {
            races: [], currentRace: "", currentRaceCountry: {}, currentBetType: "", bets: [], firstHorsesBet: [],
            secondHorsesBet: [], betMoney: "", currentCountry: "", countries: [], ajaxError: "", startingPrices: [],
            translate: this.props.t
        };

        this.props.validator.hideMessages();
        this.props.validator.purgeFields();
    }

    componentWillMount() {
        axios.get(Config.GET_RACES_EXCLUDING_RESULT)
            .then((resp) => {
                this.setState({races: resp.data.result}, () => {
                    let countryPromises = [];

                    this.state.races.map((value) => {
                        countryPromises.push(axios.post(Config.GET_COUNTRY_BY_ID, {id: value.countryId}));
                    });
                    axios.all(countryPromises)
                        .then((resp) => {
                            resp.map((value) => this.setState({countries: [...this.state.countries, value.data.result]}));
                        })
                });
            })
    };

    chooseBet = (obj) => {
        this.props.validator.showMessages();

        let bet = {
            betId: obj.betId,
            haveSp: obj.sp !== 0,
            betMoney: obj.betMoney,
            coefficient: obj.coefficient
        };

        axios.post(Config.CREATE_USER_BET, bet)
            .then((resp) => {
                if (resp.data.status === "success") {
                    let index = this.state.bets.findIndex(item => item.id === bet.id);
                    let middleArray = this.state.bets;

                    middleArray.splice(index, 1);

                    this.setState({bets: middleArray});
                    middleArray = this.state.firstHorsesBet;
                    middleArray.splice(index, 1);

                    this.setState({startingPrices: middleArray});
                    middleArray = this.state.startingPrices;
                    middleArray.splice(index, 1);

                    this.setState({firstHorsesBet: middleArray});
                    middleArray = this.state.secondHorsesBet;
                    middleArray.splice(index, 1);

                    this.setState({secondHorsesBet: middleArray});
                    this.props.getCurrentUserState();
                    this.setState({ajaxError: ""});
                } else {
                    this.setState({ajaxError: getLocalizationErrorMessage(this.state.translate, resp.data.errorMes)});
                }
            });
    };

    chooseLocation = (obj) => {
        this.setState({currentRace: obj.race});
        this.setState({currentCountry: obj.country});
    };

    chooseBetType = (event) => {
        if (this.state.currentRace !== "") {
            this.setState({firstHorsesBet: [], secondHorsesBet: [], bets: [], startingPrices: []});
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

                                    axios.post(Config.GET_SP_BY_ID, {id: value.firstStartingPriceHorseId})
                                        .then((resp) => {
                                            this.setState({startingPrices: [...this.state.startingPrices, resp.data.result]});
                                        });

                                    if (value.secondStartingPriceHorseId !== 0) {
                                        axios.post(Config.GET_HORSES_BY_SP_ID, {startingPriceId: value.secondStartingPriceHorseId})
                                            .then((resp) => {
                                                this.setState({secondHorsesBet: [...this.state.secondHorsesBet, resp.data.result]});
                                            });
                                    }

                                    axios.post(Config.GET_HORSES_BY_SP_ID, {startingPriceId: value.firstStartingPriceHorseId})
                                        .then((resp) => {
                                            this.setState({firstHorsesBet: [...this.state.firstHorsesBet, resp.data.result]});
                                        });


                                });
                            }
                        });
                    });
            });
        }
    };


    render() {
        const t = this.state.translate;
        return (
            <React.Fragment>
                <div className={"bet_user_result_select"}>
                    {
                        this.state.countries.length !== 0 &&
                        <RacesList content={t('RACES')} races={this.state.races} countries={this.state.countries}
                                   chooseLocation={this.chooseLocation}/>
                    }
                </div>
                <div className={"bet_user_result_block"}>
                    {
                        this.state.currentRace &&
                        <HeaderRaceInfo date={t('DATE')} race={this.state.currentRace}
                                        country={this.state.currentCountry}/>
                    }
                    {
                        this.state.currentRace &&
                        <RaceInfo name={t('NAME')} purse={t('PURSE')} type={t('RACE_TYPE')}
                                  country={this.state.currentCountry}
                                  race={this.state.currentRace}/>
                    }
                    <BetsTypeList chooseBetType={this.chooseBetType} data={Config.BET_TYPES}/>
                    {
                        this.props.validator.messageWhenPresent(this.state.ajaxError, {
                            element: message => <div className="ajax_error">{message}</div>
                        })
                    }
                    <div className={"user_bets"}>
                        {
                            this.state.bets.length !== 0 &&
                            <Table routerPath={"/bet_user"} header={this.state.currentBetType === "Opposite" ||
                            this.state.currentBetType === "Exacta" ? t('TABLE_BET_DOUBLE', {returnObjects: true}) : t('TABLE_BET_SINGLE', {returnObjects: true})}
                                   data={this.state.bets} firstHorsesBet={this.state.firstHorsesBet}
                                   chooseItem={this.chooseBet}
                                   secondHorsesBet={this.state.secondHorsesBet} betMoney={this.state.betMoney}
                                   startingPrices={this.state.startingPrices}
                            />
                        }
                    </div>
                </div>
            </React.Fragment>
        )
    }
}

export default withTranslation('translation')(UserBet);