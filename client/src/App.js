import React, { Component } from 'react';
import { BrowserRouter as Router, Route } from "react-router-dom";
import './resources/css/signin.css';
import SignIn from './components/SignIn';
import SignUp from './components/SignUp';
import Main from './containers/Main';

class App extends Component {

    constructor(props){
        super(props);
        this.state = {
            defaultFlag: "US"
        }
    }

    changeDefaultFlag = (codeCountry) => {
        this.setState({defaultFlag: codeCountry});
    };

    render() {
        return (
            <Router basename={"/server_war"}>
                <Route path={"/auth"} render={(props) => <Main {...props} defaultFlag={this.state.defaultFlag} changeDefaultFlag={this.changeDefaultFlag}/>} />
                <Route path={"/signup"} render={(props) => <SignUp {...props} defaultFlag={this.state.defaultFlag} changeDefaultFlag={this.changeDefaultFlag}/>} />
                <Route exact path={"/"} render={(props) => <SignIn {...props} defaultFlag={this.state.defaultFlag} changeDefaultFlag={this.changeDefaultFlag}/>} />
            </Router>
        );
    }
}

export default App;
