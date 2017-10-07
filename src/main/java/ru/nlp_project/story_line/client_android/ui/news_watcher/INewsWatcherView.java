package ru.nlp_project.story_line.client_android.ui.news_watcher;

import android.content.Context;

public interface INewsWatcherView {

	void setContent(String newsArticleId, String sourceTitleShort,
			String publicationDatePresentation, String title, String content, String url,
			String imageUrl);

	Context getContext();

	/**
	 * Выполнить обработку нажатия кнопки назад (back).
	 *
	 * @return выполнена обработка (что-то сделано) или нет
	 */
	boolean processBackPressed();
}
