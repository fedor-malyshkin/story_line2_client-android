package ru.nlp_project.story_line.client_android.data.sources_browser;


import android.util.Log;
import io.reactivex.Observable;
import io.reactivex.Scheduler;
import java.util.List;
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
	public RetrofiService retrofiService;
	@Inject
	@SchedulerType(SchedulerType.background)
	public Scheduler bckgScheduler;
	@Inject
	ILocalDBStorage localDBStorage;

	@Inject
	public SourcesBrowserRepositoryImpl() {
	}

	/**
	 * ...  к потоку данных из сети подключается слушатель для обновления через транзакцию данных в БД
	 * (т.е. при успешном обновлении -- обновляется кэш)...
	 */
	@Override
	public Observable<SourceDataModel> createSourceStream() {
		final long requestId = System.currentTimeMillis();
		SourcesBrowserRetrofitService netService = retrofiService.getSourcesBrowserService();
		Observable<SourceDataModel> netStream = netService.list()
				.subscribeOn(bckgScheduler).flatMap(Observable::fromIterable).doOnError(t -> Log.e
						(TAG, t.getMessage(), t));
		// see for details: http://stackoverflow.com/a/36118469
		// need at least 2 subscribers (localDBStorage + actual)
		netStream = netStream.replay().autoConnect(2);

		netStream.observeOn(bckgScheduler).subscribe(
				// onNext
				val -> localDBStorage.addSourceToCache(requestId, val),
				// onError
				exc -> localDBStorage.cancelSourceCacheUpdate(requestId),
				// onComplete
				() -> localDBStorage.commitSourceCacheUpdate(requestId)
		);
		// intermediate level - to receive onError to localDBStorage
		Observable<SourceDataModel> wrap = Observable.wrap(netStream);
		// fallback source
		Observable<SourceDataModel> resumeNext = wrap
				.onErrorResumeNext(localDBStorage.createSourceStream());
		return resumeNext;
	}

	@Override
	public void upsetSources(List<SourceDataModel> list) {
		localDBStorage.upsetSources(list);
	}

	@Override
	public Observable<SourceDataModel> createSourceStreamLocal() {
		return localDBStorage.createSourceStream().subscribeOn(bckgScheduler);
	}

	@Override
	public Observable<SourceDataModel> createSourceStreamRemote() {
		SourcesBrowserRetrofitService netService = retrofiService.getSourcesBrowserService();
		return netService.list()
				.subscribeOn(bckgScheduler).flatMap(Observable::fromIterable).doOnError(t -> Log.e
						(TAG, t.getMessage(), t));
	}
}
