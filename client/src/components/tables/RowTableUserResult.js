import React, {Component} from 'react';

class RowTableUserResult extends Component {

    constructor(props) {
        super(props);
    }

    render() {
        return (
            <tr>
                <td>{this.props.value.place}</td>
                <td>{this.props.horse.name}</td>
                <td>{this.props.horse.jockey}</td>
                <td>{this.props.horse.trainer}</td>
            </tr>
        )
    }
}


export default RowTableUserResult;