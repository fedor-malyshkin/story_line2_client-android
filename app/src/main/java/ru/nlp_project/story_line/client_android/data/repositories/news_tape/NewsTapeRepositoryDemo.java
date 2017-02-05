package ru.nlp_project.story_line.client_android.data.repositories.news_tape;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

/**
 * Created by fedor on 06.02.17.
 */
@Singleton
public class NewsTapeRepositoryDemo implements INewsTapeRepository {
	
	
	private PublishSubject<NewsArticleDataModel> newsArticleStream;
	private PublishSubject<NewsArticleDataModel> additionNewsArticleStream;
	
	@Inject
	public NewsTapeRepositoryDemo() {
	}

	@Override
	public Observable<NewsArticleDataModel> getNewsArticleStream() {
		newsArticleStream = PublishSubject.create();
		return newsArticleStream;
	}
	

	@Override
	public Observable<NewsArticleDataModel> getAdditionNewsArticleStream() {
		additionNewsArticleStream = PublishSubject.create();
		return additionNewsArticleStream;
	}


	@Override
	public void requsetUpdate() {
		newsArticleStream.onNext(new NewsArticleDataModel(1L, "System.currentTimeMillis()" + System
				.currentTimeMillis()));
		newsArticleStream.onNext(new NewsArticleDataModel(2L, "System.currentTimeMillis()" + System
				.currentTimeMillis()));
		newsArticleStream.onNext(new NewsArticleDataModel(3L, "System.currentTimeMillis()" + System
				.currentTimeMillis()));
		newsArticleStream.onNext(new NewsArticleDataModel(4L, "System.currentTimeMillis()" + System
				.currentTimeMillis()));
	}

	@Override
	public void requsetAddition(Long lastNewsId) {

	}
}
