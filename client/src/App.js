import React, { Component } from 'react';
import axios from 'axios';
class App extends Component {

    componentDidMount() {
        axios.get("http://localhost:8080/server_war/hello");
    }

    render() {
        return (
            <div>
                <header >
                    <p>
                        Edit <code>src/App.js</code> asd
                    </p>
                </header>
            </div>
        );
    }
}

export default App;
