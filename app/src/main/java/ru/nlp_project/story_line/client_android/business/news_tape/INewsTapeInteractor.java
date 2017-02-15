package ru.nlp_project.story_line.client_android.business.news_tape;


import io.reactivex.Observable;
import ru.nlp_project.story_line.client_android.business.models.NewsHeaderBusinessModel;

public interface INewsTapeInteractor {

	/**
	 * Создать основной поток новостей. <p> По указанному потоку возвращаются как первоначальные,
	 * так и обновлнные данны.
	 *
	 * @return поток данных
	 * @param sourceDomain
	 */
	Observable<NewsHeaderBusinessModel> createNewsHeaderStream(String sourceDomain);

	/**
	 * Создать поток новостей "дозагрузки".
	 *
	 * @param lastNewsId идентификатор последней новости для дозагрузки (нужны новости после неё).*
	 * @return поток данных
	 */
	Observable<NewsHeaderBusinessModel> createAdditionNewsHeaderStream(
		String sourceDomain, Long lastNewsId);
}
