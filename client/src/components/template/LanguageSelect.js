import React, {Component} from 'react';
import {withTranslation} from "react-i18next";
import ReactFlagsSelect from "react-flags-select";
import i18n from "../../resources/localization/i18n";

class LanguageSelect extends Component {

    constructor(props) {
        super(props);
    }

    onSelectFlag = (countryCode) => {
        i18n.changeLanguage(countryCode.toLowerCase());
        this.props.changeDefaultFlag(countryCode);
    };

    render() {
        return (
            <ReactFlagsSelect defaultCountry={this.props.defaultFlag} onSelect={this.onSelectFlag}
                              countries={["US", "RU"]} showSelectedLabel={false} showOptionLabel={false}
                              selectedSize={20}/>
        )
    }
}

export default withTranslation('translation')(LanguageSelect);