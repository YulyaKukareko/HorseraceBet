import React, {Component} from 'react';
import '../../../resources/css/table.css';
import {withTranslation} from "react-i18next";

class BetsTypeList extends Component {

    constructor(props) {
        super(props);
    }

    render() {
        let {t} = this.props;
        return (
            <React.Fragment>
                <div className={"bettingMode"}>
                    <table className={"bets_type_list"}>
                        <tbody>
                        <tr>
                            <td>
                                <div id="dvPools">
                                    <div>
                                        {this.props.data.map((value, index) => {
                                            return (
                                                <div>
                                                    <input id={"item" + index} name={"currentBetType"} type={"radio"}
                                                           value={value}
                                                           onClick={this.props.chooseBetType}/>
                                                    <label htmlFor={"item" + index}>
                                                        {value}
                                                    </label>
                                                </div>
                                            );
                                        })}
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td className="sTdDescriptionPool">
                                <span id="descriptionPool" className="sSpDescriptionPool">
                                    {t('BETS_INFO')}
                                </span>
                            </td>
                        </tr>
                        </tbody>
                    </table>
                </div>
            </React.Fragment>
        )
    }
}

export default withTranslation('translation')(BetsTypeList);