package ru.nlp_project.story_line.client_android.data.feedback;


import android.util.Log;
import io.reactivex.Observable;
import io.reactivex.Scheduler;
import javax.inject.Inject;
import javax.inject.Singleton;
import ru.nlp_project.story_line.client_android.dagger.SchedulerType;
import ru.nlp_project.story_line.client_android.data.utils.ILocalDBStorage;
import ru.nlp_project.story_line.client_android.data.utils.RetrofitService;


@Singleton
public class FeedbackRepositoryImpl implements IFeedbackRepository {

	private static final String TAG = FeedbackRepositoryImpl.class.getSimpleName();
	@Inject
	public RetrofitService retrofitService;
	@Inject
	@SchedulerType(SchedulerType.background)
	public Scheduler bckgScheduler;
	@Inject
	ILocalDBStorage localDBStorage;
	private FeedbackRetrofitService netService;

	@Inject
	public FeedbackRepositoryImpl() {
	}


	@Override
	public Observable<String> getAboutInfo() {
		return netService.getAboutInfo().subscribeOn(bckgScheduler);
	}

	@Override
	public void sendFeedback(String from, String feedback) {
		netService.sendFeedback(from, feedback).subscribeOn(bckgScheduler).subscribe(() -> {
		}, (throwable) -> {
			Log.e(TAG, "", throwable);
		});
	}

	@Override
	public void initializeRepository() {
		netService = retrofitService.getFeedbackRetrofitService();

	}
}
