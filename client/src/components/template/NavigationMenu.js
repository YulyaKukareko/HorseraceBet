import React, {Component} from 'react';
import {Link} from "react-router-dom";

class NavigationMenu extends Component {

    constructor(props) {
        super(props);
    }

    render() {
        return (
            <div className="collapse navbar-collapse">
                <ul className="navbar-nav">
                    {
                        this.props.menuItems.map((value) => {
                                return (
                                    <li className="nav-item">
                                        <Link to={value.path} className="nav-link">{value.menuHeader}</Link>
                                    </li>
                                )
                            }
                        )
                    }
                </ul>
            </div>
        )
    }
}

export default NavigationMenu;