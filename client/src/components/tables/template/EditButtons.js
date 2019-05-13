import React, {Component} from 'react';
import {withTranslation} from "react-i18next";

class EditButtons extends Component {

    constructor(props) {
        super(props);
    }

    render() {
        const {t} = this.props;
        return (
            <React.Fragment>
                <td>
                    <div>
                        <button className="btn btn-primary"
                                onClick={() => this.props.editItem(this.props.value)}>{t('EDIT')}
                        </button>
                    </div>
                </td>
                {this.props.disableDeleted !== true ? (<td>
                    <div>
                        <button className="btn btn-danger"
                                onClick={() => this.props.deleteItem(this.props.value)}>{t('DELETE')}
                        </button>
                    </div>
                </td>) : null}
            </React.Fragment>
        )
    }
}

export default withTranslation('translation')(EditButtons);