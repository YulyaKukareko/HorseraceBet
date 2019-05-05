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
        this.state = {
            races: [],
            currentRace: "",
            horses: [],
            results: []
        }
    }

    handleChange = (event) => {
        this.setState({[event.target.name]: event.target.value});
    };

    componentDidMount() {
        axios.get(Config.GET_RACE_IN_RESULT)
            .then((resp) => {
                this.setState({races: resp.data.result});
            })
    }

    chooseLocation = async (race) => {
        await this.setState({currentRace: race, horses: [], results: []});

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
        const {t} = this.props;
        return (
            <React.Fragment>
                <RacesList races={this.state.races} chooseLocation={this.chooseLocation}/>

                {this.state.currentRace && <HeaderRaceInfo race={this.state.currentRace}/>}
                {this.state.currentRace && <RaceInfo race={this.state.currentRace}/>}

                <div className={"user_bets"}>
                    {this.state.results.length !== 0 ? (
                        <Table routerPath={"/bet_result"} data={this.state.results} horses={this.state.horses}
                               header={t('TABLE RESULT USER COLUMNS', {returnObjects: true})}/>) : null}
                </div>
            </React.Fragment>
        )
    }
}

export default withTranslation('translation')(BetResult);