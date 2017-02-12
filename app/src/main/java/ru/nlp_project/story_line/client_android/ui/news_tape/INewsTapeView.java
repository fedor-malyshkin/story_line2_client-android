package ru.nlp_project.story_line.client_android.ui.news_tape;

import ru.nlp_project.story_line.client_android.business.models.NewsHeaderBusinessModel;

/**
 * Created by fedor on 04.02.17.
 */

public interface INewsTapeView  {
	void showUpdateIndicator(boolean show);
	void addNewsArticle(NewsHeaderBusinessModel news);
	void clearTape();
	void newsSelected(int position);
}
