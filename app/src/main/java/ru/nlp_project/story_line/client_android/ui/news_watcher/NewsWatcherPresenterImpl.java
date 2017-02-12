package ru.nlp_project.story_line.client_android.ui.news_watcher;

import javax.inject.Inject;
import ru.nlp_project.story_line.client_android.business.news_watcher.INewsWatcherInteractor;
import ru.nlp_project.story_line.client_android.dagger.NewsTapeScope;
import ru.nlp_project.story_line.client_android.dagger.NewsWatcherScope;

@NewsWatcherScope
public class NewsWatcherPresenterImpl implements INewsWatcherPresenter {

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

}
