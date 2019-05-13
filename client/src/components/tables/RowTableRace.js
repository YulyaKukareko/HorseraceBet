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
                <td>{this.props.value.name}</td>
                <td>{this.props.country.name}</td>
                <td>{this.props.value.type.charAt(0) + this.props.value.type.slice(1).toLowerCase()}</td>
                <td>{this.props.value.distance}</td>
                <td>${this.props.value.purse}</td>
                <td>{formattedTime}</td>
                <EditButtons value={{
                    id: this.props.value.id,
                    name: this.props.value.name,
                    countryId: this.props.value.countryId,
                    countryName: this.props.country.name,
                    type: this.props.value.type,
                    distance: this.props.value.distance,
                    purse: this.props.value.purse,
                    time: this.props.value.time
                }} editItem={this.props.editItem} deleteItem={this.props.deleteItem}/>
            </tr>
        )
    }
}

export default RowTableRace;