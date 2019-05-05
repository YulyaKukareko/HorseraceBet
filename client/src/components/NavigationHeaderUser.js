import React, {Component} from 'react';
import {Link} from "react-router-dom";
import Logo from './Logo';
import SignUpButton from './template/SignUpButton';
import NavigationMenu from "./template/NavigationMenu";

class NavigationHeaderUser extends Component {
    constructor(props) {
        super(props);
        this.state = {
            redirect: false
        }
    }

    render() {
        return (
            <nav className="navbar fixed-top navbar-toggleable-md navbar-light bg-faded">
                <Logo/>
                <NavigationMenu menuItems={this.props.menuItems}/>
                {this.props.user &&
                <div className="col-2 dk-header__not-padding-left">
                    <div className="text-center dk-header__bookie-btn">
                        <Link to={"/auth/user/profile"}
                              className="btn btn-primary dropdown-toggle dk-header__myaccount">
                            <span className="fa fa-user"/>
                        </Link>
                        <div className="text-right dk-header__bookie-money pull-right hidden-sm">
                            <p className="dk-header__text">
                                <strong className="dk-header__username">{this.props.user.firstName}</strong>
                                <span
                                    className="dk-header__money-text amountAvailable">${this.props.user.balance}</span>
                            </p>
                        </div>
                    </div>
                </div>}
                <SignUpButton/>
            </nav>
        )
    }
}

export default NavigationHeaderUser;

