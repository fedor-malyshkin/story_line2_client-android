package ru.nlp_project.story_line.client_android.business.feedback;

import io.reactivex.Observable;
import ru.nlp_project.story_line.client_android.business.IInteractor;
import ru.nlp_project.story_line.client_android.business.models.SourceBusinessModel;
import ru.nlp_project.story_line.client_android.ui.feedback.IFeedbackPresenter;
import ru.nlp_project.story_line.client_android.ui.sources_browser.ISourcesBrowserPresenter;

public interface IFeedbackInteractor extends IInteractor<IFeedbackPresenter> {

	Observable<String> getAboutInfo();


	void sendFeedback(String from, String message);
}
