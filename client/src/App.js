import React, {Component} from 'react';
import {BrowserRouter as Router, Route, Switch} from "react-router-dom";
import './resources/css/signin.css';
import SignIn from './components/SignIn';
import SignUp from './components/SignUp';
import Main from './containers/Main';
import NoMatch from "./containers/NoMatch";
import {createValidator} from "./common/Validator";
import {withTranslation} from "react-i18next";

class App extends Component {

    constructor(props) {
        super(props);

        this.state = {
            defaultFlag: "US",
            validator: createValidator(this.props.t)
        }
    }

    changeDefaultFlag = (codeCountry) => {
        this.setState({defaultFlag: codeCountry}, () => {
            this.setState({validator: createValidator(this.props.t)});
        });
    };

    render() {
        return (
            <Router basename={"/server_war"}>
                <Switch>
                    <Route path={"/auth"} render={(props) => <Main {...props} validator={this.state.validator}
                                                                   defaultFlag={this.state.defaultFlag}
                                                                   changeDefaultFlag={this.changeDefaultFlag}/>}/>
                    <Route path={"/signup"} render={(props) => <SignUp {...props} validator={this.state.validator}
                                                                       defaultFlag={this.state.defaultFlag}
                                                                       changeDefaultFlag={this.changeDefaultFlag}/>}/>
                    <Route exact path={"/"} render={(props) => <SignIn {...props} validator={this.state.validator}
                                                                       defaultFlag={this.state.defaultFlag}
                                                                       changeDefaultFlag={this.changeDefaultFlag}/>}/>
                    <Route component={NoMatch}/>
                </Switch>
            </Router>
        );
    }
}

export default withTranslation('translation')(App);
