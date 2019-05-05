import React, {Component} from 'react';
import LanguageSelect from "./template/LanguageSelect";

class MainContent extends Component {
    render() {
        return (
            <main className="main-content">
                <section className="hero hero_image">
                    <div className="container">
                        <div className="hero_content">
                            <div className="block-center">
                                <div className="block-center_content">
                                    <h1 className="hero_title">Horse Racing Odds</h1>
                                    <span className="hero_title small">Get a 10% Signup Bonus and Rebates from <i>bet ★ usracing</i></span>
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

export default MainContent;