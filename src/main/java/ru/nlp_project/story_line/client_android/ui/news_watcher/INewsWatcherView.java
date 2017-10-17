package ru.nlp_project.story_line.client_android.ui.news_watcher;

import android.content.Context;
import ru.nlp_project.story_line.client_android.ui.news_browser.INewsBrowserView;

public interface INewsWatcherView {

	/**
	 * Выполнить обновление представления.
	 */
	void refreshPresentation();

	void gotoSource();

	void collapseFABMenuFast();

	void expandFABMenuFast();

	void collapseFABMenu();

	void shareNews();

	void shareImage();

	void shareURL();

	void shareText();

	void expandFABMenu();

	boolean isFABMenuExpanded();

	boolean isFABMenuShown();

	void showFABMenu();

	void hideFABMenu();

	void setContent(String newsArticleId, String sourceTitleShort,
			String publicationDatePresentation, String title, String content, String url,
			String imageUrl);

	Context getContext();

	/**
	 * Выполнить обработку нажатия кнопки назад (back).
	 *
	 * @return выполнена обработка (что-то сделано) или нет
	 */
	boolean processBackPressed();

	/**
	 * Called when this view is shown To User (in ViewPager)
	 *
	 * @param activity activity with viewPager
	 */
	void viewShownToUser(
			INewsBrowserView activity);
}
