import React, {Component} from 'react';

class ChooseButton extends Component {

    constructor(props) {
        super(props);
    }

    render() {
        return (
            <React.Fragment>
                <button disabled={!!this.props.disabled} className="btn btn-primary"
                        onClick={() => this.props.chooseItem(this.props.value)}>{this.props.content}
                </button>
            </React.Fragment>
        )
    }
}

export default ChooseButton;