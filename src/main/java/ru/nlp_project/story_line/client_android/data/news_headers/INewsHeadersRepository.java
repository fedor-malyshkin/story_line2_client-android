package ru.nlp_project.story_line.client_android.data.news_headers;

import io.reactivex.Observable;
import ru.nlp_project.story_line.client_android.business.models.NewsHeaderBusinessModel;
import ru.nlp_project.story_line.client_android.data.IRepository;


public interface INewsHeadersRepository extends IRepository {

	/**
	 * Получить заголовки новостей.
	 *
	 * @param sourceDomain имя источника.
	 * @param lastNewsId идентификатор последней новости после которой будет возвращён список (не
	 * включая указанную новость).
	 */
	Observable<NewsHeaderBusinessModel> createNewsHeaderRemoteCachedStream(String sourceDomain,
			String lastNewsId);
}
