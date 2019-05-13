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
                        <React.Fragment>
                            <div className="form-group">
                                <label className="col-lg-3 control-label">
                                    {value.placeholder}
                                </label>
                                <div className="col-lg-8">
                                    <input disabled={value.disabled === true} className="form-control" name={value.name}
                                           type={value.name !== "password" ? "text" : "password"}
                                           value={value.editField} onChange={this.props.handleChange}/>
                                </div>
                            </div>
                            {value.validator}
                        </React.Fragment>
                    )
                })}
                <div className="form-group">
                    <label className="col-md-3 control-label"/>
                    <div className="col-md-8">
                        <input type="submit" className="btn btn-primary" value={this.props.contentButton}/>
                    </div>
                </div>
            </React.Fragment>
        )
    }
}

export default EditProfile;