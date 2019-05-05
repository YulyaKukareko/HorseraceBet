import React, {Component} from 'react';
import ChooseButton from './template/ChooseButton'

class RowTableSPRace extends Component {

    constructor(props) {
        super(props);
    }

    render() {
        return (
            <tr>
                <td>{this.props.value.id}</td>
                <td>{this.props.value.location}</td>
                <td>{this.props.value.distance}</td>
                <ChooseButton chooseItem={this.props.chooseItem} value={this.props.value}/>
            </tr>
        )
    }
}


export default RowTableSPRace;