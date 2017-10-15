package ru.nlp_project.story_line.client_android.business.news_watcher;

import io.reactivex.Single;
import javax.inject.Inject;
import ru.nlp_project.story_line.client_android.business.models.NewsArticleBusinessModel;
import ru.nlp_project.story_line.client_android.business.sources_browser.ISourcesBrowserInteractor;
import ru.nlp_project.story_line.client_android.data.news_article.INewsArticlesRepository;
import ru.nlp_project.story_line.client_android.ui.news_watcher.INewsWatcherPresenter;

public class NewsWatcherInteractorImpl implements INewsWatcherInteractor {

	@Inject
	INewsArticlesRepository repository;
	@Inject
	ISourcesBrowserInteractor sourcesBrowserInteractor;


	@Inject
	public NewsWatcherInteractorImpl() {
	}

	@Override
	public Single<NewsArticleBusinessModel> createNewsArticleRemoteCachedStream(
			String newsArticleServerId) {
		return repository.createNewsArticleRemoteCachedStream(newsArticleServerId);
	}

	@Override
	public String getSourceTitleShortCached(String source) {
		return sourcesBrowserInteractor.getSourceTitleShortCached(source);
	}


	@Override
	public void initializeInteractor() {

	}

	@Override
	public void bindPresenter(INewsWatcherPresenter presenter) {

	}
}
