package ru.nlp_project.story_line.client_android.data.news_articles;

import android.util.Log;
import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import javax.inject.Inject;
import javax.inject.Singleton;
import ru.nlp_project.story_line.client_android.business.models.NewsArticleBusinessModel;
import ru.nlp_project.story_line.client_android.dagger.SchedulerType;
import ru.nlp_project.story_line.client_android.data.models.NewsArticleDataModel;
import ru.nlp_project.story_line.client_android.data.utils.Converters;
import ru.nlp_project.story_line.client_android.data.utils.ILocalDBStorage;
import ru.nlp_project.story_line.client_android.data.utils.RetrofitService;


@Singleton
public class NewsArticlesRepositoryImpl implements INewsArticlesRepository {

	private static final String TAG = NewsArticlesRepositoryImpl.class.getSimpleName();
	@Inject
	public RetrofitService retrofitService;
	@Inject
	@SchedulerType(SchedulerType.background)
	public Scheduler bckgScheduler;
	@Inject
	ILocalDBStorage localDBStorage;

	@Inject
	public NewsArticlesRepositoryImpl() {
	}

	/**
	 * Создать новостной поток - из одной новости.
	 */
	@Override
	public Single<NewsArticleBusinessModel> createNewsArticleRemoteCachedStream(String serverId) {
		final long requestId = System.currentTimeMillis();
		NewsArticlesRetrofitService netService = retrofitService.getNewsWatcherService();
		// connectable (run if more than 1 subscriber)
		Observable<NewsArticleDataModel> netStream = netService.getNewsArticleById(serverId)
				.subscribeOn(bckgScheduler).toObservable().doOnError(t -> Log.e
						(TAG, t.getMessage(), t)).publish().autoConnect(2);

		// first subscriber -- write to cache if request from network
		netStream.subscribe(
				// onNext
				localDBStorage::addNewsArticleToCache
		);

		Observable<NewsArticleDataModel> localStream = localDBStorage
				.createNewsArticleStream(serverId).subscribeOn(bckgScheduler).toObservable();

		Observable<NewsArticleDataModel> stream = netStream
				.switchIfEmpty(localStream);
		return stream.singleOrError().compose(Converters.toNewsArticleBusinessModel);
	}

	@Override
	public void initializeRepository() {

	}
}
