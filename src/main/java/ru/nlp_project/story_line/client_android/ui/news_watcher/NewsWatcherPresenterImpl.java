package ru.nlp_project.story_line.client_android.ui.news_watcher;

import io.reactivex.Scheduler;
import javax.inject.Inject;
import ru.nlp_project.story_line.client_android.business.news_watcher.INewsWatcherInteractor;
import ru.nlp_project.story_line.client_android.dagger.NewsWatcherScope;
import ru.nlp_project.story_line.client_android.dagger.SchedulerType;
import ru.nlp_project.story_line.client_android.ui.utils.StringUtils;

@NewsWatcherScope
public class NewsWatcherPresenterImpl implements INewsWatcherPresenter {

	@Inject
	@SchedulerType(SchedulerType.ui)
	public Scheduler uiScheduler;
	@Inject
	INewsWatcherInteractor interactor;


	private String newsArticleServerId;
	private INewsWatcherView view;


	@Inject
	public NewsWatcherPresenterImpl() {
	}

	@Override
	public void bindView(INewsWatcherView view) {
		this.view = view;
	}

	@Override
	public void unbindView() {
		this.view = null;
	}


	@Override
	public void initializePresenter() {
		loadContent();
	}

	@Override
	public void setNewsArticleServerId(String newsArticleServerId) {
		this.newsArticleServerId = newsArticleServerId;
	}

	@Override
	public void loadContent() {
		interactor.createNewsArticleRemoteCachedStream(newsArticleServerId).observeOn(uiScheduler)
				.subscribe(newsArticle -> {
					view.setContent(newsArticle.getServerId(),
							interactor.getSourceTitleShortCached(newsArticle.getSource()),
							StringUtils.dateDatePresentation(view.getContext(), newsArticle.getPublicationDate()),
							newsArticle.getTitle(), newsArticle.getContent(), newsArticle.getUrl(),
							newsArticle.getImageUrl());
				});
	}

	@Override
	public void onShownToUser() {
		// collapse in any case
		view.collapseFABMenuFast();
		view.showFABMenu();
		view.refreshPresentation();
	}

	@Override
	public String formatUrlString(String url, String title) {
		return String.format("<a href=\"%s\">%s</a>", url, title);
	}

	@Override
	public void onScrollViewDown() {
		if (view.isFABMenuShown()) {
			view.collapseFABMenuFast();
			view.hideFABMenu();
		}
	}

	@Override
	public void onScrollViewUp() {
		if (!view.isFABMenuShown()) {
			view.showFABMenu();
		}

	}

	@Override
	public boolean processBackPressed() {
		if (view.isFABMenuExpanded()) {
			view.collapseFABMenu();
			return true;
		}
		return false;

	}

	@Override
	public void onPressShareNewsFABLayout() {
		view.shareNews();
		view.collapseFABMenu();
	}

	@Override
	public void onPressShareImageFABLayout() {
		view.shareImage();
		view.collapseFABMenu();

	}

	@Override
	public void onPressShareURLFABLayout() {
		view.shareURL();
		view.collapseFABMenu();

	}

	@Override
	public void onPressShareTextFABLayout() {
		view.shareText();
		view.collapseFABMenu();

	}

	@Override
	public void onPressGotoSourceFABLayout() {
		view.gotoSource();
		view.collapseFABMenu();

	}

	@Override
	public void onWholeViewClick() {
		// FAB menu
		if (view.isFABMenuExpanded()) {
			view.collapseFABMenu();
		}
		// FAB
		if (view.isFABMenuShown()) {
			view.hideFABMenu();
		} else {
			view.showFABMenu();
		}

	}

}
