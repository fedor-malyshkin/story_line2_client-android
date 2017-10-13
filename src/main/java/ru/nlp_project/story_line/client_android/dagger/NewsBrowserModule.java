package ru.nlp_project.story_line.client_android.dagger;

import dagger.Module;
import dagger.Provides;
import ru.nlp_project.story_line.client_android.business.news_browser.INewsBrowserInteractor;
import ru.nlp_project.story_line.client_android.business.news_browser.NewsBrowserInteractorImpl;
import ru.nlp_project.story_line.client_android.ui.news_browser.INewsBrowserPresenter;
import ru.nlp_project.story_line.client_android.ui.news_browser.NewsBrowserPresenterImpl;

@Module
public class NewsBrowserModule {

	@Provides
	@NewsBrowserScope
	public INewsBrowserPresenter provideNewsBrowserPresenter(NewsBrowserPresenterImpl
		implementation) {
		return implementation;
	}

}
