import React, {Component} from 'react';
import '../resources/css/table.css'
import Config from "../config/Config";
import axios from 'axios';
import Table from '../components/tables/template/Table';
import EditTable from '../components/editors/EditTable';
import {withTranslation} from "react-i18next";
import getLocalizationErrorMessage from '../common/APIUtils';

class EditTableHorse extends Component {
    constructor(props) {
        super(props);

        this.state = {
            horses: [],
            trainer: "",
            jockey: "",
            weight: "",
            name: "",
            id: "",
            ajaxError: "",
            translate: this.props.t
        };
        this.props.validator.hideMessages();
        this.props.validator.purgeFields();
    }

    componentWillMount() {
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
                if (resp.data.status === "success") {

                    let index = this.state.horses.findIndex(item => item.id === obj.id);
                    let middleArray = this.state.horses;
                    middleArray.splice(index, 1);

                    this.setState({horses: middleArray});
                    this.setState({ajaxError: ""});
                } else {
                    this.setState({
                        ajaxError: getLocalizationErrorMessage(this.state.translate, resp.data.errorMes)
                    });
                    this.props.validator.showMessages();
                }
            })
    };

    createHorse = () => {
        this.props.validator.showMessages();

        if (this.props.validator.allValid()) {
            let horse = {
                name: this.state.name,
                jockey: this.state.jockey,
                trainer: this.state.trainer,
                weight: this.state.weight
            };

            axios.post(Config.CREATE_HORSE, horse)
                .then((resp) => {
                    if (resp.data.status === "success") {
                        this.getMainContent();
                        this.setState({ajaxError: ""});
                    } else {
                        this.setState({ajaxError: getLocalizationErrorMessage(this.state.translate, resp.data.errorMes)});
                    }
                });
        } else {
            this.forceUpdate();
        }
    };

    updateHorse = () => {
        this.props.validator.showMessages();
        if (this.props.validator.allValid()) {
            let updateHorse = {
                id: this.state.id,
                name: this.state.name,
                jockey: this.state.jockey,
                trainer: this.state.trainer,
                weight: this.state.weight
            };

            axios.post(Config.UPDATE_HORSE, updateHorse)
                .then((resp) => {
                    if (resp.data.status === "success") {
                        this.setState({ajaxError: ""});
                        this.setState({
                            horses: this.state.horses.map(el => (el.id === updateHorse.id ? updateHorse : el))
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
        let t = this.state.translate;

        return (
            <React.Fragment>
                <div className="main_table">
                    {
                        this.state.horses.length !== 0 &&
                        <Table routerPath={"/horses"}
                               header={t('TABLE_EDIT_HORSE_COLUMNS', {returnObjects: true})}
                               data={this.state.horses}
                               editItem={this.editHorse}
                               deleteItem={this.deleteHorse}/>
                    }
                </div>
                <EditTable editStyle={"edit"}
                           data={[{
                               name: "trainer",
                               value: this.state.trainer,
                               placeholder: t('TRAINER'),
                               validator: this.props.validator.message("trainer", this.state.trainer, 'required')
                           }, {
                               name: "jockey",
                               value: this.state.jockey,
                               placeholder: t('JOCKEY'),
                               validator: this.props.validator.message("jockey", this.state.jockey, 'required')
                           }, {
                               name: "weight", value: this.state.weight, placeholder: t('WEIGHT'),
                               validator: this.props.validator.message("weight", this.state.weight, 'required|numeric')
                           }, {
                               name: "name",
                               value: this.state.name,
                               placeholder: t('NAME'),
                               validator: this.props.validator.message("name", this.state.name, 'required')
                           }]}
                           handleChange={this.handleChange} create={this.createHorse}
                           update={this.updateHorse}/>
                {
                    this.props.validator.messageWhenPresent(this.state.ajaxError, {
                        element: message => <div className="ajax_error">{message}</div>
                    })
                }
            </React.Fragment>
        )
    }
}


export default withTranslation('translation')(EditTableHorse);