import React, {Component} from 'react';
import RacesList from "../components/tables/template/RacesList";
import RaceInfo from "../components/tables/template/RaceInfo";
import HeaderRaceInfo from "../components/tables/template/HeaderRaceInfo";
import Config from "../config/Config";
import axios from 'axios';
import Table from "../components/tables/template/Table";
import {withTranslation} from "react-i18next";

class BetResult extends Component {

    constructor(props) {
        super(props);

        this.state = {races: [], currentRace: "", horses: [], results: [], countries: [], currentCountry: {}}
    }

    handleChange = (event) => {
        this.setState({[event.target.name]: event.target.value});
    };

    componentWillMount() {
        axios.get(Config.GET_RACE_IN_RESULT)
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
    }

    chooseLocation = async (obj) => {
        await this.setState({currentRace: obj.race, horses: [], results: []});

        this.setState({currentCountry: obj.country});

        await axios.post(Config.GET_RESULTS_BY_RACE_ID, {raceId: this.state.currentRace.id})
            .then((resp) => {
                this.setState({results: resp.data.result});
            });
        this.state.results.map((value) => {
            axios.post(Config.GET_HORSE_BY_ID, {id: value.horseId})
                .then((resp) => {
                    this.setState({horses: [...this.state.horses, resp.data.result]});
                });
        })
    };


    render() {
        let {t} = this.props;

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
                        <HeaderRaceInfo date={t('DATE')} race={this.state.currentRace} country={this.state.currentCountry}/>
                    }
                    {
                        this.state.currentRace &&
                        <RaceInfo race={this.state.currentRace} country={this.state.currentCountry} name={t('NAME')}
                                  purse={t('PURSE')} type={t('RACE_TYPE')}/>
                    }

                    <div className={"bets_results"}>
                        {
                            this.state.results.length !== 0 &&
                            <Table routerPath={"/bet_result"} data={this.state.results} horses={this.state.horses}
                                   header={t('TABLE_RESULT_USER_COLUMNS', {returnObjects: true})}/>
                        }
                    </div>
                </div>
            </React.Fragment>
        )
    }
}

export default withTranslation('translation')(BetResult);