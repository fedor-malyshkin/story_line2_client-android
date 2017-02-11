package ru.nlp_project.story_line.client_android.data.categories_browser;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import javax.inject.Inject;
import javax.inject.Singleton;
import ru.nlp_project.story_line.client_android.dagger.SchedulerType;
import ru.nlp_project.story_line.client_android.data.utils.ILocalDBStorage;
import ru.nlp_project.story_line.client_android.data.utils.RetrofiService;

/**
 * Created by fedor on 09.02.17.
 */

@Singleton
public class CategoriesBrowserRepositoryImpl implements ICategoriesBrowserRepository {

	@Inject
	ILocalDBStorage localDBStorage;

	@Inject
	public RetrofiService retrofiService;
	@Inject
	@SchedulerType(SchedulerType.background)
	public Scheduler bckgScheduler;

	@Inject
	public CategoriesBrowserRepositoryImpl() {
	}

	/**
	 * ...  к потоку данных из сети подключается слушатель для обновления через транзакцию данных в
	 * БД (т.е. при успешном обновлении -- обновляется кэш)...
	 */
	@Override
	public Observable<CategoryDataModel> createCategoryStream() {
		CategoriesBrowserRetrofitService netService = retrofiService.getCategoriesBrowserService();
		Observable<CategoryDataModel> obs = netService.list()
			.subscribeOn(bckgScheduler).flatMap(Observable::fromIterable);
		// see for details: http://stackoverflow.com/a/36118469
		obs = obs.replay().autoConnect();
		obs.observeOn(bckgScheduler).subscribe(
			// onNext
			localDBStorage::addCategoryToCache,
			// onError
			localDBStorage::cancelCategoryCacheUpdate,
			// onComplete
			localDBStorage::commitCategoryCacheUpdate
		);
		// intermediate level - to recieve onError to localDBStorage
		Observable<CategoryDataModel> wrap = Observable.wrap(obs);
		// fallback source
		Observable<CategoryDataModel> resumeNext = wrap
			.onErrorResumeNext(localDBStorage.createCategoryStream());
		return resumeNext;
	}

}
