package ru.nlp_project.story_line.client_android.dagger;

import dagger.Module;
import dagger.Provides;
import ru.nlp_project.story_line.client_android.business.news_browser.INewsBrowserInteractor;
import ru.nlp_project.story_line.client_android.business.news_browser.NewsBrowserInteractorImpl;
import ru.nlp_project.story_line.client_android.business.news_watcher.INewsWatcherInteractor;
import ru.nlp_project.story_line.client_android.business.news_watcher.NewsWatcherInteractorImpl;
import ru.nlp_project.story_line.client_android.ui.news_browser.INewsBrowserPresenter;
import ru.nlp_project.story_line.client_android.ui.news_browser.NewsBrowserPresenterImpl;
import ru.nlp_project.story_line.client_android.ui.news_watcher.INewsWatcherPresenter;
import ru.nlp_project.story_line.client_android.ui.news_watcher.NewsWatcherPresenterImpl;

@Module
public class NewsWatcherModule {

	@Provides
	@NewsWatcherScope
	public INewsWatcherPresenter provideNewsWatcherPresenter(NewsWatcherPresenterImpl
		implementation) {
		return implementation;
	}

	@Provides
	@NewsWatcherScope
	public INewsWatcherInteractor provideNewsWatcherInteractor(NewsWatcherInteractorImpl
		implementation) {
		return implementation;
	}

}
