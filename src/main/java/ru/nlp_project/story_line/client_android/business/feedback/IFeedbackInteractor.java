package ru.nlp_project.story_line.client_android.business.feedback;

import io.reactivex.Observable;
import ru.nlp_project.story_line.client_android.business.IInteractor;

public interface IFeedbackInteractor extends IInteractor {

	Observable<String> getAboutInfo();


	void sendFeedback(String from, String message);
}
