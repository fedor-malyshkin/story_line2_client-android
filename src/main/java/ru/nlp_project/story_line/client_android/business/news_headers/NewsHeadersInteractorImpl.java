package ru.nlp_project.story_line.client_android.business.news_headers;

import io.reactivex.Observable;
import javax.inject.Inject;
import ru.nlp_project.story_line.client_android.business.models.NewsHeaderBusinessModel;
import ru.nlp_project.story_line.client_android.business.sources.ISourcesInteractor;
import ru.nlp_project.story_line.client_android.data.news_headers.INewsHeadersRepository;

public class NewsHeadersInteractorImpl implements INewsHeadersInteractor {

	@Inject
	INewsHeadersRepository repository;
	@Inject
	ISourcesInteractor sourcesBrowserInteractor;


	@Inject
	public NewsHeadersInteractorImpl() {
	}

	@Override
	public Observable<NewsHeaderBusinessModel> createNewsHeaderRemoteCachedStream(
			String sourceDomain) {
		return repository.createNewsHeaderRemoteCachedStream(sourceDomain, null);
	}

	@Override
	public Observable<NewsHeaderBusinessModel> createAdditionNewsHeaderRemoteCachedStream(
			String sourceDomain,
			String lastNewsHeaderServerId) {
		return repository.createNewsHeaderRemoteCachedStream(sourceDomain, lastNewsHeaderServerId);
	}

	@Override
	public String getSourceTitleShortCached(String sourceName) {
		return sourcesBrowserInteractor.getSourceTitleShortCached(sourceName);
	}


	@Override
	public void initializeInteractor() {

	}


}
