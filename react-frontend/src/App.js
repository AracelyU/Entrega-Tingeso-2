import React from 'react';
import './App.css';
import {BrowserRouter as Router, Route, Switch} from 'react-router-dom'
import ListBookComponent from './components/ListBookComponent';
import HeaderComponent from './components/HeaderComponent';
import CreateBookComponent from './components/CreateBookComponent';
import ViewBookComponent from './components/ViewBookComponent';

function App() {
  return (
    <div>
        <Router>
              <HeaderComponent />
                <div className="container">
                    <Switch> 
                          <Route path = "/" exact component = {ListBookComponent}></Route>
                          <Route path = "/books" component = {ListBookComponent}></Route>
                          <Route path = "/add-book" component = {CreateBookComponent}></Route>
                          <Route path = "/view-book/:id" component = {ViewBookComponent}></Route>
                    </Switch>
                </div>
        </Router>
    </div>
    
  );
}

export default App;
