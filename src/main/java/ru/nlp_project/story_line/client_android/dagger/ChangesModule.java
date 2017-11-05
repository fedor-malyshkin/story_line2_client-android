package ru.nlp_project.story_line.client_android.dagger;

import dagger.Module;
import dagger.Provides;
import ru.nlp_project.story_line.client_android.ui.changes.ChangesPresenterImpl;
import ru.nlp_project.story_line.client_android.ui.changes.IChangesPresenter;
import ru.nlp_project.story_line.client_android.ui.news_browser.INewsBrowserPresenter;
import ru.nlp_project.story_line.client_android.ui.news_browser.NewsBrowserPresenterImpl;

@Module
public class ChangesModule {

	@Provides
	@ChangesScope
	public IChangesPresenter provideNewsBrowserPresenter(ChangesPresenterImpl
			implementation) {
		return implementation;
	}

}
