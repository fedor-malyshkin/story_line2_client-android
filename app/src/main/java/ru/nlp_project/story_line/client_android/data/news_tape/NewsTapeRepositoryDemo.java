package ru.nlp_project.story_line.client_android.data.news_tape;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.ReplaySubject;
import io.reactivex.subjects.Subject;
import javax.inject.Inject;
import javax.inject.Singleton;
import ru.nlp_project.story_line.client_android.data.models.NewsHeaderDataModel;

/**
 * Created by fedor on 06.02.17.
 */
@Singleton
public class NewsTapeRepositoryDemo implements INewsTapeRepository {


	private Subject<NewsHeaderDataModel> newsArticleStream;
	private Subject<NewsHeaderDataModel> additionNewsArticleStream;

	@Inject
	public NewsTapeRepositoryDemo() {
	}

	@Override
	public Observable<NewsHeaderDataModel> createNewsArticleStream() {
		newsArticleStream = ReplaySubject.create();

		for (int i = 0; i < 50; i++) {
			newsArticleStream
				.onNext(new NewsHeaderDataModel((long) i, "System.currentTimeMillis()" + System
					.currentTimeMillis(), "serverId-" + i));
		}
		newsArticleStream.onComplete();
		return newsArticleStream;
	}


	@Override
	public Observable<NewsHeaderDataModel> createAdditionNewsArticleStream(Long lastNewsId) {
		additionNewsArticleStream = PublishSubject.create();
		return additionNewsArticleStream;
	}

}
