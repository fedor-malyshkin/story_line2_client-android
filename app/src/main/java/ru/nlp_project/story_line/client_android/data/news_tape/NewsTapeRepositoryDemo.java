package ru.nlp_project.story_line.client_android.data.news_tape;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.ReplaySubject;
import io.reactivex.subjects.Subject;
import javax.inject.Inject;
import javax.inject.Singleton;

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
		newsArticleStream = ReplaySubject.create();

		for (int i = 0; i < 100; i++) {
			newsArticleStream
				.onNext(new NewsArticleDataModel(1L, "System.currentTimeMillis()" + System
					.currentTimeMillis()));
		}
		newsArticleStream.onComplete();
		return newsArticleStream;
	}


	@Override
	public Observable<NewsArticleDataModel> createAdditionNewsArticleStream(Long lastNewsId) {
		additionNewsArticleStream = PublishSubject.create();
		return additionNewsArticleStream;
	}

}
