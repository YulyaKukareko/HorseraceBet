import React, {Component} from 'react';
import '../resources/css/table.css'
import Config from "../config/Config";
import axios from 'axios';
import Table from "../components/tables/template/Table";
import EditTable from '../components/editors/EditTable';
import {withTranslation} from "react-i18next";
import getLocalizationErrorMessage from '../common/APIUtils';

class EditTableBet extends Component {

    constructor(props) {
        super(props);

        this.state = {
            id: "",
            availableHorses: [],
            availableRaces: [],
            startingPrices: [],
            name: "",
            type: "",
            time: 0,
            firstHorseStartingPriceId: "",
            firstHorseId: "",
            firstHorseName: "",
            firstHorseJockey: "",
            firstHorseSP: "",
            secondHorseId: "",
            secondHorseStartingPriceId: "",
            secondHorseName: "",
            secondHorseJockey: "",
            secondHorseSP: "",
            coefficient: "",
            firstHorsesBet: [],
            secondHorsesBet: [],
            racesBet: [],
            bets: [],
            startingPricesBetFirstHorse: [],
            startingPricesBetSecondHorse: [],
            betTypes: [],
            currentRace: "",
            currentRaceId: "",
            translate: this.props.t,
            ajaxError: ""
        };
        this.props.validator.hideMessages();
        this.props.validator.purgeFields();
    }

    componentWillMount() {
        axios.get(Config.GET_RACES_EXCLUDING_RESULT).then((resp) => {
            this.setState({availableRaces: resp.data.result});
        });

        axios.get(Config.GET_BETS_TYPE).then((resp) => {
            this.setState({betTypes: resp.data.result});
        });

        this.getMainContent();
    }

    getMainContent = async () => {
        await this.setState({
            firstStartingPriceHorseId: [], secondStartingPriceHorseId: [], startingPricesBetFirstHorse: [],
            startingPricesBetSecondHorse: [], secondHorsesBet: [], firstHorsesBet: [], racesBet: []
        });

        await axios.get(Config.GET_BETS).then((resp) => {
            this.setState({bets: resp.data.result});
        });

        let startingPricesBetFirstHorsePromises = [];
        let startingPricesBetSecondHorsePromises = [];

        this.state.bets.map((value) => {
            startingPricesBetFirstHorsePromises.push(axios.post(Config.GET_SP_BY_ID, {id: value.firstStartingPriceHorseId}));
            value.secondStartingPriceHorseId !== 0 ? startingPricesBetSecondHorsePromises.push(axios.post(Config.GET_SP_BY_ID, {id: value.secondStartingPriceHorseId})) : null;
        });
        await axios.all(startingPricesBetFirstHorsePromises)
            .then((resp) => {
                resp.map((value) => this.setState({startingPricesBetFirstHorse: [...this.state.startingPricesBetFirstHorse, value.data.result]}));
            });
        await axios.all(startingPricesBetSecondHorsePromises)
            .then((resp) => {
                resp.map((value) => this.setState({startingPricesBetSecondHorse: [...this.state.startingPricesBetSecondHorse, value.data.result]}));
            });

        let firstHorsesBetPromises = [];
        let secondHorsesBetPromises = [];
        let racesBetPromises = [];

        this.state.startingPricesBetFirstHorse.map((value) => {
            firstHorsesBetPromises.push(axios.post(Config.GET_HORSE_BY_ID, {id: value.horseId}));
            racesBetPromises.push(axios.post(Config.GET_RACE_BY_ID, {id: value.raceId}));
        });
        this.state.startingPricesBetSecondHorse.map((value) => {
            secondHorsesBetPromises.push(axios.post(Config.GET_HORSE_BY_ID, {id: value.horseId}));
        });
        await axios.all(secondHorsesBetPromises)
            .then((resp) => {
                resp.map((value) => this.setState({secondHorsesBet: [...this.state.secondHorsesBet, value.data.result]}));
            });
        axios.all(firstHorsesBetPromises)
            .then((resp) => {
                resp.map((value) => this.setState({firstHorsesBet: [...this.state.firstHorsesBet, value.data.result]}));
            });
        axios.all(racesBetPromises)
            .then((resp) => {
                resp.map((value) => this.setState({racesBet: [...this.state.racesBet, value.data.result]}));
            });
    };

    chooseFirstHorse = (obj) => {
        this.setState({
            firstHorseName: obj.name,
            firstHorseJockey: obj.jockey,
            firstHorseSP: obj.sp,
            firstHorseStartingPriceId: obj.id
        });
    };

    chooseSecondHorse = (obj) => {
        this.setState({
            secondHorseName: obj.name,
            secondHorseJockey: obj.jockey,
            secondHorseStartingPriceId: obj.id,
            secondHorseSP: obj.sp
        });
    };

