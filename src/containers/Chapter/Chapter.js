import React, {Component} from 'react';

import AppBarAndMenu from "../../components/AppBarAndMenu/AppBarAndMenu";
import chaptersContent from "../../content/cms/tutorial";

import './Chapter.css';
import {Link} from "react-router-dom";
import ArticleView from "../../components/ArticleView/ArticleView";
import Content from "../../components/Content/Content";

class Chapter extends Component {
    constructor(props) {
        super(props);
        this.state = {chapterId: props.match.params.chapterId};
    }

    componentDidMount() {
        this.setState({chapterId: this.props.match.params.chapterId})
    }

    componentDidUpdate(lastProps) {
        if (lastProps.match.params.chapterId !== this.props.match.params.chapterId) {
            this.setState({chapterId: this.props.match.params.chapterId})
        }
    }

    render() {
        const chapterId = this.state.chapterId;
        const chapter = chaptersContent[chapterId];

        return <div>
            <AppBarAndMenu title={chapter.title}>
                <Content>
                    <ArticleView article={chapter}>
                        <Link to={'/tutorial'}>Back to tutorial overview</Link>
                    </ArticleView>
                </Content>
            </AppBarAndMenu>
        </div>;
    }
}

export default Chapter;