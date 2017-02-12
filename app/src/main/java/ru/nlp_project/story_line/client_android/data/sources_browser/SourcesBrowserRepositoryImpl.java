package ru.nlp_project.story_line.client_android.data.sources_browser;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import javax.inject.Inject;
import javax.inject.Singleton;
import ru.nlp_project.story_line.client_android.dagger.SchedulerType;
import ru.nlp_project.story_line.client_android.data.models.SourceDataModel;
import ru.nlp_project.story_line.client_android.data.utils.ILocalDBStorage;
import ru.nlp_project.story_line.client_android.data.utils.RetrofiService;

/**
 * Created by fedor on 09.02.17.
 */

@Singleton
public class SourcesBrowserRepositoryImpl implements ISourcesBrowserRepository {

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
	// TODO: test variants
	@Override
	public Observable<SourceDataModel> createSourceStream() {
		SourcesBrowserRetrofitService netService = retrofiService.getSourcesBrowserService();
		Observable<SourceDataModel> obs = netService.list()
			.subscribeOn(bckgScheduler).flatMap(Observable::fromIterable);
		// see for details: http://stackoverflow.com/a/36118469
		obs = obs.replay().autoConnect();
		obs.observeOn(bckgScheduler).subscribe(
			// onNext
			localDBStorage::addSourceToCache,
			// onError
			localDBStorage::cancelSourceCacheUpdate,
			// onComplete
			localDBStorage::commitSourceCacheUpdate
		);
		// intermediate level - to recieve onError to localDBStorage
		Observable<SourceDataModel> wrap = Observable.wrap(obs);
		// fallback source
		Observable<SourceDataModel> resumeNext = wrap
			.onErrorResumeNext(localDBStorage.createSourceStream());
		return resumeNext;
	}

}
