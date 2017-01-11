package ru.nlp_project.story_line.client_android;

import java.util.List;

import ru.nlp_project.story_line.client_android.datamodel.NewsArticleHeader;

/**
 * Created by fedor on 11.01.17.
 */

public interface IEventProcessor {
	List<NewsArticleHeader> getDefaultNewsArticleHeaders();
	// TODO: delete this
	void populateDB();
	
	void updateData();
}
