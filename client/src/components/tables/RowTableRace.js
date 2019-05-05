import React, {Component} from 'react';
import EditButtons from './template/EditButtons'

class RowTableRace extends Component {

    constructor(props) {
        super(props);
    }

    render() {
        let formattedTime = this.props.value.time.substring(0, this.props.value.time.length - 5);
        return (
            <tr>
                <td>{this.props.value.location}</td>
                <td>{this.props.value.type.charAt(0) + this.props.value.type.slice(1).toLowerCase()}</td>
                <td>{this.props.value.distance}</td>
                <td>{this.props.value.purse}</td>
                <td>{formattedTime}</td>
                <EditButtons value={this.props.value} editItem={this.props.editItem}
                             deleteItem={this.props.deleteItem}/>
            </tr>
        )
    }
}


export default RowTableRace;