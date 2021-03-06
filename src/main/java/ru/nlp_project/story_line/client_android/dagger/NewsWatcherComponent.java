package ru.nlp_project.story_line.client_android.dagger;

import dagger.Subcomponent;
import ru.nlp_project.story_line.client_android.ui.news_browser.NewsBrowserActivity;
import ru.nlp_project.story_line.client_android.ui.news_watcher.NewsWatcherFragment;

@Subcomponent(modules = NewsWatcherModule.class)
@NewsWatcherScope
public abstract class NewsWatcherComponent {

	public abstract void inject(NewsWatcherFragment fragment);
}
