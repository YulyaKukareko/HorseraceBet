import React, {Component} from 'react';
import ChooseButton from './template/ChooseButton'
import {withTranslation} from "react-i18next";

class RowTableSPHorse extends Component {

    constructor(props) {
        super(props);
    }

    render() {
        let {t} = this.props;

        return (
            <tr>
                <td>{this.props.value.id}</td>
                <td>{this.props.value.name}</td>
                <td>{this.props.value.jockey}</td>
                <ChooseButton content={t('CHOOSE_HORSE')} chooseItem={this.props.chooseItem} value={this.props.value}/>
            </tr>
        )
    }
}

export default withTranslation('translation')(RowTableSPHorse);