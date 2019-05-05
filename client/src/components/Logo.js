import React, {Component} from 'react';

class Logo extends Component {
    constructor(props) {
        super(props);
    }

    render() {
        return (
            <a className="navbar-brand">
                <img className="logo" src={require("../resources/img/logo_v2.png")}/>
            </a>
        )
    }
}

export default Logo;