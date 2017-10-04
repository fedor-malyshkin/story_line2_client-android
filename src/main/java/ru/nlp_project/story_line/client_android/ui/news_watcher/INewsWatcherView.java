package ru.nlp_project.story_line.client_android.ui.news_watcher;

import android.content.Context;

public interface INewsWatcherView {

	void setContent(String newsArticleId, String sourceTitleShort,
			String publicationDatePresentation, String title, String content);

	Context getContext();
}
