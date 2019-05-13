import React, {Component} from 'react';
import {Link} from "react-router-dom";
import {withTranslation} from "react-i18next";

class SignUpButton extends Component {

    constructor(props) {
        super(props);
    }

    render() {
        const {t} = this.props;

        return (
            <Link to={"/"} className={"header_btn_sign_up header_signup"}>{t('SIGN_UP')}</Link>
        )
    }
}

export default withTranslation('translation')(SignUpButton);