package ru.nlp_project.story_line.client_android.dagger;

import dagger.Module;
import dagger.Provides;
import ru.nlp_project.story_line.client_android.ui.sources_browser.ISourcesBrowserPresenter;
import ru.nlp_project.story_line.client_android.ui.sources_browser.SourcesBrowserPresenterImpl;

/**
 * Created by fedor on 05.02.17.
 */
@Module
public class SourcesBrowserModule {

	@Provides
	@SourcesBrowserScope
	public ISourcesBrowserPresenter provideSourcesBrowserPresenter(SourcesBrowserPresenterImpl
			implementation) {
		return implementation;
	}

}
