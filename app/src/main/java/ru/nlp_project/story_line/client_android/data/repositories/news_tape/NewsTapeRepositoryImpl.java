package ru.nlp_project.story_line.client_android.data.repositories.news_tape;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;

/**
 * Created by fedor on 05.02.17.
 */
@Singleton
public class NewsTapeRepositoryImpl implements INewsTapeRepository {

	@Inject
	public NewsTapeRepositoryImpl() {
	}

	@Override
	public Observable<NewsArticleDataModel> getNewsArticleStream() {
		Observable<NewsArticleDataModel> observable = Observable
				.just(new NewsArticleDataModel(1L, "val1"), new NewsArticleDataModel(2L, "val2"));
		return observable;
	}

	@Override
	public Observable<NewsArticleDataModel> getAdditionNewsArticleStream() {
		Observable<NewsArticleDataModel> observable = Observable
				.just(new NewsArticleDataModel(3L, "va3"), new NewsArticleDataModel(4L, "val5"));
		return observable;
	}

	@Override
	public void requsetUpdate() {

	}

	@Override
	public void requsetAddition(Long lastNewsId) {

	}
}
