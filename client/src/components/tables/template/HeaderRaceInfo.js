import React, {Component} from 'react';

class HeaderRaceInfo extends Component {

    constructor(props) {
        super(props);
    }

    render() {
        let formattedTime = this.props.race.time.substring(0, this.props.race.time.length - 5);
        return (
            <React.Fragment>
                <div className={"sDvRaceInformationHeader"}>
                    <table>
                        <tbody>
                        <tr><span className={"sLblTrackNameTitle"}>{this.props.race.location}</span></tr>
                        <tr><span className={"sLblMTP"}>{this.props.date}: {formattedTime}</span></tr>
                        </tbody>
                    </table>
                </div>
            </React.Fragment>
        )
    }
}

export default HeaderRaceInfo;