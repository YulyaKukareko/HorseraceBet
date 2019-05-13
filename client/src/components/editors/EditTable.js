import React, {Component} from 'react';
import '../../resources/css/table.css'
import EditButtons from './template/EditButtons';

class EditTable extends Component {

    constructor(props) {
        super(props);
    }

    render() {
        return (
            <div className={this.props.editStyle}>
                <div className="row">
                    <div className="col">
                        {this.props.select && this.props.select.options ?
                            (<React.Fragment>
                                <select name={this.props.select.name} className="form-control"
                                        value={this.props.select.value} onChange={this.props.handleChange}>
                                    <option defaultChecked={""}>
                                        {this.props.select.placeholder}...
                                    </option>
                                    {this.props.select.options.map((value) => {
                                        return (
                                            <option
                                                value={value}>{value.charAt(0) + value.slice(1).toLowerCase()}
                                            </option>
                                        )
                                    })};
                                </select>
                                {this.props.select.validator}
                            </React.Fragment>) : null
                        }
                        {this.props.data.map((value) => {
                            return (
                                <React.Fragment>
                                    <div className="md-form mt-0">
                                        <input disabled={value.disabled} name={value.name} value={value.value}
                                               type="text" className="form-control" placeholder={value.placeholder}
                                               onChange={this.props.handleChange}/>
                                    </div>
                                    {value.validator}
                                </React.Fragment>
                            )
                        })}
                    </div>
                </div>
                <EditButtons disableCreate={this.props.disableCreate} disableSave={this.props.disableSave}
                             create={this.props.create} update={this.props.update}/>
            </div>
        )
    }
}

export default EditTable;