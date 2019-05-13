import React, {Component} from 'react';
import '../../../resources/css/table.css';

class RacesList extends Component {

    constructor(props) {
        super(props);
    }

    render() {
        return (
            <div className={"sDvMenuTrackListContent"}>
                <div className={"sDvMenuTrackListHeader"}>
                    {this.props.content}
                </div>
                <div className={"sTlbTracksMenu"}>
                    {this.props.races.map((value, index) => {
                        return (
                            <a onClick={() => this.props.chooseLocation({
                                race: value,
                                country: this.props.countries[index]
                            })}>
                                <div>
                                    <div>{value.name}</div>
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