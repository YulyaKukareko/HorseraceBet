import React, {Component} from 'react';

class EditButtons extends Component {

    constructor(props) {
        super(props);
    }

    render() {
        return (
            <React.Fragment>
                <td>
                    <div>
                        <button className="btn btn-primary"
                                onClick={() => this.props.editItem(this.props.value)}>Edit
                        </button>
                    </div>
                </td>
                <td>
                    <div>
                        <button className="btn btn-danger"
                                onClick={() => this.props.deleteItem(this.props.value)}>Delete
                        </button>
                    </div>
                </td>
            </React.Fragment>
        )
    }
}

export default EditButtons;