import React, {Component} from 'react';
import '../resources/css/table.css'
import Table from "../components/tables/template/Table";
import Config from "../config/Config";
import axios from 'axios'
import Header from "../components/tables/template/Header";
import RowTableAdminResult from "../components/tables/RowTableAdminResult";
import EditTable from "../components/editors/EditTable";
import {withTranslation} from "react-i18next";
import getLocalizationErrorMessage from '../common/APIUtils';

class EditTableResult extends Component {

    constructor(props) {
        super(props);

        this.state = {
            completedRaces: [], horses: [], joinHorses: [], raceId: "", currentHorseId: "", currentHorseName: "",
            currentHorseJockey: "", currentPlace: "", countPlaces: 0, ajaxError: "", translate: this.props.t
        };

        this.props.validator.hideMessages();
        this.props.validator.purgeFields();
    };

    componentWillMount() {
        axios.get(Config.GET_COMPLETED_RACES)
            .then((resp) => {
                this.setState({completedRaces: resp.data.result});
            });
    };

    handleChange = (event) => {
        this.setState({[event.target.name]: event.target.value});
    };

    chooseHorse = (obj) => {
        this.setState({currentHorseId: obj.id, currentHorseName: obj.name, currentHorseJockey: obj.jockey});
    };

    createResultRow = () => {
        this.props.validator.showMessages();
        if (this.props.validator.allValid()) {
            let result = {
                raceId: this.state.raceId,
                place: this.state.currentPlace,
                horseId: this.state.currentHorseId
            };

            axios.post(Config.SAVE_RESULT_ROW, result)
                .then((resp) => {
                    if (resp.data.status === "success") {
                        this.setState({joinHorses: [...this.state.joinHorses, this.state.horses.find(el => el.id === result.horseId)]});
                        let index = this.state.horses.findIndex(item => item.id === result.horseId);
                        let middleArray = this.state.horses;
                        middleArray.splice(index, 1);
                        this.setState({
                            horses: middleArray,
                            currentHorseName: "",
                            currentHorseJockey: "",
                            currentHorseId: "",
                            currentPlace: ""
                        });
                        this.props.validator.hideMessages();
                        this.setState({ajaxError: ""});
                    } else {
                        this.setState({ajaxError: getLocalizationErrorMessage(this.state.translate, resp.data.errorMes)});
                    }
                });
        } else {
            this.forceUpdate();
        }
    };

    getRacesInfo = (event) => {
        this.setState({raceId: event.target.value, joinHorses: []}, () => {
            let race = {
                raceId: this.state.raceId
            };

            axios.post(Config.GET_HORSES_SP_BY_RACE_ID, race)
                .then((resp) => {
                    this.setState({horses: resp.data.result});
                });
            axios.post(Config.GET_CONT_PLACES, race)
                .then((resp) => {
                    this.setState({countPlaces: resp.data.result});
                });
        });
    };

    formationTable = () => {
        let arrayRows = [];
        for (let i = 0; i < this.state.countPlaces; i++) {
            arrayRows.push(<RowTableAdminResult disabled={!!this.state.joinHorses[i]} value={i + 1}
                                                horse={this.state.joinHorses[i] ? this.state.joinHorses[i] : {
                                                    name: "-",
                                                    jockey: "-"
                                                }} chooseItem={this.choosePlace}/>)
        }

        return arrayRows;
    };

    choosePlace = (obj) => {
        this.setState({currentPlace: obj.place})
    };

    render() {
        const t = this.state.translate;

        return (
            <React.Fragment>
                <div className={"result_block"}>
                    <div className={"result"}>
                        <table className="table table-striped table-invers">
                            <thead>
                            <Header header={t('TABLE_RESULT_COLUMNS', {returnObjects: true})}/>
                            </thead>
                            <tbody>
                            {
                                this.formationTable()
                            }
                            </tbody>
                        </table>
                    </div>
                    <div className="result_sub_component">
                        <Table routerPath={"/sphorses"} header={t('TABLE_SP_COLUMNS_HORSE', {returnObjects: true})}
                               data={this.state.horses}
                               chooseItem={this.chooseHorse}/>
                    </div>
                </div>
                <div className={"result_edit"}>
                    <select name="raceId" className="form-control"
                            onChange={(e) => this.getRacesInfo(e)} value={this.state.raceId}>
                        <option defaultChecked={""}>
                            {t('RACE')}...
                        </option>
                        {
                            this.state.completedRaces.map((value) => {
                                let formattedTime = value.time.substring(0, value.time.length - 5);

                                return (
                                    <option value={value.id}>{value.name + " " + formattedTime}</option>
                                )
                            })
                        }
                    </select>
                    <EditTable
                        data={[{
                            name: "currentPlace",
                            value: this.state.currentPlace,
                            placeholder: t('PLACE'),
                            disabled: true,
                            validator: this.props.validator.message(t('PLACE'), this.state.currentPlace, 'required|numeric')
                        }, {
                            name: "currentHorseName",
                            value: this.state.currentHorseName,
                            placeholder: t('HORSE_NAME'),
                            disabled: true,
                            validator: this.props.validator.message(t('HORSE_NAME'), this.state.currentHorseName, 'required')
                        }, {
                            name: "currentHorseJockey",
                            value: this.state.currentHorseJockey,
                            placeholder: t('JOCKEY'),
                            disabled: true,
                            validator: this.props.validator.message(t('JOCKEY'), this.state.currentHorseJockey, 'required')
                        }]}
                        handleChange={this.handleChange} disableSave={true} create={this.createResultRow}/>
                    {
                        this.props.validator.messageWhenPresent(this.state.ajaxError, {
                            element: message => <div className="ajax_error">{message}</div>
                        })
                    }
                </div>
            </React.Fragment>
        )
    }
}


export default withTranslation('translation')(EditTableResult);