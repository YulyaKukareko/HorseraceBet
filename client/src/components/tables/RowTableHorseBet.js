import React, {Component} from 'react';
import ChooseButton from "./template/ChooseButton";

class RowTableHorseBet extends Component {

    constructor(props) {
        super(props);
    }

    render() {
        return (
            <tr>
                <td>{this.props.horse.name}</td>
                <td>{this.props.horse.jockey}</td>
                <td>{this.props.horse.trainer}</td>
                <td>{this.props.horse.weight}</td>
                <td>{this.props.value.sp}</td>
                <ChooseButton chooseItem={this.props.chooseItem} value={{
                    id: this.props.value.id,
                    name: this.props.horse.name,
                    jockey: this.props.horse.jockey,
                    sp: this.props.value.sp
                }}/>
            </tr>
        )
    }
}


export default RowTableHorseBet;