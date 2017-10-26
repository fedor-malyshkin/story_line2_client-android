package ru.nlp_project.story_line.client_android.data.feedback;

import io.reactivex.Observable;
import ru.nlp_project.story_line.client_android.data.IRepository;

public interface IFeedbackRepository extends IRepository {

	Observable<String> getAboutInfo();

	void sendFeedback(String from, String feedBack);

}
