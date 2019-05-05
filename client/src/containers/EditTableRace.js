import React, {Component} from 'react';
import '../resources/css/table.css'
import Config from "../config/Config";
import axios from 'axios';
import Table from "../components/tables/template/Table";
import EditTable from '../components/editors/EditTable';
import {withTranslation} from "react-i18next";

class EditTableRace extends Component {

    constructor(props) {
        super(props);
        this.state = {
            races: [],
            type: "",
            location: "",
            distance: "",
            purse: "",
            time: "",
            id: "",
            raceTypes: []
        };
    }

    componentDidMount() {
        this.getMainContent();
        axios.get(Config.GET_RACE_TYPES).then((resp) => {
            this.setState({raceTypes: resp.data.result});
        })
    }

    handleChange = (event) => {
        this.setState({[event.target.name]: event.target.value});
    };

    getMainContent = () => {
        axios.get(Config.GET_RACES).then((resp) => {
            this.setState({races: resp.data.result});
        });
    };

    editRace = (obj) => {
        this.setState({
            location: obj.location,
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
                if (resp.data.result === "success") {
                    let index = this.state.races.findIndex(item => item.id === obj.id);
                    let middleArray = this.state.races;
                    middleArray.splice(index, 1);
                    this.setState({races: middleArray});
                }
            })
    };

    createRace = () => {
        let race = {
            location: this.state.location,
            distance: this.state.distance,
            type: this.state.type.toUpperCase(),
            purse: this.state.purse,
            time: this.state.time
        };

        axios.post(Config.CREATE_RACE, race)
            .then(() => {
                this.getMainContent();
            });
    };

    updateRace = () => {
        let updateRace = {
            id: this.state.id,
            location: this.state.location,
            distance: this.state.distance,
            type: this.state.type,
            purse: this.state.purse,
            time: this.state.time
        };

        axios.post(Config.UPDATE_RACE, updateRace)
            .then((resp) => {
                if (resp.data.result === "success") {
                    this.setState({
                        races: this.state.races.map(el => (el.id === updateRace.id ? updateRace : el))
                    });
                }
            });
    };

    render() {
        const {t} = this.props;
        return (
            <React.Fragment>
                <div className="main_table">
                    {this.state.races.length !== 0 ? (
                        <Table routerPath={"/races"} header={t('TABLE_RACE_COLUMNS', {returnObjects: true})}
                               data={this.state.races}
                               editItem={this.editRace}
                               deleteItem={this.deleteRace}/>) : null}
                </div>
                <EditTable editStyle={"edit"}
                           data={[{name: "location", value: this.state.location, placeholder: t('LOCATION')}, {
                               name: "distance",
                               value: this.state.distance,
                               placeholder: t('DISTANCE')
                           }, {name: "purse", value: this.state.purse, placeholder: t('PURSE')}, {
                               name: "time",
                               value: this.state.time,
                               placeholder: t('TIME')
                           }]}
                           select={{name: "type", placeholder: t('RACE_TYPE'), options: this.state.raceTypes}}
                           handleChange={this.handleChange} create={this.createRace}
                           update={this.updateRace}/>
            </React.Fragment>
        )

    }
}


export default withTranslation('translation')(EditTableRace);