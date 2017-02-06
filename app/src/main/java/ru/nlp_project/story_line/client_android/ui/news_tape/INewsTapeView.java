package ru.nlp_project.story_line.client_android.ui.news_tape;

/**
 * Created by fedor on 04.02.17.
 */

public interface INewsTapeView  {
	void showUpdateIndicator(boolean show);
	void addNewsArticle(NewsArticleUIModel news);
	void clearTape();
}
