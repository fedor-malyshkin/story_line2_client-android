package ru.nlp_project.story_line.client_android.business.news_watcher;

import io.reactivex.Single;
import javax.inject.Inject;
import ru.nlp_project.story_line.client_android.business.models.NewsArticleBusinessModel;
import ru.nlp_project.story_line.client_android.dagger.NewsBrowserScope;
import ru.nlp_project.story_line.client_android.dagger.NewsWatcherScope;
import ru.nlp_project.story_line.client_android.data.news_watcher.INewsWatcherRepository;

@NewsWatcherScope
public class NewsWatcherInteractorImpl implements INewsWatcherInteractor {

	@Inject
	INewsWatcherRepository repository;


	@Inject
	public NewsWatcherInteractorImpl() {
	}

	@Override
	public Single<NewsArticleBusinessModel> createCachedNewsArticleStream(String serverId) {
		return null;
	}
}
