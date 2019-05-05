import React, {Component} from 'react';
import {Route} from "react-router-dom";
import NavigationHeader from '../components/NavigationHeader';
import Config from '../config/Config';
import MainContent from "../components/MainContent";
import '../resources/css/main.css';
import EditTableHorse from './EditTableHorse';
import EditTableRace from "./EditTableRace";
import EditTableHorseSP from "./EditTableHorseSP";
import EditTableBet from "./EditTableBet";
import EditTableResult from "./EditTableResult";
import NavigationHeaderUser from "../components/NavigationHeaderUser";
import UserBet from "./UserBet";
import axios from 'axios';
import HistoryBets from "./HistoryBets";
import EditUserProfile from "./EditUserProfile";
import BetResult from "./BetResult";
import {Redirect} from 'react-router-dom';
import {ClipLoader} from 'react-spinners';
import {withTranslation} from "react-i18next";

class Main extends Component {

    constructor(props) {
        super(props);
        this.state = {
            user: "",
            isLoading: true
        }
    }

    componentDidMount() {
        document.body.className = "body_class_header";
        this.getCurrentUserState();
    }

    getCurrentUserState = () => {
        axios.get(Config.GET_USER_BY_ID)
            .then((resp) => {
                if (resp.data.result) {
                    this.setState({user: resp.data.result, isLoading: false});
                } else {
                    this.setState({isLoading: false});
                }
            })
    };

    render() {
        const {t} = this.props;
        return (
            <div>
                <Route path={this.props.match.url + "/bookmaker"}
                       render={() => this.state.user.role && this.state.user.role.toLowerCase() === "bookmaker" ?
                           (<div>
                               <NavigationHeader menuItems={t('BOOKMAKER_MENU_ITEMS', {returnObjects: true})}/>
                               <MainContent defaultFlag={this.props.defaultFlag}
                                            changeDefaultFlag={this.props.changeDefaultFlag}/>
                               <div id="mainDiv"/>
                               <div className={"table_edit_area"}>
                                   <Route exact path={this.props.match.url + "/bookmaker/horse"}
                                          render={() => <EditTableHorse/>}/>
                                   <Route exact path={this.props.match.url + "/bookmaker/race"}
                                          render={() => <EditTableRace/>}/>
                                   <Route exact path={this.props.match.url + "/bookmaker/sphorse"}
                                          render={() => <EditTableHorseSP/>}/>
                                   <Route exact path={this.props.match.url + "/bookmaker/bet"}
                                          render={() => <EditTableBet/>}/>
                               </div>
                           </div>) : this.state.isLoading ? (
                               <div className={"slider_style"}><ClipLoader sizeUnit={"px"} size={150} color={'#123abc'}
                                                                           loading={this.state.isLoading}/></div>) : (
                               <Redirect to={"/"}/>)}/>
                <Route/>
                <Route path={this.props.match.url + "/admin"}
                       render={() => this.state.user.role && this.state.user.role.toLowerCase() === "admin" ?
                           (<div>
                               <NavigationHeader menuItems={t('ADMIN_MENU_ITEMS', {returnObjects: true})}/>
                               <MainContent defaultFlag={this.props.defaultFlag}
                                            changeDefaultFlag={this.props.changeDefaultFlag}/>
                               <div id="mainDiv"/>
                               <div className={"table_edit_area"}>
                                   <Route exact path={this.props.match.url + "/admin/result"}
                                          render={() => <EditTableResult/>}/>
                               </div>
                           </div>) : this.state.isLoading ? (
                               <div className={"slider_style"}><ClipLoader sizeUnit={"px"} size={150} color={'#123abc'}
                                                                           loading={this.state.isLoading}/></div>) : (
                               <Redirect to={"/"}/>)}/>
                <Route/>
                <Route path={this.props.match.url + "/user"}
                       render={() => this.state.user.role && this.state.user.role.toLowerCase() === "user" ?
                           (<div>
                               <NavigationHeaderUser user={this.state.user}
                                                     menuItems={t('USER_MENU_ITEMS', {returnObjects: true})}/>
                               <MainContent defaultFlag={this.props.defaultFlag}
                                            changeDefaultFlag={this.props.changeDefaultFlag}/>
                               <div id="mainDiv"/>
                               <Route exact path={this.props.match.url + "/user/bet"}
                                      render={() => <UserBet getCurrentUserState={this.getCurrentUserState}/>}/>
                               <Route exact path={this.props.match.url + "/user/history"}
                                      render={() => <HistoryBets/>}/>
                               <Route exact path={this.props.match.url + "/user/profile"}
                                      render={() => <EditUserProfile user={this.state.user}/>}/>
                               <Route exact path={this.props.match.url + "/user/result"}
                                      render={() => <BetResult/>}/>
                           </div>) : this.state.isLoading ? (
                               <div className={"slider_style"}><ClipLoader sizeUnit={"px"} size={150} color={'#123abc'}
                                                                           loading={this.state.isLoading}/></div>) : (
                               <Redirect to={"/"}/>)}/>
                <Route/>
            </div>
        )
    }
}

export default withTranslation('translation')(Main);