package ru.nlp_project.story_line.client_android.business.feedback;

import io.reactivex.Observable;
import javax.inject.Inject;
import ru.nlp_project.story_line.client_android.data.feedback.IFeedbackRepository;
import ru.nlp_project.story_line.client_android.ui.feedback.IFeedbackPresenter;


public class FeedbackInteractorImpl implements IFeedbackInteractor {

	private static final String TAG = FeedbackInteractorImpl.class.getName();
	private IFeedbackPresenter presenter;
	@Inject
	IFeedbackRepository repository;
	@Inject
	public FeedbackInteractorImpl() {
	}

	@Override
	public void initializeInteractor() {
	}



	@Override
	public void bindPresenter(IFeedbackPresenter presenter) {
		this.presenter = presenter;
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
