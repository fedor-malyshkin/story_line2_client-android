package ru.nlp_project.story_line.client_android.dagger;

import dagger.Module;
import dagger.Provides;
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


}
