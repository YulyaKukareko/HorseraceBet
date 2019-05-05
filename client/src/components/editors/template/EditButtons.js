import React, {Component} from 'react';
import '../../../resources/css/table.css'

class EditButtons extends Component {

    constructor(props) {
        super(props);
    }

    render() {
        return (
            <div className="buttons">
                <button className="btn btn-secondary btn-success" onClick={this.props.create}>Create</button>
                <button className="btn btn-secondary horse_edit_btn btn-success" onClick={this.props.update}>Save
                </button>
            </div>
        )
    }
}

export default EditButtons;
