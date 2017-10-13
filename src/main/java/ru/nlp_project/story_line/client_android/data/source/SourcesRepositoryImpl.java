package ru.nlp_project.story_line.client_android.data.source;


import android.util.Log;
import io.reactivex.Observable;
import io.reactivex.Scheduler;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;
import ru.nlp_project.story_line.client_android.business.models.SourceBusinessModel;
import ru.nlp_project.story_line.client_android.dagger.SchedulerType;
import ru.nlp_project.story_line.client_android.data.models.SourceDataModel;
import ru.nlp_project.story_line.client_android.data.utils.Converters;
import ru.nlp_project.story_line.client_android.data.utils.ILocalDBStorage;
import ru.nlp_project.story_line.client_android.data.utils.RetrofitService;


@Singleton
public class SourcesRepositoryImpl implements ISourcesRepository {

	private static final String TAG = SourcesRepositoryImpl.class.getSimpleName();
	@Inject
	public RetrofitService retrofitService;
	@Inject
	@SchedulerType(SchedulerType.background)
	public Scheduler bckgScheduler;
	@Inject
	ILocalDBStorage localDBStorage;

	@Inject
	public SourcesRepositoryImpl() {
	}

	/**
	 * ...  к потоку данных из сети подключается слушатель для обновления через транзакцию данных в БД
	 * (т.е. при успешном обновлении -- обновляется кэш)...
	 */
	@Override
	public Observable<SourceBusinessModel> createSourceRemoteCachedStream() {
		SourcesRetrofitService netService = retrofitService.getSourcesBrowserService();
		Observable<SourceDataModel> netStream = netService.list()
				.subscribeOn(bckgScheduler).flatMap(Observable::fromIterable).doOnError(t -> Log.e
						(TAG, t.getMessage(), t));
		// see for details: http://stackoverflow.com/a/36118469
		// need at least 2 subscribers (localDBStorage + actual)
		netStream = netStream.replay().autoConnect(2);

		netStream.observeOn(bckgScheduler).subscribe(
				// onNext
				val -> localDBStorage.addSourceToCache(val),
				// onError
				exc -> Log.e
						(TAG, exc.getMessage(), exc)
		);
		// intermediate level - to receive onError to localDBStorage
		Observable<SourceDataModel> wrap = Observable.wrap(netStream);
		// fallback source
		Observable<SourceDataModel> resumeNext = wrap
				.onErrorResumeNext(localDBStorage.createSourceStream());
		return resumeNext.compose(Converters.toSourceBusinessModel);
	}

	@Override
	public void upsetSources(List<SourceBusinessModel> list) {
		localDBStorage.upsetSources(SourceBusinessModel.convertList(list));
	}

	@Override
	public Observable<SourceBusinessModel> createSourceLocalStream() {
		return localDBStorage.createSourceStream().subscribeOn(bckgScheduler)
				.compose(Converters.toSourceBusinessModel);
	}

	@Override
	public Observable<SourceBusinessModel> createSourceRemoteStream() {
		SourcesRetrofitService netService = retrofitService.getSourcesBrowserService();
		return netService.list()
				.subscribeOn(bckgScheduler).flatMap(Observable::fromIterable)
				.compose(Converters.toSourceBusinessModel).doOnError(t -> Log.e
						(TAG, t.getMessage(), t));
	}
}
