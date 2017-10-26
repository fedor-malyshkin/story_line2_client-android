package ru.nlp_project.story_line.client_android.ui.feedback;

import ru.nlp_project.story_line.client_android.ui.IPresenter;


public interface IFeedbackPresenter extends IPresenter<IFeedbackView> {

	void sendFeedback(String from, String message);

	void unlock();

	void loadAboutInfo();
}
