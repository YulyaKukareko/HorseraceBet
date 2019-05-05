import React, {Component} from 'react';
import '../resources/css/table.css'
import Config from "../config/Config";
import axios from 'axios';
import Table from "../components/tables/template/Table";
import EditTable from '../components/editors/EditTable';
import {withTranslation} from "react-i18next";

class EditTableHorseSP extends Component {

    constructor(props) {
        super(props);
        this.state = {
            horses: [],
            name: "",
            jockey: "",
            races: [],
            location: "",
            distance: "",
            racesSp: [],
            horsesSp: [],
            startingPrices: [],
            horseId: "",
            raceId: "",
            id: "",
            currentSp: ""
        };
    }

    componentDidMount() {
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
        this.setState({raceId: obj.id, location: obj.location, distance: obj.distance}, () => {
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
            location: obj.location,
            currentSp: obj.sp,
            id: obj.id
        });
    };

    deleteSp = (obj) => {
        axios.post(Config.DELETE_SP, obj)
            .then((resp) => {
                if (resp.data.result === "success") {
                    let index = this.state.startingPrices.findIndex(item => item.id === obj.id);
                    let middleArray = this.state.startingPrices;
                    middleArray.splice(index, 1);
                    this.setState({startingPrices: middleArray});
                }
            })
    };

    createSP = () => {
        let startingPrice = {
            raceId: this.state.raceId,
            horseId: this.state.horseId,
            sp: this.state.currentSp === "" ? 0 : this.state.currentSp
        };

        axios.post(Config.CREATE_SP, startingPrice)
            .then((resp) => {
                if (resp.data.result === "success") {
                    this.setState({startingPrices: [], horsesSp: [], racesSp: []}, () => {
                        this.getMainContent();
                        let index = this.state.horses.findIndex(item => item.id === startingPrice.horseId);
                        let middleArray = this.state.horses;
                        middleArray.splice(index, 1);
                        this.setState({horses: middleArray});
                    });
                }
            });
    };

    updateSP = () => {
        let updateSP = {
            raceId: this.state.raceId,
            horseId: this.state.horseId,
            sp: this.state.currentSp,
            id: this.state.id
        };

        axios.post(Config.UPDATE_SP, updateSP)
            .then((resp) => {
                if (resp.data.result === "success") {
                    this.setState({
                        startingPrices: this.state.startingPrices.map(el => (el.id === updateSP.id ? updateSP : el))
                    });
                }
            });
    };

    render() {
        const {t} = this.props;
        return (
            <React.Fragment>
                <div className="horse_starting_sub_component">
                        <Table routerPath={"/sphorses"} header={t('TABLE_SP_COLUMNS_HORSE', {returnObjects: true})}
                               data={this.state.horses}
                               chooseItem={this.chooseHorse}/>)}
                </div>
                <div className="horse_starting_sub_component">
                    {this.state.races.length !== 0 ? (
                        <Table routerPath={"/spraces"} header={t('TABLE_SP_COLUMNS_RACES', {returnObjects: true})}
                               data={this.state.races}
                               chooseItem={this.chooseRace}/>) : null}
                </div>
                <div className="horse_starting_main">
                    {this.state.horses.length !== 0 ? (
                        <Table routerPath={"/sp"} header={t('TABLE_SP_COLUMNS', {returnObjects: true})}
                               data={this.state.startingPrices} horses={this.state.horsesSp} races={this.state.racesSp}
                               editItem={this.editSp}
                               deleteItem={this.deleteSp}/>) : null}
                </div>
                <div className={"horse_starting_price_editors"}>
                    <EditTable
                        data={[{name: "location", value: this.state.location, placeholder: t('RACE'), disabled: true}, {
                            name: "name",
                            value: this.state.name,
                            placeholder: t('HORSE_NAME'),
                            disabled: true
                        }, {name: "currentSp", value: this.state.currentSp, placeholder: t('STARTING_PRICE')}]}
                        handleChange={this.handleChange} create={this.createSP}
                        update={this.updateSP}/>
                </div>
            </React.Fragment>
        )
    }
}


export default withTranslation('translation')(EditTableHorseSP);