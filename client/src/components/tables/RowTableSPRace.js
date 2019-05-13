import React, {Component} from 'react';
import ChooseButton from './template/ChooseButton'
import {withTranslation} from "react-i18next";

class RowTableSPRace extends Component {

    constructor(props) {
        super(props);
    }

    render() {
        let {t} = this.props;

        return (
            <tr>
                <td>{this.props.value.id}</td>
                <td>{this.props.value.name}</td>
                <td>{this.props.value.distance}</td>
                <ChooseButton content={t('CHOOSE_RACE')} chooseItem={this.props.chooseItem} value={this.props.value}/>
            </tr>
        )
    }
}

export default withTranslation('translation')(RowTableSPRace);