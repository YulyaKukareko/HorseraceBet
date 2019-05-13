import React, {Component} from 'react';
import '../../../resources/css/table.css'

class Header extends Component {

    constructor(props) {
        super(props);
    }

    render() {
        return (
            <tr>
                {
                    this.props.header.map((value) => {
                        return (
                            <th scope="col">{value}</th>
                        );
                })}
            </tr>
        )
    }
}

export default Header;