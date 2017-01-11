package ru.nlp_project.story_line.client_android.core;

import java.util.List;

import ru.nlp_project.story_line.client_android.data.model.NewsArticleHeader;

/**
 * Created by fedor on 11.01.17.
 */

public interface ILocalDataStorage {
	List<NewsArticleHeader> getExistingNewsArticleHeaders();

	void populateDB();
}
