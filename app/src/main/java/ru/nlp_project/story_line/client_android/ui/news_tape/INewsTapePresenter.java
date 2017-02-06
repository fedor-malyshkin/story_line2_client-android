package ru.nlp_project.story_line.client_android.ui.news_tape;

import ru.nlp_project.story_line.client_android.ui.IPresenter;

/**
 * Created by fedor on 04.02.17.
 */

public interface INewsTapePresenter extends IPresenter<INewsTapeView> {
	void reloadNewsArticles();
}
