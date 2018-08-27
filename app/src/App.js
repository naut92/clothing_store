import React, { Component } from 'react';
import './App.css';
import Home from './Home';
import { BrowserRouter as Router, Route, Switch } from 'react-router-dom';
import StoreList from './StoreList';
import StoreEdit from './StoreEdit';
import StockList from './StockList';
import StockEdit from './StockEdit';


class App extends Component {
    render() {
        return (
            <Router>
                <Switch>
                    <Route path='/' exact={true} component={Home}/>
                    <Route path='/store' exact={true} component={StoreList}/>
                    <Route path='/store/:id' component={StoreEdit}/>;
                    <Route path='/stock' exact={true} component={StockList}/>
                    <Route path='/stock/:id' component={StockEdit}/>;
                </Switch>
            </Router>
        )
    }
}

export default App;