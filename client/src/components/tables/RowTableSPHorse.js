import React, {Component} from 'react';
import ChooseButton from './template/ChooseButton'

class RowTableSPHorse extends Component {

    constructor(props) {
        super(props);
    }

    render() {
        return (
            <tr>
                <td>{this.props.value.id}</td>
                <td>{this.props.value.name}</td>
                <td>{this.props.value.jockey}</td>
                <ChooseButton chooseItem={this.props.chooseItem} value={this.props.value}/>
            </tr>
        )
    }
}


export default RowTableSPHorse;