    handleChange = (event) => {
        if (event.target.name === "type") {
            if ((event.target.value !== "OPPOSITE") && (event.target.value !== "EXACTA")) {
                this.setState({
                    secondHorseName: "-",
                    secondHorseJockey: "-",
                    secondHorseStartingPriceId: "-",
                    secondHorseSP: "-"
                });
            }
        }

        this.setState({[event.target.name]: event.target.value});
    };

    editBet = (obj) => {
        this.setState({
            firstHorseStartingPriceId: obj.firstHorseStartingPriceId,
            firstHorseName: obj.firstHorseName,
            firstHorseJockey: obj.firstHorseJockey,
            firstHorseSP: obj.firstHorseSP,
            currentRace: obj.currentRace,
            type: obj.type,
            time: obj.time,
            coefficient: obj.coefficient,
            id: obj.id,
            secondHorseStartingPriceId: obj.secondHorseStartingPriceId,
            secondHorseName: obj.secondHorseName,
            secondHorseJockey: obj.secondHorseJockey,
            secondHorseSP: obj.secondHorseSP,
            currentRaceId: obj.currentRace.id
        });
    };

    createBet = () => {
        this.props.validator.showMessages();

        if (this.props.validator.allValid()) {
            let bet = {
                type: this.state.type.toUpperCase(),
                firstStartingPriceHorseId: this.state.firstHorseStartingPriceId,
                secondStartingPriceHorseId: this.state.type === "OPPOSITE" || this.state.type === "EXACTA" ? this.state.secondHorseStartingPriceId : 0,
                coefficient: this.state.coefficient
            };

            axios.post(Config.CREATE_BET, bet)
                .then((resp) => {
                    if (resp.data.status === "success") {
                        this.setState({ajaxError: ""});
                        this.setState({
                            firstHorsesBet: [], secondHorsesBet: [], startingPricesBetFirstHorse: [],
                            startingPricesBetSecondHorse: [], racesBet: [], bets: []
                        }, () => {
                            this.getMainContent();
                        });
                    } else {
                        this.setState({ajaxError: getLocalizationErrorMessage(this.state.translate, resp.data.errorMes)});
                    }
                });
        } else {
            this.forceUpdate();
        }
    };

    updateBet = () => {
        this.props.validator.showMessages();

        if (this.props.validator.allValid()) {
            let updateBet = {
                id: this.state.id,
                type: this.state.type.toUpperCase(),
                firstStartingPriceHorseId: this.state.firstHorseStartingPriceId,
                secondStartingPriceHorseId: this.state.type === "OPPOSITE" || this.state.type === "EXACTA" ?
                    this.state.secondHorseStartingPriceId : 0,
                coefficient: this.state.coefficient
            };

            axios.post(Config.UPDATE_BET, updateBet)
                .then((resp) => {
                    if (resp.data.status === "success") {
                        this.setState({ajaxError: ""});
                        this.setState({
                            bets: this.state.bets.map(el => (el.id === updateBet.id ? updateBet : el))
                        });
                    } else {
                        this.setState({ajaxError: getLocalizationErrorMessage(resp.data.errorMes)});
                    }
                });
        } else {
            this.forceUpdate();
        }
    };

    getBetsInfo = (event) => {
        this.setState({startingPrices: [], availableHorses: []});
        this.setState({currentRaceId: event.target.value});

        let race = this.state.availableRaces.find(el => {
            return el.id == event.target.value
        });

        this.setState({currentRace: race}, () => {
            let race = {
                raceId: this.state.currentRace.id
            };
            axios.post(Config.GET_SP_BY_RACE_ID, race).then((resp) => {

                this.setState({startingPrices: resp.data.result}, () => {
                    let horsesPromises = [];
                    this.state.startingPrices.map((value) => horsesPromises.push(axios.post(Config.GET_HORSE_BY_ID, {id: value.horseId})));
                    axios.all(horsesPromises)
                        .then((resp) => {
                            resp.map((value) => {
                                this.setState({availableHorses: [...this.state.availableHorses, value.data.result]});
                            })
                        })
                });
            });
        });
    };

