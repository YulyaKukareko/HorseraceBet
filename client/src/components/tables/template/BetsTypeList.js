import React, {Component} from 'react';
import '../../../resources/css/table.css';

class BetsTypeList extends Component {

    constructor(props) {
        super(props);
    }

    render() {
        return (
            <React.Fragment>
                <div className={"bettingMode"}>
                    <table border="0" cellPadding="0" cellSpacing="0" width="100%">
                        <tbody>
                        <tr>
                            <td>
                                <div id="dvPools">
                                    <div>
                                        <div id="dvPoolTypeId_0">
                                            <input id="rblPools_0" name={"currentBetType"} type={"radio"} value={"Win"}
                                                   onClick={this.props.chooseBetType}/>
                                            <label htmlFor="rblPools_0">Win</label>
                                        </div>
                                        <div id="dvPoolTypeId_1">
                                            <input id="rblPools_1" name={"currentBetType"} type={"radio"}
                                                   value={"Place"} onClick={this.props.chooseBetType}/>
                                            <label htmlFor="rblPools_1">Place</label>
                                        </div>
                                        <div id="dvPoolTypeId_2">
                                            <input id="rblPools_2" name={"currentBetType"} type={"radio"} value={"Show"}
                                                   onClick={this.props.chooseBetType}/>
                                            <label htmlFor="rblPools_2">Show</label>
                                        </div>
                                        <div id="dvPoolTypeId_3">
                                            <input id="rblPools_3" name={"currentBetType"} type={"radio"}
                                                   value={"Exacta"} onClick={this.props.chooseBetType}/>
                                            <label htmlFor="rblPools_3">Exacta</label>
                                        </div>
                                        <div id="dvPoolTypeId_4">
                                            <input id="rblPools_4" name={"currentBetType"} type={"radio"}
                                                   value={"Opposite"} onClick={this.props.chooseBetType}/>
                                            <label htmlFor="rblPools_4">Opposite</label>
                                        </div>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td className="sTdDescriptionPool">
                                <span id="descriptionPool" className="sSpDescriptionPool">Win / Place / Show payouts are based off of 2. A “Win” bet requires the selected horse to finish in first place. A “Place” bet requires the selected horse to finish in either first or second place. A “Show" bet requires the select horse to finish in either first, second, or third place.</span>
                            </td>
                        </tr>
                        </tbody>
                    </table>
                </div>
            </React.Fragment>
        )
    }
}

export default BetsTypeList;