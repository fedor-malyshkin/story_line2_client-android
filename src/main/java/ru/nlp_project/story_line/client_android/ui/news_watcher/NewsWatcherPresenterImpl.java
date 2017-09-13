package ru.nlp_project.story_line.client_android.ui.news_watcher;

import io.reactivex.Scheduler;
import javax.inject.Inject;
import ru.nlp_project.story_line.client_android.business.news_watcher.INewsWatcherInteractor;
import ru.nlp_project.story_line.client_android.dagger.NewsWatcherScope;
import ru.nlp_project.story_line.client_android.dagger.SchedulerType;

@NewsWatcherScope
public class NewsWatcherPresenterImpl implements INewsWatcherPresenter {

	@Inject
	@SchedulerType(SchedulerType.ui)
	public Scheduler uiScheduler;

	private String newsArticleServerId;

	@Inject
	public NewsWatcherPresenterImpl() {
	}

	@Inject
	INewsWatcherInteractor interactor;

	private INewsWatcherView view;


	@Override
	public void bindView(INewsWatcherView view) {
		this.view = view;
	}

	@Override
	public void unbindView() {
		this.view = null;
	}


	@Override
	public void initialize(String newsArticleServerId) {
		this.newsArticleServerId = newsArticleServerId;

	}

	@Override
	public void loadContent() {
		interactor.createNewsArticleRemoteCachedStream(newsArticleServerId).observeOn(uiScheduler)
			.subscribe(newsArticle -> {
				view.setContent(newsArticle.getTitle(), newsArticle.getContent(), newsArticle
					.getImageUrl());
			});
	}
}
