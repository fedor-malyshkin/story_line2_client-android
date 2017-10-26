package ru.nlp_project.story_line.client_android.dagger;

import dagger.Subcomponent;
import ru.nlp_project.story_line.client_android.ui.feedback.AboutActivity;
import ru.nlp_project.story_line.client_android.ui.feedback.FeedbackActivity;
import ru.nlp_project.story_line.client_android.ui.sources_browser.SourcesBrowserActivity;

/**
 * Created by fedor on 05.02.17.
 */

@Subcomponent(modules = FeedbackModule.class)
@FeedbackScope
public abstract class FeedbackComponent {

	public abstract void inject(FeedbackActivity activity);

	public abstract void inject(AboutActivity activity);
}
