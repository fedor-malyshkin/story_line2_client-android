package ru.nlp_project.story_line.client_android.dagger;

import dagger.Module;
import dagger.Provides;
import ru.nlp_project.story_line.client_android.ui.feedback.FeedbackPresenterImpl;
import ru.nlp_project.story_line.client_android.ui.feedback.IFeedbackPresenter;

/**
 * Created by fedor on 05.02.17.
 */
@Module
public class FeedbackModule {

	@Provides
	@FeedbackScope
	public IFeedbackPresenter provideFeedbackPresenter(FeedbackPresenterImpl implementation) {
		return implementation;
	}

}
