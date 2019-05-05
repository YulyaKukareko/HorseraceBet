import React, {Component} from 'react';
import '../resources/css/table.css'
import Config from "../config/Config";
import axios from 'axios';
import Table from '../components/tables/template/Table';
import EditTable from '../components/editors/EditTable';
import {withTranslation} from "react-i18next";

class EditTableHorse extends Component {
    constructor(props) {
        super(props);
        this.state = {horses: [], trainer: "", jockey: "", weight: "", name: "", id: ""};
    }

    componentDidMount() {
        this.getMainContent();
    }

    getMainContent = () => {
        axios.get(Config.GET_HORSES).then((resp) => {
            this.setState({horses: resp.data.result});
        });
    };

    handleChange = (event) => {
        this.setState({[event.target.name]: event.target.value});
    };

    editHorse = (obj) => {
        this.setState({trainer: obj.trainer, jockey: obj.jockey, weight: obj.weight, name: obj.name, id: obj.id});
    };

    deleteHorse = (obj) => {
        axios.post(Config.DELETE_HORSE, obj)
            .then((resp) => {
                if (resp.data.result === "success") {
                    let index = this.state.horses.findIndex(item => item.id === obj.id);
                    let middleArray = this.state.horses;
                    middleArray.splice(index, 1);
                    this.setState({horses: middleArray});
                }
            })
    };

    createHorse = () => {
        debugger;
        let horse = {
            name: this.state.name,
            jockey: this.state.jockey,
            trainer: this.state.trainer,
            weight: this.state.weight
        };

        axios.post(Config.CREATE_HORSE, horse)
            .then((resp) => {
                if (resp.data.result === "success") {
                    this.getMainContent();
                }
            });
    };

    updateHorse = () => {
        let updateHorse = {
            id: this.state.id,
            name: this.state.name,
            jockey: this.state.jockey,
            trainer: this.state.trainer,
            weight: this.state.weight
        };

        axios.post(Config.UPDATE_HORSE, updateHorse)
            .then((resp) => {
                if (resp.data.result === "success") {
                    this.setState({
                        horses: this.state.horses.map(el => (el.id === updateHorse.id ? updateHorse : el))
                    });
                }
            });
    };


    render() {
        const {t} = this.props;
        return (
            <React.Fragment>
                <div className="main_table">
                    {this.state.horses.length !== 0 ? (
                        <Table routerPath={"/horses"} header={t('TABLE_EDIT_HORSE_COLUMNS', {returnObjects: true})}
                               data={this.state.horses}
                               editItem={this.editHorse}
                               deleteItem={this.deleteHorse}/>) : null
                    }
                </div>
                <EditTable editStyle={"edit"}
                           data={[{name: "trainer", value: this.state.trainer, placeholder: t('TRAINER')}, {
                               name: "jockey",
                               value: this.state.jockey,
                               placeholder: t('JOCKEY')
                           }, {name: "weight", value: this.state.weight, placeholder: t('WEIGHT')}, {
                               name: "name",
                               value: this.state.name,
                               placeholder: t('NAME')
                           }]}
                           handleChange={this.handleChange} create={this.createHorse}
                           update={this.updateHorse}/>
            </React.Fragment>
        )
    }
}


export default withTranslation('translation')(EditTableHorse);