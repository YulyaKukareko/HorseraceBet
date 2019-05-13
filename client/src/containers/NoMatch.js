import React, { Component } from 'react';
import '../resources/css/notfound.css';
import {withTranslation} from "react-i18next";

class NoMatch extends Component {

    render() {
        const {t} = this.props;

        return (
            <div id="notfound">
                <div className="notfound">
                    <div className="notfound-404">
                        <h1>4<span>0</span>4</h1>
                    </div>
                    <p>
                        {t('ERROR_MESSAGE_404')}
                    </p>
                    <a href="http://localhost:8080/server_war/">{t('HOME_PAGE')}</a>
                </div>
            </div>
        );
    }
}

export default withTranslation('translation')(NoMatch);
