package ru.nlp_project.story_line.client_android.business.news_articles;

import io.reactivex.Single;
import javax.inject.Inject;
import ru.nlp_project.story_line.client_android.business.models.NewsArticleBusinessModel;
import ru.nlp_project.story_line.client_android.business.sources.ISourcesInteractor;
import ru.nlp_project.story_line.client_android.data.news_articles.INewsArticlesRepository;

public class NewsArticlesInteractorImpl implements INewsArticlesInteractor {

	@Inject
	INewsArticlesRepository repository;
	@Inject
	ISourcesInteractor sourcesBrowserInteractor;


	@Inject
	public NewsArticlesInteractorImpl() {
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


}
