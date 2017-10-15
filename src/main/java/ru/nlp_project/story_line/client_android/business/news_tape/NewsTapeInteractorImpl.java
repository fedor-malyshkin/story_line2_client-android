package ru.nlp_project.story_line.client_android.business.news_tape;

import io.reactivex.Observable;
import javax.inject.Inject;
import ru.nlp_project.story_line.client_android.business.models.NewsHeaderBusinessModel;
import ru.nlp_project.story_line.client_android.business.sources_browser.ISourcesBrowserInteractor;
import ru.nlp_project.story_line.client_android.data.news_header.INewsHeadersRepository;
import ru.nlp_project.story_line.client_android.ui.news_browser.INewsBrowserPresenter;

public class NewsTapeInteractorImpl implements INewsTapeInteractor {

	@Inject
	INewsHeadersRepository repository;
	@Inject
	ISourcesBrowserInteractor sourcesBrowserInteractor;


	@Inject
	public NewsTapeInteractorImpl() {
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

	@Override
	public void bindPresenter(INewsBrowserPresenter presenter) {

	}
}
