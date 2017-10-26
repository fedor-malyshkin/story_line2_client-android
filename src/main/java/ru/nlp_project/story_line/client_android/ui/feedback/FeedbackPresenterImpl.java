package ru.nlp_project.story_line.client_android.ui.feedback;

import io.reactivex.Scheduler;
import javax.inject.Inject;
import ru.nlp_project.story_line.client_android.business.feedback.IFeedbackInteractor;
import ru.nlp_project.story_line.client_android.dagger.FeedbackScope;
import ru.nlp_project.story_line.client_android.dagger.SchedulerType;

/**
 * Created by fedor on 07.02.17.
 */
@FeedbackScope
public class FeedbackPresenterImpl implements IFeedbackPresenter {

	@Inject
	IFeedbackInteractor interactor;
	@Inject
	@SchedulerType(SchedulerType.ui)
	Scheduler uiScheduler;


	private IFeedbackView view;

	@Inject
	public FeedbackPresenterImpl() {
	}

	@Override
	public void initializePresenter() {
		interactor.bindPresenter(this);
	}


	@Override
	public void bindView(IFeedbackView view) {
		this.view = view;
	}

	@Override
	public void unbindView() {
		this.view = null;
	}


	@Override
	public void sendFeedback(String from, String message) {
		interactor.sendFeedback(from, message);
		view.clearTextFields();
		view.lockTextFields();
		view.convertSendToUnlockButton();
	}

	@Override
	public void unlock() {
		view.clearTextFields();
		view.unlockTextFields();
		view.convertUnlockToSendButton();
	}

	@Override
	public void loadAboutInfo() {
		interactor.getAboutInfo().observeOn(uiScheduler).subscribe(view::loadAboutInfo, view::loadAboutInfoError);
	}
}
