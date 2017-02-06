package ru.nlp_project.story_line.client_android.business.news_tape;


import io.reactivex.Observable;

/**
 * Created by fedor on 05.02.17.
 */

public interface INewsTapeInteractor {
	/**
	 * Создать основной поток новостей.
	 * <p>
	 * По указанному потоку возвращаются как первоначальные, так и обновлнные данны.
	 *
	 * @return поток данных
	 */
	Observable<NewsArticleBusinessModel> createNewsArticleStream();

	/**
	 * Создать поток новостей "дозагрузки".
	 *
	 * @param lastNewsId идентификатор последней новости для дозагрузки (нужны новости после неё).*
	 * @return поток данных
	 */
	Observable<NewsArticleBusinessModel> createAdditionNewsArticleStream(
			Long lastNewsId);
}
