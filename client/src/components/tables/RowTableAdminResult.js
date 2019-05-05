import React, {Component} from 'react';
import ChooseButton from "./template/ChooseButton";

class RowTableAdminResult extends Component {

    constructor(props) {
        super(props);
    }

    render() {
        return (
            <tr>
                <td>{this.props.value}</td>
                <td>{this.props.horse.name}</td>
                <td>{this.props.horse.jockey}</td>
                <ChooseButton disabled={this.props.disabled} chooseItem={this.props.chooseItem}
                              value={{place: this.props.value}}/>
            </tr>
        )
    }
}


export default RowTableAdminResult;