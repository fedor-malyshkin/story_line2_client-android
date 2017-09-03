package ru.nlp_project.story_line.client_android.data.news_tape;

import android.util.Log;
import io.reactivex.Observable;
import io.reactivex.Scheduler;
import javax.inject.Inject;
import javax.inject.Singleton;
import ru.nlp_project.story_line.client_android.dagger.SchedulerType;
import ru.nlp_project.story_line.client_android.data.models.NewsHeaderDataModel;
import ru.nlp_project.story_line.client_android.data.utils.ILocalDBStorage;
import ru.nlp_project.story_line.client_android.data.utils.RetrofiService;

@Singleton
public class NewsTapeRepositoryImpl implements INewsTapeRepository {

	private static final String TAG = NewsTapeRepositoryImpl.class.getSimpleName();

	@Inject
	ILocalDBStorage localDBStorage;

	@Inject
	public RetrofiService retrofiService;
	@Inject
	@SchedulerType(SchedulerType.background)
	public Scheduler bckgScheduler;

	@Inject
	public NewsTapeRepositoryImpl() {
	}

	@Override
	public Observable<NewsHeaderDataModel> createCachedNewsHeaderStream(String sourceDomain) {
		final long requestId = System.currentTimeMillis();
		NewsTapeRetrofitService netService = retrofiService.getNewsTapeService();
		Observable<NewsHeaderDataModel> netStream = netService.listHeaders(sourceDomain, 20)
			.subscribeOn(bckgScheduler).flatMap(Observable::fromIterable).doOnError(t -> Log.e
				(TAG, t.getMessage(), t));
		// see for details: http://stackoverflow.com/a/36118469
		// need at least 2 subscribers (localDBStorage + actual)
		netStream = netStream.replay().autoConnect(2);

		netStream.observeOn(bckgScheduler).subscribe(
			// onNext
			localDBStorage::addNewsHeaderToCache,
			// onError
			t -> Log.e(TAG, t.getMessage(), t),
			// onComplete
			()->{}
		);
		// intermediate level - to recieve onError to localDBStorage
		Observable<NewsHeaderDataModel> wrap = Observable.wrap(netStream);
		// fallback source
		Observable<NewsHeaderDataModel> resumeNext = wrap
			.onErrorResumeNext(localDBStorage.createNewsHeaderStream(sourceDomain));
		return resumeNext;
	}

	// TODO: implements with DB as cache
	@Override
	public Observable<NewsHeaderDataModel> createAdditionNewsHeaderStream(String sourceDomain,
		Long lastNewsId) {
		return null;
	}

}
