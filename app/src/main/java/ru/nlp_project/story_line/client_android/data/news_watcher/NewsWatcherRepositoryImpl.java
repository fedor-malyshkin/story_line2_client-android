package ru.nlp_project.story_line.client_android.data.news_watcher;

import io.reactivex.Maybe;
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

	@Override
	public Single<NewsArticleDataModel> createCachedNewsArticleStream(String serverId) {
		NewsWatcherRetrofitService netService = retrofiService.getNewsWatcherService();
		// connectable (run if more than 1 subscriber)
		Observable<NewsArticleDataModel> netStream = netService.getNewsArticleById(serverId)
			.subscribeOn(bckgScheduler).toObservable().publish().autoConnect(2);

		// first subscriber -- write to cache if request from network
		netStream.subscribe(
			// onNext
			localDBStorage::addNewsArticleToCache,
			// onError
			localDBStorage::cancelNewsArticleCacheUpdate,
			// onComplete
			localDBStorage::commitNewsArticleCacheUpdate
		);

		Observable<NewsArticleDataModel> localStream = localDBStorage
			.createNewsArticleStream(serverId).subscribeOn(bckgScheduler).toObservable();
		Observable<NewsArticleDataModel> stream = localStream
			.switchIfEmpty(netStream);
		return stream.singleOrError();
	}
}
