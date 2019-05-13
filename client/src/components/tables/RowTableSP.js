import React, {Component} from 'react';
import EditButtons from "./template/EditButtons";

class RowTableSP extends Component {

    constructor(props) {
        super(props);
    }

    render() {
        return (
            <tr>
                <td>{this.props.value.id}</td>
                <td>{this.props.horse.id}</td>
                <td>{this.props.horse.name}</td>
                <td>{this.props.race.id}</td>
                <td>{this.props.race.name}</td>
                <td>{this.props.value.sp}</td>
                <EditButtons value={{
                    id: this.props.value.id,
                    raceId: this.props.race.id,
                    horseId: this.props.horse.id,
                    name: this.props.horse.name,
                    raceName: this.props.race.name,
                    location: this.props.race.location,
                    sp: this.props.value.sp
                }} editItem={this.props.editItem} deleteItem={this.props.deleteItem}/>
            </tr>
        )
    }
}

export default RowTableSP;