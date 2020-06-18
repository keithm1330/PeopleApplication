import React, { Component } from 'react';
import './App.css';
import Home from './Home';
import { BrowserRouter as Router, Route ,Switch } from 'react-router-dom'
import PeopleList from './PeopleList';
import PeopleEdit from './PeopleEdit';
import AddressEdit from './AddressEdit';
class App extends Component {
  render() {
    return (
      <Router>
        <Switch>
          <Route path='/' exact={true} component={PeopleList}/>
          <Route path='/people' exact={true} component={PeopleList}/>
          <Route path='/person/:id' component={PeopleEdit}/>
          <Route path='/address/:id/:personid'  component={AddressEdit}/>
          <Route path='/address/:id'  component={AddressEdit}/>
        </Switch>
      </Router>
    )
  }
}

export default App;