package ru.nlp_project.story_line.client_android.ui.news_watcher;

import ru.nlp_project.story_line.client_android.ui.IPresenter;

public interface INewsWatcherPresenter extends IPresenter<INewsWatcherView> {

	void setNewsArticleServerId(String newsArticleServerId);

	void loadContent();


	void onShownToUser();

	String formatUrlString(String url, String title);

	void onScrollViewDown();

	void onScrollViewUp();

	boolean processBackPressed();

	void onPressShareNewsFABLayout();

	void onPressShareImageFABLayout();

	void onPressShareURLFABLayout();

	void onPressShareTextFABLayout();

	void onPressGotoSourceFABLayout();

	void onWholeViewClick();
}
