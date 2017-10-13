package ru.nlp_project.story_line.client_android.business.news_watcher;

import io.reactivex.Single;
import javax.inject.Inject;
import ru.nlp_project.story_line.client_android.business.models.NewsArticleBusinessModel;
import ru.nlp_project.story_line.client_android.data.news_article.INewsArticlesRepository;

public class NewsWatcherInteractorImpl implements INewsWatcherInteractor {

	@Inject
	INewsArticlesRepository repository;


	@Inject
	public NewsWatcherInteractorImpl() {
	}

	@Override
	public Single<NewsArticleBusinessModel> createNewsArticleRemoteCachedStream(
			String newsArticleServerId) {
		return repository.createNewsArticleRemoteCachedStream(newsArticleServerId);
	}


}
