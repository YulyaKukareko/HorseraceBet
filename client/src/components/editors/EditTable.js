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
                        {this.props.select && this.props.select.options &&
                        <select name={this.props.select.name} className="form-control"
                                placeholder={this.props.select.placeholder} onChange={this.props.handleChange}>
                            {this.props.select.options.map((value) => {
                                return (
                                    <option value={value}>{value.charAt(0) + value.slice(1).toLowerCase()}</option>
                                )
                            })};
                        </select>

                        }
                        {this.props.data.map((value) => {
                            return (
                                value.disabled ?
                                    <div className="md-form mt-0">
                                        <input disabled={true} name={value.name} value={value.value} type="text"
                                               className="form-control"
                                               placeholder={value.placeholder} onChange={this.props.handleChange}/>
                                    </div> :
                                    <div className="md-form mt-0">
                                        <input name={value.name} value={value.value} type="text"
                                               className="form-control"
                                               placeholder={value.placeholder} onChange={this.props.handleChange}/>
                                    </div>
                            )
                        })}
                    </div>
                </div>
                <EditButtons create={this.props.create} update={this.props.update}/>
            </div>
        )
    }
}

export default EditTable;