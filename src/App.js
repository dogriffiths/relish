import React, {Component} from 'react';
import {HashRouter, Route} from 'react-router-dom';

import HomePage from "./containers/HomePage/HomePage";

import './App.css';
import TableOfContentsPage from "./containers/TableOfContentsPage/TableOfContentsPage";
import SourceCodePage from "./containers/SourceCodePage/SourceCodePage";
import BlogIndex from "./containers/BlogIndex/BlogIndex";
import Article from "./containers/Article/Article";
import Chapter from "./containers/Chapter/Chapter";
import Search from "./containers/Search/Search";

class App extends Component {
    render() {
        return (
            <HashRouter>
                <div>
                    <div>
                        <Route exact path="/" component={HomePage}/>
                        <Route exact path="/tutorial" component={TableOfContentsPage}/>
                        <Route parameter='chapterId'  path="/tutorial/:chapterId" component={Chapter}/>
                        <Route exact path="/sourceCode" component={SourceCodePage}/>
                        <Route exact path="/blogs" component={BlogIndex}/>
                        <Route parameter='blogId'  path="/blogs/:blogId" component={Article}/>
                        <Route exact path="/search" component={Search}/>
                        <Route parameter='terms'  path="/search/:terms" component={Search}/>
                    </div>
                </div>
            </HashRouter>
        );
    }
}

export default App;
