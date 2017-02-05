package ru.nlp_project.story_line.client_android.ui.news_tape;

import ru.nlp_project.story_line.client_android.ui.IPresenter;

/**
 * Created by fedor on 04.02.17.
 */

public interface INewsTapePresenter extends IPresenter<INewsTapeView> {
	void connectNewsArticlesStream();
	void connectAdditionNewsArticlesStream();

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
