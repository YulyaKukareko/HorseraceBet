import React, {Component} from 'react';
import EditButtons from './template/EditButtons';

class RowTableHorse extends Component {

    constructor(props) {
        super(props);
    }

    render() {
        return (
            <tr>
                <td>{this.props.value.name}</td>
                <td>{this.props.value.jockey}</td>
                <td>{this.props.value.trainer}</td>
                <td>{this.props.value.weight}</td>
                <EditButtons value={this.props.value} editItem={this.props.editItem}
                             deleteItem={this.props.deleteItem}/>
            </tr>
        )
    }
}

export default RowTableHorse;