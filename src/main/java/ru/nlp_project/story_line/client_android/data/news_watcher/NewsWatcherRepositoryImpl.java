package ru.nlp_project.story_line.client_android.data.news_watcher;

import android.util.Log;
import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import javax.inject.Inject;
import javax.inject.Singleton;
import ru.nlp_project.story_line.client_android.dagger.SchedulerType;
import ru.nlp_project.story_line.client_android.data.models.NewsArticleDataModel;
import ru.nlp_project.story_line.client_android.data.utils.ILocalDBStorage;
import ru.nlp_project.story_line.client_android.data.utils.RetrofiService;


@Singleton
public class NewsWatcherRepositoryImpl implements INewsWatcherRepository {

	private static final String TAG = NewsWatcherRepositoryImpl.class.getSimpleName();

	@Inject
	ILocalDBStorage localDBStorage;

	@Inject
	public RetrofiService retrofiService;
	@Inject
	@SchedulerType(SchedulerType.background)
	public Scheduler bckgScheduler;

	@Inject
	public NewsWatcherRepositoryImpl() {
	}

	/**
	 * Создать новостной поток - из одной новости.
	 * @param serverId
	 * @return
	 */
	@Override
	public Single<NewsArticleDataModel> createCachedNewsArticleStream(String serverId) {
		final long requestId = System.currentTimeMillis();
		NewsWatcherRetrofitService netService = retrofiService.getNewsWatcherService();
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
		Observable<NewsArticleDataModel> stream = localStream
			.switchIfEmpty(netStream);
		return stream.singleOrError();
	}
}
