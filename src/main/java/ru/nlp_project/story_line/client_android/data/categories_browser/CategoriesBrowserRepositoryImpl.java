package ru.nlp_project.story_line.client_android.data.categories_browser;

import android.util.Log;
import io.reactivex.Observable;
import io.reactivex.Scheduler;
import javax.inject.Inject;
import javax.inject.Singleton;
import ru.nlp_project.story_line.client_android.dagger.SchedulerType;
import ru.nlp_project.story_line.client_android.data.models.CategoryDataModel;
import ru.nlp_project.story_line.client_android.data.utils.ILocalDBStorage;
import ru.nlp_project.story_line.client_android.data.utils.RetrofiService;


@Singleton
public class CategoriesBrowserRepositoryImpl implements ICategoriesBrowserRepository {

	private static final String TAG = CategoriesBrowserRepositoryImpl.class.getSimpleName();

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
		final long requestId = System.currentTimeMillis();
		CategoriesBrowserRetrofitService netService = retrofiService.getCategoriesBrowserService();
		Observable<CategoryDataModel> obs = netService.list()
			.subscribeOn(bckgScheduler).flatMap(Observable::fromIterable).doOnError(t -> Log.e
				(TAG, t.getMessage(), t));
		// see for details: http://stackoverflow.com/a/36118469
		// need at least 2 subscribers (localDBStorage + actual)
		obs = obs.replay().autoConnect(2);
		obs.observeOn(bckgScheduler).subscribe(
			// onNext
			val -> localDBStorage.addCategoryToCache(requestId, val),
			// onError
			exc -> localDBStorage.cancelCategoryCacheUpdate(requestId),
			// onComplete
			() -> localDBStorage.commitCategoryCacheUpdate(requestId)
		);
		// intermediate level - to recieve onError to localDBStorage
		Observable<CategoryDataModel> wrap = Observable.wrap(obs);
		// fallback source
		Observable<CategoryDataModel> resumeNext = wrap
			.onErrorResumeNext(localDBStorage.createCategoryStream());
		return resumeNext;
	}

}
