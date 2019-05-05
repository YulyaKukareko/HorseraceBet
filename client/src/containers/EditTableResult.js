import React, {Component} from 'react';
import '../resources/css/table.css'
import Table from "../components/tables/template/Table";
import Config from "../config/Config";
import axios from 'axios'
import Header from "../components/tables/template/Header";
import RowTableAdminResult from "../components/tables/RowTableAdminResult";
import EditTable from "../components/editors/EditTable";
import {withTranslation} from "react-i18next";

class EditTableResult extends Component {

    constructor(props) {
        super(props);
        this.state = {
            completedRaces: [], horses: [], joinHorses: [], raceId: "", currentHorseId: "", currentHorseName: "",
            currentHorseJockey: "", currentPlace: "", countPlaces: 0
        }
    };

    componentDidMount() {
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
        let result = {
            raceId: this.state.raceId,
            place: this.state.currentPlace,
            horseId: this.state.currentHorseId
        };

        axios.post(Config.SAVE_RESULT_ROW, result)
            .then(() => {
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
            });
    };

    getRacesInfo = (event) => {
        this.setState({raceId: event.target.value}, () => {
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
        const {t} = this.props;
        return (
            <React.Fragment>
                <div className={"result"}>
                    <table className="table table-striped table-invers">
                        <thead>
                        <Header header={t('TABLE_RESULT_COLUMNS', {returnObjects: true})}/>
                        </thead>
                        <tbody>
                        {this.formationTable()}
                        </tbody>
                    </table>
                </div>
                <div className="result_sub_component">
                    <Table routerPath={"/sphorses"} header={t('TABLE_SP_COLUMNS_HORSE', {returnObjects: true})}
                           data={this.state.horses}
                           chooseItem={this.chooseHorse}/>
                </div>
                <select name={"raceId"} className="form-control race_select_bet_result" placeholder={"Choose race"}
                        onChange={(e) => this.getRacesInfo(e)}>
                    {this.state.completedRaces.map((value, index) => {
                        let formattedTime = value.time.substring(0, value.time.length - 5);
                        return (
                            <option value={value.id}>{value.location + " " + formattedTime}</option>
                        )
                    })}
                </select>
                <EditTable editStyle={"edit_result"}
                           data={[{
                               name: "currentPlace",
                               value: this.state.currentPlace,
                               placeholder: t('PLACE'),
                               disabled: true
                           }, {
                               name: "currentHorseName",
                               value: this.state.currentHorseName,
                               placeholder: t('HORSE_NAME'),
                               disabled: true
                           }, {
                               name: "currentHorseJockey",
                               value: this.state.currentHorseJockey,
                               placeholder: t('JOCKEY'),
                               disabled: true
                           }]}
                           handleChange={this.handleChange} create={this.createResultRow}/>
            </React.Fragment>
        )
    }
}


export default withTranslation('translation')(EditTableResult);