    render() {
        const t = this.state.translate;

        return (
            <React.Fragment>
                <div className={"bet_block"}>
                    <div className="sub_table_bet">
                        {this.state.bets.length !== 0 &&
                        <Table routerPath={"/bet"} header={t('TABLE_BET_COLUMNS', {returnObjects: true})}
                               data={this.state.bets}
                               firstHorsesBet={this.state.firstHorsesBet}
                               secondHorsesBet={this.state.secondHorsesBet}
                               firstHorseStaringPrices={this.state.startingPricesBetFirstHorse}
                               secondHorseStaringPrices={this.state.startingPricesBetSecondHorse}
                               races={this.state.racesBet}
                               editItem={this.editBet}
                               deleteItem={this.deleteBet}/>}
                    </div>
                    <div className="sub_table_bet">
                        {
                            this.state.startingPrices.length !== 0 &&
                            <Table routerPath={"/available_horse_bet"}
                                   header={t('TABLE_BET_AVAILABLE_HORSE_COLUMNS', {returnObjects: true})}
                                   data={this.state.startingPrices} horses={this.state.availableHorses}
                                   chooseItem={this.chooseFirstHorse}/>
                        }
                    </div>
                    {
                        (this.state.type === "OPPOSITE" || this.state.type === "EXACTA") &&
                        <div className="sub_table_bet">
                            {
                                this.state.startingPrices.length !== 0 &&
                                <Table routerPath={"/available_horse_bet"}
                                       header={t('TABLE_BET_AVAILABLE_HORSE_COLUMNS', {returnObjects: true})}
                                       data={this.state.startingPrices} horses={this.state.availableHorses}
                                       chooseItem={this.chooseSecondHorse}/>
                            }
                        </div>
                    }
                </div>
                <div className={"bet_editor"}>
                    <select className="form-control race_select_bet" onChange={(e) => this.getBetsInfo(e)}
                            value={this.state.currentRaceId}>
                        <option defaultChecked={""}>
                            {t('RACE')}...
                        </option>
                        {
                            this.state.availableRaces.map((value) => {
                                let formattedTime = value.time.substring(0, value.time.length - 5);

                                return (
                                    <option value={value.id}>{value.name + " " + formattedTime}</option>
                                )
                            })
                        }
                    </select>
                    {
                        this.props.validator.message(t('RACE'), this.state.currentRaceId, 'required')
                    }
                    <EditTable data={[{
                        name: "name",
                        value: this.state.currentRace.name,
                        placeholder: t('RACE_LOCATION'),
                        disabled: true,
                        validator: this.props.validator.message(t('RACE_LOCATION'), this.state.currentRace.name, 'required')
                    }, {
                        name: "time",
                        value: this.state.currentRace.time,
                        placeholder: t('TIME'),
                        disabled: true,
                        validator: this.props.validator.message(t('TIME'), this.state.currentRace.time, 'required')
                    }, {
                        name: "firstHorseName",
                        value: this.state.firstHorseName,
                        placeholder: t('FIRST_HORSE_NAME'),
                        disabled: true,
                        validator: this.props.validator.message(t('FIRST_HORSE_NAME'), this.state.firstHorseName, 'required')
                    }, {
                        name: "firstHorseJockey",
                        value: this.state.firstHorseJockey,
                        placeholder: t('FIRST_HORSE_JOCKEY'),
                        disabled: true,
                        validator: this.props.validator.message(t('FIRST_HORSE_JOCKEY'), this.state.firstHorseJockey, 'required')
                    }, {
                        name: "firstHorseSP",
                        value: this.state.firstHorseSP,
                        placeholder: t('FIRST_HORSE_SP'),
                        disabled: true,
                        validator: this.props.validator.message(t('FIRST_HORSE_JOCKEY'), this.state.firstHorseJockey, 'required')
                    }, {
                        name: "secondHorseName",
                        value: this.state.secondHorseName,
                        placeholder: t('SECOND_HORSE_NAME'),
                        disabled: true,
                        validator: this.props.validator.message(t('SECOND_HORSE_NAME'), this.state.secondHorseName, 'required')
                    }, {
                        name: "secondHorseJockey",
                        value: this.state.secondHorseJockey,
                        placeholder: t('SECOND_HORSE_JOCKEY'),
                        disabled: true,
                        validator: this.props.validator.message(t('SECOND_HORSE_JOCKEY'), this.state.secondHorseJockey, 'required')
                    }, {
                        name: "secondHorseSP",
                        value: this.state.secondHorseSP,
                        placeholder: t('SECOND_HORSE_SP'),
                        disabled: true,
                        validator: this.props.validator.message(t('SECOND_HORSE_SP'), this.state.secondHorseSP, 'required')
                    }, {
                        name: "coefficient",
                        value: this.state.coefficient,
                        placeholder: t('COEFFICIENT'),
                        validator: this.props.validator.message(t('COEFFICIENT'), this.state.coefficient, 'required')
                    }]} select={{
                        name: "type",
                        placeholder: "Bet type",
                        value: this.state.type,
                        options: this.state.betTypes,
                        validator: this.props.validator.message(t('BET_TYPE'), this.state.type, 'required')
                    }}
                               handleChange={this.handleChange} create={this.createBet}
                               update={this.updateBet}/>
                </div>
                {
                    this.props.validator.messageWhenPresent(this.state.ajaxError, {
                        element: message => <div className="ajax_error">{message}</div>
                    })
                }
            </React.Fragment>
        )
    }
}

export default withTranslation('translation')(EditTableBet);