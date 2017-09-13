package ru.nlp_project.story_line.client_android.data.news_tape;

import io.reactivex.Observable;
import ru.nlp_project.story_line.client_android.data.models.NewsHeaderDataModel;


public interface INewsTapeRepository {

	/**
	 * Получить заголовки новостей.
	 *
	 * @param sourceDomain имя источника.
	 * @param lastNewsId идентификатор последней новости после которой будет возвращён список (не включая указанную новость).
	 */
	Observable<NewsHeaderDataModel> createNewsHeaderRemoteCachedStream(String sourceDomain,
			String lastNewsId);
}
