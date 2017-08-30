package ru.nlp_project.story_line.client_android.data.sources_browser;


import android.util.Log;
import io.reactivex.Observable;
import io.reactivex.Scheduler;
import javax.inject.Inject;
import javax.inject.Singleton;
import ru.nlp_project.story_line.client_android.dagger.SchedulerType;
import ru.nlp_project.story_line.client_android.data.models.SourceDataModel;
import ru.nlp_project.story_line.client_android.data.utils.ILocalDBStorage;
import ru.nlp_project.story_line.client_android.data.utils.RetrofiService;


@Singleton
public class SourcesBrowserRepositoryImpl implements ISourcesBrowserRepository {

	private static final String TAG = SourcesBrowserRepositoryImpl.class.getSimpleName();
	@Inject
	ILocalDBStorage localDBStorage;

	@Inject
	public RetrofiService retrofiService;
	@Inject
	@SchedulerType(SchedulerType.background)
	public Scheduler bckgScheduler;

	@Inject
	public SourcesBrowserRepositoryImpl() {
	}

	/**
	 * ...  к потоку данных из сети подключается слушатель для обновления через транзакцию данных в
	 * БД (т.е. при успешном обновлении -- обновляется кэш)...
	 */
	@Override
	public Observable<SourceDataModel> createSourceStream() {
		final long requestId = System.currentTimeMillis();
		SourcesBrowserRetrofitService netService = retrofiService.getSourcesBrowserService();
		Observable<SourceDataModel> obs = netService.list()
			.subscribeOn(bckgScheduler).flatMap(Observable::fromIterable).doOnError(t -> Log.e
				(TAG, t.getMessage(), t));
		// see for details: http://stackoverflow.com/a/36118469
		// need at least 2 subscribers (localDBStorage + actual)
		obs = obs.replay().autoConnect(2);

		obs.observeOn(bckgScheduler).subscribe(
			// onNext
			val->localDBStorage.addSourceToCache(requestId,val),
			// onError
			exc->localDBStorage.cancelSourceCacheUpdate(requestId),
			// onComplete
			()->localDBStorage.commitSourceCacheUpdate(requestId)
		);
		// intermediate level - to recieve onError to localDBStorage
		 Observable<SourceDataModel> wrap = Observable.wrap(obs);
		// fallback source
		Observable<SourceDataModel> resumeNext = wrap
			.onErrorResumeNext(localDBStorage.createSourceStream());
		return resumeNext;
	}

}
