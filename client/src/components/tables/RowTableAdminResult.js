import React, {Component} from 'react';
import ChooseButton from "./template/ChooseButton";
import {withTranslation} from "react-i18next";

class RowTableAdminResult extends Component {

    constructor(props) {
        super(props);
    }

    render() {
        let {t} = this.props;

        return (
            <tr>
                <td>{this.props.value}</td>
                <td>{this.props.horse.name}</td>
                <td>{this.props.horse.jockey}</td>
                <ChooseButton content={t('ADD')} disabled={this.props.disabled} chooseItem={this.props.chooseItem}
                              value={{place: this.props.value}}/>
            </tr>
        )
    }
}

export default withTranslation('translation')(RowTableAdminResult);