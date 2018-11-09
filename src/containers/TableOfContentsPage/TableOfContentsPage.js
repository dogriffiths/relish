import React, {Component} from 'react';
import AppBarAndMenu from "../../components/AppBarAndMenu/AppBarAndMenu";
import ImageItem from "../../components/ImageItem/ImageItem";
import tutorialContent from "../../content/cms/tutorial";
import {withRouter} from "react-router-dom";

import './TableOfContentsPage.css';

class TableOfContentsPage extends Component {
    render() {
        return <div>
            <AppBarAndMenu title='Table of contents'>
                <section className='tableOfContents'>
                    {Object.keys(tutorialContent).sort().map(tutorialId => {
                            const tutorial = tutorialContent[tutorialId];
                            return <ImageItem
                                id={tutorialId}
                                src={tutorial.image}
                                name={
                                    <div>
                                        <div className='chapter-number'
                                             style={{float: 'left', marginRight: '1ex'}}>{tutorial.name} </div>
                                        <div className='chapter-title'>
                                            {tutorial.title}
                                        </div>
                                    </div>
                                }
                                description={tutorial.subtitle}
                                onClick={(chapterId) => this.props.history.push(`/tutorial/${chapterId}`)}
                            />;
                        }
                    )}
                </section>
            </AppBarAndMenu>
        </div>
            ;
    }
}

export default withRouter(TableOfContentsPage);
