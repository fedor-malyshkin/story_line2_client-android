package ru.nlp_project.story_line.client_android.business.news_tape;


import io.reactivex.Observable;

/**
 * Created by fedor on 05.02.17.
 */

public interface INewsTapeInteractor {
	/**
	 * Получить основной поток новостей.
	 * <p>
	 * По указанному потоку возвращаются как первоначальные, так и обновлнные данны.
	 *
	 * @return поток данных
	 */
	Observable<NewsArticleBusinessModel> getNewsArticleStream();

	/**
	 * Получить поток новостей "дозагрузки".
	 *
	 * @return поток данных
	 */
	Observable<NewsArticleBusinessModel> getAdditionNewsArticleStream();

	/**
	 * Запросить обновление данных новостей. Вызывается на текущий момент мгновенно.
	 * TODO: реализовать задержку в возврате до завершения возврата данных.
	 */
	void requsetUpdate();

	/**
	 * Запросить "дозагрузку" новостей по потоку из {@link #getAdditionNewsArticleStream()}.
	 *
	 * @param lastNewsId идентификатор последней новости для дозагрузки (нужны новости после неё).
	 */
	void requsetAddition(Long lastNewsId);

}
