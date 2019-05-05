import React, {Component} from 'react';
import '../../../resources/css/table.css';

class RaceInfo extends Component {

    constructor(props) {
        super(props);
    }

    render() {
        return (
            <React.Fragment>
                <div className={"sTdMainRaceInfomation"}>
                    <div className={"sDvRaceInformation"}>
                        <table>
                            <tbody>
                            <tr>
                                <td width="60px" align="left" valign="bottom">
                                    <span id="lblType" className="sLblRaceInformationTitle"
                                          style={{display: "inline-block", width: 50 + 'px'}}>{this.props.type}</span>
                                </td>
                                <td width="60px" align="left" valign="bottom">
                                    <span id="lblType" className="sLblRaceInformationTitle"
                                          style={{display: "inline-block", width: 50 + 'px'}}>{this.props.location}</span>
                                </td>
                                <td width="60px" align="left" valign="bottom">
                                    <span id="lblType" className="sLblRaceInformationTitle"
                                          style={{display: "inline-block", width: 50 + 'px'}}>{this.props.purse}</span>
                                </td>
                            </tr>
                            <tr>
                                <td width="110px" className="sTdRaceInformation">
                                    <span id="RaceType">{this.props.race.type}</span>
                                </td>
                                <td width="110px" className="sTdRaceInformation">
                                    <span id="RaceType">{this.props.race.location}</span>
                                </td>
                                <td width="110px" className="sTdRaceInformation">
                                    <span id="RaceType">{this.props.race.purse}$</span>
                                </td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
            </React.Fragment>
        )
    }
}

export default RaceInfo;