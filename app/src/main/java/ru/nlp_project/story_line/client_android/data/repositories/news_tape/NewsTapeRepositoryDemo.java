package ru.nlp_project.story_line.client_android.data.repositories.news_tape;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.ReplaySubject;
import io.reactivex.subjects.Subject;

/**
 * Created by fedor on 06.02.17.
 */
@Singleton
public class NewsTapeRepositoryDemo implements INewsTapeRepository {
	
	
	private Subject<NewsArticleDataModel> newsArticleStream;
	private Subject<NewsArticleDataModel> additionNewsArticleStream;
	
	@Inject
	public NewsTapeRepositoryDemo() {
	}

	@Override
	public Observable<NewsArticleDataModel> createNewsArticleStream() {
		newsArticleStream =  ReplaySubject.create();

		newsArticleStream.onNext(new NewsArticleDataModel(1L, "System.currentTimeMillis()" + System
				.currentTimeMillis()));
		newsArticleStream.onNext(new NewsArticleDataModel(2L, "System.currentTimeMillis()" + System
				.currentTimeMillis()));
		newsArticleStream.onNext(new NewsArticleDataModel(3L, "System.currentTimeMillis()" + System
				.currentTimeMillis()));
		newsArticleStream.onNext(new NewsArticleDataModel(4L, "System.currentTimeMillis()" + System
				.currentTimeMillis()));
		newsArticleStream.onComplete();
		return newsArticleStream;
	}
	

	@Override
	public Observable<NewsArticleDataModel> createAdditionNewsArticleStream(Long lastNewsId) {
		additionNewsArticleStream = PublishSubject.create();
		return additionNewsArticleStream;
	}

}
