import React, {Component} from 'react';
import '../../../resources/css/table.css';

class RacesList extends Component {

    constructor(props) {
        super(props);
    }

    render() {
        return (
            <div className={"sDvMenuTrackListContent"}>
                <div className={"sDvMenuTrackListHeader"}>{this.props.content}</div>
                <div className={"sTlbTracksMenu"}>
                    {this.props.races.map((value) => {
                        return (
                            <a onClick={() => this.props.chooseLocation(value)}>
                                <div>
                                    <div>{value.location}</div>
                                </div>
                            </a>
                        )
                    })}
                </div>
            </div>
        )
    }
}

export default RacesList;