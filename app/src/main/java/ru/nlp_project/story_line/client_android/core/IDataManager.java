package ru.nlp_project.story_line.client_android.core;

import java.util.List;

import ru.nlp_project.story_line.client_android.datamodel.NewsArticleHeader;

/**
 * Менеджер данных. Использует объекты моделей и реализации других интерфейсов для обеспечения
 * получения данных как из локального храниллища, так и из сети.
 */

public interface IDataManager {
	/**
	 * Получить закэшированные локально данные (заголовки статей).
	 *
	 * @param limit количество записей (-1 -- бесконечное количество)
	 * @return список заголовков
	 */
	List<NewsArticleHeader> getCachedLocalyNewsArticleHeaders(int limit);

	/**
	 * Обновить с сервера данные.
	 *
	 * @return результат обновления
	 */
	DownloadDataResult downloadNewsArticles();

	/**
	 * Выполнить дополнительную закачку данных с сервера.
	 *
	 * @param fromId с какого идентификатора выполнять дозакачку данных.
	 * @return результат обновления
	 */
	DownloadDataResult downloadAdditionalNewsArticles(long fromId);

	List<NewsArticleHeader> getAdditionalNewsArticles(long fromId, int limit);
	// TODO: remove
	void populateDB();
}
