import React, {Component} from 'react';
import '../resources/css/table.css'
import Config from "../config/Config";
import axios from 'axios';
import Table from "../components/tables/template/Table";
import EditTable from '../components/editors/EditTable';
import {withTranslation} from "react-i18next";
import getLocalizationErrorMessage from '../common/APIUtils';

class EditTableRace extends Component {

    constructor(props) {
        super(props);

        this.state = {
            races: [], type: "", name: "", distance: "", purse: "", time: "", id: "", raceTypes: [], countries: [],
            countryId: "", countryName: "", countryRaces: [], ajaxError: "", translate: this.props.t
        };
        this.props.validator.hideMessages();
        this.props.validator.purgeFields();
    }

    componentWillMount() {
        this.getMainContent();

        axios.get(Config.GET_RACE_TYPES)
            .then((resp) => {
                this.setState({raceTypes: resp.data.result});
            });
        axios.get(Config.GET_COUNTRIES)
            .then((resp) => {
                this.setState({countries: resp.data.result});
            });
    }

    handleChange = (event) => {
        this.setState({[event.target.name]: event.target.value});
    };

    getMainContent = async () => {
        this.setState({countryRaces: []});

        await axios.get(Config.GET_RACES).then((resp) => {
            this.setState({races: resp.data.result});
        });

        let countryPromises = [];

        this.state.races.map((value) => {
            countryPromises.push(axios.post(Config.GET_COUNTRY_BY_ID, {id: value.countryId}));
        });
        await axios.all(countryPromises)
            .then((resp) => {
                resp.map((value) => this.setState({countryRaces: [...this.state.countryRaces, value.data.result]}));
            })
    };

    editRace = (obj) => {
        this.setState({
            name: obj.name,
            countryId: obj.countryId,
            countryName: obj.countryName,
            distance: obj.distance,
            purse: obj.purse,
            type: obj.type,
            time: obj.time,
            id: obj.id
        });
    };

    deleteRace = (obj) => {
        axios.post(Config.DELETE_RACE, obj)
            .then((resp) => {
                if (resp.data.status === "success") {
                    let index = this.state.races.findIndex(item => item.id === obj.id);
                    let middleArray = this.state.races;

                    middleArray.splice(index, 1);
                    this.setState({races: middleArray});

                    middleArray = this.state.countryRaces;
                    middleArray.splice(index, 1);

                    this.setState({countryRaces: middleArray});
                    this.setState({ajaxError: ""});
                } else {
                    this.props.validator.showMessages();
                    this.setState({ajaxError: getLocalizationErrorMessage(this.state.translation, resp.data.errorMes)});
                }
            })
    };

    createRace = () => {
        this.props.validator.showMessages();

        if (this.props.validator.allValid()) {
            let race = {
                name: this.state.name,
                countryId: this.state.countryId,
                distance: this.state.distance,
                type: this.state.type.toUpperCase(),
                purse: this.state.purse,
                time: this.state.time
            };

            axios.post(Config.CREATE_RACE, race)
                .then((resp) => {
                    if (resp.data.status === "success") {
                        this.getMainContent();
                        this.setState({ajaxError: ""});
                    } else {
                        this.setState({ajaxError: getLocalizationErrorMessage(this.state.translation, resp.data.errorMes)});
                    }
                });
        } else {
            this.forceUpdate();
        }
    };

    updateRace = () => {
        this.props.validator.showMessages();
        if (this.props.validator.allValid()) {
            let updateRace = {
                id: this.state.id,
                name: this.state.name,
                distance: this.state.distance,
                type: this.state.type,
                purse: this.state.purse,
                time: this.state.time,
                countryId: this.state.countryId
            };

            axios.post(Config.UPDATE_RACE, updateRace)
                .then((resp) => {
                    if (resp.data.status === "success") {
                        this.setState({
                            races: this.state.races.map(el => (el.id === updateRace.id ? updateRace : el)),
                            countryRaces: this.state.countryRaces.map(el => (el.id === updateRace.countryId ? {
                                id: this.state.countryId, name: this.state.countryName
                            } : el))
                        });
                        this.setState({ajaxError: ""});
                    } else {
                        this.setState({ajaxError: resp.data.errorMes});
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
                <div className="main_table">
                    {
                        this.state.countryRaces.length !== 0 &&
                        this.state.races.length === this.state.countryRaces.length &&
                        <Table routerPath={"/races"} header={t('TABLE_RACE_COLUMNS', {returnObjects: true})}
                               countryRaces={this.state.countryRaces}
                               data={this.state.races}
                               editItem={this.editRace}
                               deleteItem={this.deleteRace}/>
                    }
                </div>
                <div>
                    <div className={"select_country"}>
                        {
                            this.state.countries.length !== 0 &&
                            <select name="countryId" onChange={this.handleChange} className="form-control"
                                    value={this.state.countryId}>
                                <option defaultChecked={""}>
                                    {t('COUNTRY')}...
                                </option>
                                {this.state.countries.map((value) => {
                                    return (
                                        <option value={value.id}>{value.name}</option>
                                    )})
                                };
                            </select>
                        }
                        {
                            this.props.validator.message(t('COUNTRY'), this.state.countryId, 'required')
                        }
                    </div>
                    <EditTable editStyle={"edit"}
                               data={[{
                                   name: "name",
                                   value: this.state.name,
                                   placeholder: t('NAME'),
                                   validator: this.props.validator.message(t('NAME'), this.state.name, 'required')
                               }, {
                                   name: "distance",
                                   value: this.state.distance,
                                   placeholder: t('DISTANCE'),
                                   validator: this.props.validator.message(t('DISTANCE'), this.state.distance, 'required|numeric')
                               }, {
                                   name: "purse",
                                   value: this.state.purse,
                                   placeholder: t('PURSE'),
                                   validator: this.props.validator.message(t('PURSE'), this.state.purse, 'required|currency')
                               }, {
                                   name: "time",
                                   value: this.state.time,
                                   placeholder: t('TIME'),
                                   validator: this.props.validator.message(t('TIME'), this.state.time && moment(this.state.time, 'YYYY-MM-DD HH:mm:ss'), [{after_or_equal: moment()}])
                               }]}
                               select={{
                                   name: "type",
                                   placeholder: t('RACE_TYPE'),
                                   value: this.state.type,
                                   options: this.state.raceTypes,
                                   validator: this.props.validator.message(t('RACE_TYPE'), this.state.type, 'required')
                               }}
                               handleChange={this.handleChange} create={this.createRace}
                               update={this.updateRace}/>
                    {this.props.validator.messageWhenPresent(this.state.ajaxError, {
                        element: message => <div className="ajax_error">{message}</div>
                    })}
                </div>
            </React.Fragment>
        )

    }
}


export default withTranslation('translation')(EditTableRace);