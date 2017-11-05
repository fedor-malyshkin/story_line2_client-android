package ru.nlp_project.story_line.client_android.business.news_headers;


import io.reactivex.Observable;
import ru.nlp_project.story_line.client_android.business.IInteractor;
import ru.nlp_project.story_line.client_android.business.models.NewsHeaderBusinessModel;

public interface INewsHeadersInteractor extends IInteractor {

	/**
	 * Создать основной поток новостей. <p> По указанному потоку возвращаются как первоначальные, так
	 * и обновлнные данны.
	 *
	 * @return поток данных
	 */
	Observable<NewsHeaderBusinessModel> createNewsHeaderRemoteCachedStream(String sourceDomain);

	/**
	 * Создать поток новостей "дозагрузки".
	 *
	 * @param lastNewsHeaderServerId идентификатор последней новости для дозагрузки (нужны новости
	 * после неё).*
	 * @return поток данных
	 */
	Observable<NewsHeaderBusinessModel> createAdditionNewsHeaderRemoteCachedStream(
			String sourceDomain, String lastNewsHeaderServerId);

	String getSourceTitleShortCached(String sourceName);
}
