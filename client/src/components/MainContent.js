import React, {Component} from 'react';
import LanguageSelect from "./template/LanguageSelect";
import {withTranslation} from "react-i18next";

class MainContent extends Component {

    render() {
        let {t} = this.props;

        return (
            <main className="main-content">
                <section className="hero hero_image">
                    <div className="container">
                        <div className="hero_content">
                            <div className="block-center">
                                <div className="block-center_content">
                                    <h1 className="hero_title">{t('MAIN_CONTENT_TITLE')}</h1>
                                    <span className="hero_title small">{t('MAIN_CONTENT')} <i>bet ★ usracing</i></span>
                                </div>
                            </div>
                        </div>
                        <div className={"select_language"}>
                            <LanguageSelect defaultFlag={this.props.defaultFlag}
                                            changeDefaultFlag={this.props.changeDefaultFlag}/>
                        </div>
                    </div>
                </section>
            </main>
        )
    }
}

export default withTranslation('translation')(MainContent);