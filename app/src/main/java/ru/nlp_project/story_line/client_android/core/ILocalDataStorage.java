package ru.nlp_project.story_line.client_android.core;

import java.util.List;

import ru.nlp_project.story_line.client_android.datamodel.NewsArticle;
import ru.nlp_project.story_line.client_android.datamodel.NewsArticleHeader;

/**
 * Менеджер данных, хранящихся на локальном хранилище/
 */

public interface ILocalDataStorage {
	List<NewsArticleHeader> getNewsArticleHeaders(int limit);

	// TODO: remote
	void populateDB();

	/**
	 * Сохранить полные стать и одновременно создать заголовки для выгрузка списка.
	 */
	void storeNewsArticles(List<NewsArticle> articles);

	List<NewsArticleHeader> getAdditionalNewsArticleHeaders(long fromId, int limit);
}
