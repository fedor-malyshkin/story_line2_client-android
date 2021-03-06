package ru.nlp_project.story_line.client_android.data.news_headers;

import android.util.Log;
import io.reactivex.Observable;
import io.reactivex.Scheduler;
import javax.inject.Inject;
import javax.inject.Singleton;
import ru.nlp_project.story_line.client_android.business.models.NewsHeaderBusinessModel;
import ru.nlp_project.story_line.client_android.dagger.SchedulerType;
import ru.nlp_project.story_line.client_android.data.models.NewsHeaderDataModel;
import ru.nlp_project.story_line.client_android.data.utils.Converters;
import ru.nlp_project.story_line.client_android.data.utils.ILocalDBStorage;
import ru.nlp_project.story_line.client_android.data.utils.RetrofitService;

@Singleton
public class NewsHeadersRepositoryImpl implements INewsHeadersRepository {

	private static final String TAG = NewsHeadersRepositoryImpl.class.getSimpleName();
	@Inject
	public RetrofitService retrofitService;
	@Inject
	@SchedulerType(SchedulerType.background)
	public Scheduler bckgScheduler;
	@Inject
	ILocalDBStorage localDBStorage;

	@Inject
	public NewsHeadersRepositoryImpl() {
	}

	@Override
	public Observable<NewsHeaderBusinessModel> createNewsHeaderRemoteCachedStream(String sourceDomain,
			String lastNewsId) {
		NewsHeadersRetrofitService netService = retrofitService.getNewsTapeService();
		Observable<NewsHeaderDataModel> netStream = netService.listHeaders(sourceDomain, 20, lastNewsId)
				.subscribeOn(bckgScheduler).flatMap(Observable::fromIterable).doOnError(t -> Log.e
						(TAG, t.getMessage(), t));
		netStream = netStream.doOnNext(localDBStorage::addNewsHeader);
		// fallback source
		return netStream
				.onErrorResumeNext(localDBStorage.createNewsHeaderStream(sourceDomain))
				.compose(Converters.toNewsHeaderBusinessModel);
	}

	@Override
	public void initializeRepository() {

	}
}
