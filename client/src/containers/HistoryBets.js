import React, {Component} from 'react';
import Table from "../components/tables/template/Table";
import Config from "../config/Config";
import axios from 'axios';
import {withTranslation} from "react-i18next";

class HistoryBets extends Component {

    constructor(props) {
        super(props);

        this.state = {
            userBets: [], bets: [], horseStartingPrice: [], firstHorsesBets: [], secondHorsesBets: [], races: [],
            countries: []
        }
    }

    componentWillMount() {
        axios.get(Config.GET_USER_BET)
            .then((resp) => {

                this.setState({userBets: resp.data.result}, async () => {
                    let betPromises = [];

                    this.state.userBets.map((value) => {
                        if (value.betId !== 0) {
                            betPromises.push(axios.post(Config.GET_BET_BY_ID, {betId: value.betId}));
                        }
                    });
                    await axios.all(betPromises)
                        .then((resp) => {
                            resp.map((value) => {
                                this.setState({bets: [...this.state.bets, value.data.result]});
                            });
                        });

                    let secondHorseStartingPrices = [];
                    let startingPriceFirstHorsePromises = [];
                    let startingPriceSecondHorsePromises = [];

                    this.state.bets.map((value) => {
                        value && startingPriceFirstHorsePromises.push(axios.post(Config.GET_SP_BY_ID, {id: value.firstStartingPriceHorseId}));
                        value && startingPriceSecondHorsePromises.push(axios.post(Config.GET_SP_BY_ID, {id: value.secondStartingPriceHorseId}));
                    });

                    await axios.all(startingPriceFirstHorsePromises)
                        .then((resp) => {
                            resp.map((value) => {
                                this.setState({horseStartingPrice: [...this.state.horseStartingPrice, value.data.result]});
                            })
                        });
                    await axios.all(startingPriceSecondHorsePromises)
                        .then((resp) => {
                            resp.map((value) => {
                                secondHorseStartingPrices.push(value.data.result);
                            })
                        });

                    let firstHorsesBetsPromises = [];
                    let secondHorsesBetsPromises = [];
                    let racesPromises = [];

                    this.state.horseStartingPrice.map((value, index) => {
                        firstHorsesBetsPromises.push(axios.post(Config.GET_HORSE_BY_ID, {id: value.horseId}));
                        secondHorseStartingPrices[index] ? secondHorsesBetsPromises.push(axios.post(Config.GET_HORSE_BY_ID, {id: secondHorseStartingPrices[index].horseId})) : null;
                        racesPromises.push(axios.post(Config.GET_RACE_BY_ID, {id: value.raceId}));
                    });

                    axios.all(firstHorsesBetsPromises)
                        .then((resp) => {
                            resp.map((value) => {
                                this.setState({firstHorsesBets: [...this.state.firstHorsesBets, value.data.result]});
                            })
                        });
                    axios.all(secondHorsesBetsPromises)
                        .then((resp) => {
                            resp.map((value) => {
                                this.setState({secondHorsesBets: [...this.state.secondHorsesBets, value.data.result]});
                            })
                        });
                    await axios.all(racesPromises)
                        .then((resp) => {
                            resp.map((value) => {
                                this.setState({races: [...this.state.races, value.data.result]});
                            })
                        });

                    let countryPromises = [];

                    this.state.races.map((value) => {
                        countryPromises.push(axios.post(Config.GET_COUNTRY_BY_ID, {id: value.countryId}));
                    });
                    axios.all(countryPromises)
                        .then((resp) => {
                            resp.map((value) => {
                                this.setState({countries: [...this.state.countries, value.data.result]});
                            });
                        })
                });
            });
    }

    render() {
        const {t} = this.props;

        return (
            <React.Fragment>
                {
                    this.state.userBets.length !== 0 &&
                    <Table routerPath={"/history_bets"} header={t('TABLE_USER_BET_COLUMNS', {returnObjects: true})}
                           firstHorsesBets={this.state.firstHorsesBets}
                           secondHorsesBets={this.state.secondHorsesBets} races={this.state.races}
                           horseStartingPrice={this.state.horseStartingPrice} bets={this.state.bets}
                           data={this.state.userBets} countries={this.state.countries}
                    />
                }
            </React.Fragment>
        )
    }
}

export default withTranslation('translation')(HistoryBets);