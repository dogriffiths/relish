import blog from './cms/blog';
import tutorial from './cms/tutorial';
import moment from "moment";

const blogArticles = Object.keys(blog)
    .map(blogKey => {return {id: blogKey, category: 'blogs', ...blog[blogKey]}})
    .filter(article => moment(article.date).utc() < moment().utc())
    .sort((a, b) => (a.date === b.date) ? 0 : ((a.date < b.date) ? 1 : -1))
;

const allLessons =  Object.keys(tutorial)
    .map(key => {return {id: key, category: 'tutorial', ...tutorial[key]}});

export {blogArticles, allLessons};