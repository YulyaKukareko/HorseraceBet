import React, {Component} from 'react';
import '../../../resources/css/table.css'
import {withTranslation} from "react-i18next";

class EditButtons extends Component {

    constructor(props) {
        super(props);
    }

    render() {
        const {t} = this.props;
        return (
            <div className="buttons">
                {
                    this.props.disableCreate === true ? null :
                        <button className="btn btn-secondary btn-success"
                                onClick={this.props.create}>{t('CREATE')}
                        </button>
                }
                {
                    this.props.disableSave === true ? null :
                        <button className="btn btn-secondary horse_edit_btn btn-success" onClick={this.props.update}>
                            {t('SAVE')}
                        </button>
                }
            </div>
        )
    }
}

export default withTranslation('translation')(EditButtons);
