import React, {Component} from 'react';
import '../resources/css/table.css'
import Config from "../config/Config";
import axios from 'axios';
import Table from "../components/tables/template/Table";
import EditTable from '../components/editors/EditTable';
import {withTranslation} from "react-i18next";
import getLocalizationErrorMessage from '../common/APIUtils';

class EditTableHorseSP extends Component {

    constructor(props) {
        super(props);

        this.state = {
            horses: [],
            name: "",
            jockey: "",
            races: [],
            raceName: "",
            distance: "",
            racesSp: [],
            horsesSp: [],
            startingPrices: [],
            horseId: "",
            raceId: "",
            id: "",
            currentSp: "",
            countryRaces: "",
            translate: this.props.t
        };
        this.props.validator.hideMessages();
        this.props.validator.purgeFields();
    }

    componentWillMount() {
        axios.get(Config.GET_RACES_EXCLUDING_RESULT).then((resp) => {
            this.setState({races: resp.data.result});
        });

        this.getMainContent();
    }

    getMainContent = async () => {
        this.setState({startingPrices: [], horsesSp: [], racesSp: []});

        await axios.get(Config.GET_SP).then((resp) => {
            this.setState({startingPrices: resp.data.result});
        });

        let startingPricePromises = [];

        this.state.startingPrices.map((value) => {
            startingPricePromises.push(axios.post(Config.GET_HORSE_BY_ID, {id: value.horseId}));
        });
        axios.all(startingPricePromises)
            .then((resp) => {
                resp.map(value => this.setState({horsesSp: [...this.state.horsesSp, value.data.result]}));
            });

        let racesPromises = [];

        this.state.startingPrices.map((value) => {
            racesPromises.push(axios.post(Config.GET_RACE_BY_ID, {id: value.raceId}));
        });
        axios.all(racesPromises)
            .then((resp) => {
                resp.map(value => this.setState({racesSp: [...this.state.racesSp, value.data.result]}))
            });
    };

    chooseHorse = (obj) => {
        this.setState({horseId: obj.id, name: obj.name, jockey: obj.jockey});
    };

    chooseRace = (obj) => {
        this.setState({raceId: obj.id, raceName: obj.name, distance: obj.distance}, () => {
            axios.post(Config.GET_HORSES_BY_EXCLUDING_RACE_ID, {"raceId": this.state.raceId}).then((resp) => {
                this.setState({horses: resp.data.result});
            });
        });
    };

    handleChange = (event) => {
        this.setState({[event.target.name]: event.target.value});
    };

    editSp = (obj) => {
        this.setState({
            horseId: obj.horseId,
            raceId: obj.raceId,
            name: obj.name,
            raceName: obj.raceName,
            currentSp: obj.sp,
            id: obj.id
        });
    };

    deleteSp = (obj) => {
        axios.post(Config.DELETE_SP, obj)
            .then((resp) => {
                if (resp.data.status === "success") {
                    let index = this.state.startingPrices.findIndex(item => item.id === obj.id);

                    let middleArray = this.state.startingPrices;
                    middleArray.splice(index, 1);
                    this.setState({startingPrices: middleArray});
                }
            })
    };

    createSP = () => {
        this.props.validator.showMessages();
        if (this.props.validator.allValid()) {
            let startingPrice = {
                raceId: this.state.raceId,
                horseId: this.state.horseId,
                sp: this.state.currentSp === "" ? 0 : this.state.currentSp
            };

            axios.post(Config.CREATE_SP, startingPrice)
                .then((resp) => {
                    if (resp.data.status === "success") {
                        this.setState({startingPrices: [], horsesSp: [], racesSp: [], name: ""}, () => {
                            this.getMainContent();
                            this.props.validator.hideMessages();

                            let index = this.state.horses.findIndex(item => item.id === startingPrice.horseId);
                            let middleArray = this.state.horses;

                            middleArray.splice(index, 1);
                            this.setState({horses: middleArray});
                            this.setState({ajaxError: ""});
                        });
                    } else {
                        this.setState({ajaxError: getLocalizationErrorMessage(this.state.translate, resp.data.errorMes)});
                    }
                });
        } else {
            this.forceUpdate();
        }
    };

    updateSP = () => {
        this.props.validator.showMessages();

        if (this.props.validator.allValid()) {
            let updateSP = {
                raceId: this.state.raceId,
                horseId: this.state.horseId,
                sp: this.state.currentSp,
                id: this.state.id
            };

            axios.post(Config.UPDATE_SP, updateSP)
                .then((resp) => {
                    if (resp.data.status === "success") {
                        this.setState({ajaxError: ""});
                        this.setState({
                            startingPrices: this.state.startingPrices.map(el => (el.id === updateSP.id ? updateSP : el))
                        });
                    } else {
                        this.setState({ajaxError: getLocalizationErrorMessage(this.state.translate, resp.data.errorMes)});
                    }
                });
        } else {
            this.forceUpdate();
        }
    };

    render() {
        const t = this.state.translate;

        return (
            <React.Fragment>
                <div className={"horse_starting_price"}>
                    <div className="horse_starting_sub_component">
                        <Table routerPath={"/sphorses"} header={t('TABLE_SP_COLUMNS_HORSE', {returnObjects: true})}
                               data={this.state.horses}
                               chooseItem={this.chooseHorse}/>
                    </div>
                    <div className="horse_starting_sub_component">
                        {
                            this.state.races.length !== 0 &&
                            <Table routerPath={"/spraces"} header={t('TABLE_SP_COLUMNS_RACES', {returnObjects: true})}
                                   data={this.state.races}
                                   chooseItem={this.chooseRace}/>
                        }
                    </div>
                    <div className="horse_starting_main">
                        {
                            this.state.startingPrices.length !== 0 &&
                            <Table routerPath={"/sp"} header={t('TABLE_SP_COLUMNS', {returnObjects: true})}
                                   data={this.state.startingPrices} horses={this.state.horsesSp}
                                   races={this.state.racesSp}
                                   editItem={this.editSp}
                                   deleteItem={this.deleteSp}/>
                        }
                    </div>
                </div>
                <div className={"horse_starting_price_editors"}>
                    <EditTable
                        data={[{
                            name: "raceName", value: this.state.raceName, placeholder: t('RACE'), disabled: true,
                            validator: this.props.validator.message(t('LOCATION'), this.state.raceName, 'required')
                        }, {
                            name: "name",
                            value: this.state.name,
                            placeholder: t('HORSE_NAME'),
                            disabled: true,
                            validator: this.props.validator.message(t('HORSE_NAME'), this.state.name, 'required')
                        }, {
                            name: "currentSp", value: this.state.currentSp, placeholder: t('STARTING_PRICE')
                        }]}
                        handleChange={this.handleChange} create={this.createSP}
                        update={this.updateSP}/>
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


export default withTranslation('translation')(EditTableHorseSP);