package ru.nlp_project.story_line.client_android.core;

import java.util.List;

import ru.nlp_project.story_line.client_android.datamodel.NewsArticle;

/**
 * Менеджер данных, расположенных в сети.
 */
public interface IRemoteDataStorage {
	List<NewsArticle> getNewsArticles() ;
	
	List<NewsArticle> getNewsArticles(long fromId);

}
