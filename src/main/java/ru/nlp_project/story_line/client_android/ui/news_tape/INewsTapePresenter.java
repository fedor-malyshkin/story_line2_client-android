package ru.nlp_project.story_line.client_android.ui.news_tape;

import ru.nlp_project.story_line.client_android.ui.IPresenter;

public interface INewsTapePresenter extends IPresenter<INewsTapeView> {

	/**
	 * Выполнить первоначальную загрузку/перезагрузку заголовков новостей.
	 */
	void reloadNewsHeaders();

	void initialize(String sourceName, String sourceTitle, String sourceTitleShort);

	/**
	 * Выполнить дополнительную подзагрузку заголовков новостей.
	 * @param serverId идентификатор последней новости в списке
	 */
	void loadMoreNewsHeaders(String serverId);
}
