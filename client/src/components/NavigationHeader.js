import React, {Component} from 'react';
import SignUpButton from "./template/SignUpButton";
import NavigationMenu from "./template/NavigationMenu";
import Logo from "./Logo";


class NavigationHeader extends Component {
    constructor(props) {
        super(props);
    }

    render() {
        return (
            <nav className="navbar fixed-top navbar-toggleable-md navbar-light bg-faded">
                <Logo/>
                <NavigationMenu menuItems={this.props.menuItems}/>
                <SignUpButton/>
            </nav>
        )
    }
}

export default NavigationHeader;