package ru.nlp_project.story_line.client_android.business.feedback;

import io.reactivex.Observable;
import javax.inject.Inject;
import ru.nlp_project.story_line.client_android.data.feedback.IFeedbackRepository;


public class FeedbackInteractorImpl implements IFeedbackInteractor {

	private static final String TAG = FeedbackInteractorImpl.class.getName();
	@Inject
	IFeedbackRepository repository;

	@Inject
	public FeedbackInteractorImpl() {
	}

	@Override
	public void initializeInteractor() {
	}

	@Override
	public Observable<String> getAboutInfo() {
		return repository.getAboutInfo();
	}

	@Override
	public void sendFeedback(String from, String message) {
		repository.sendFeedback(from, message);
	}
}
