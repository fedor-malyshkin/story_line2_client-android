package ru.nlp_project.story_line.client_android.ui.news_tape;

/**
 * Created by fedor on 04.02.17.
 */

public interface INewsTapeView  {
	void updateNewsArticle(NewsArticleUIModel news);
	
	void addNewsArticle(NewsArticleUIModel news);
	
	void clearTape();
}
