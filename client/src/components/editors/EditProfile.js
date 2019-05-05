import React, {Component} from 'react';
import '../../resources/css/table.css'

class EditProfile extends Component {

    constructor(props) {
        super(props);
    }

    render() {
        return (
            <React.Fragment>
                {this.props.data.map((value) => {
                    return (
                        <div className="form-group">
                            <label className="col-lg-3 control-label">{value.placeholder}</label>
                            <div className="col-lg-8">
                                <input className="form-control" name={value.name}
                                       type={value.name !== "password" ? "text" : "password"}
                                       value={value.editField} onChange={this.props.handleChange}/>
                            </div>
                        </div>

                    )
                })}
                <div className="form-group">
                    <label className="col-md-3 control-label"/>
                    <div className="col-md-8">
                        <input type="submit" className="btn btn-primary" value="Save Changes"/>
                    </div>
                </div>
            </React.Fragment>
        )
    }
}

export default EditProfile;