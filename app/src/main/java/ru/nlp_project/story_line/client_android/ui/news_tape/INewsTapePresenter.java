package ru.nlp_project.story_line.client_android.ui.news_tape;

import ru.nlp_project.story_line.client_android.ui.IPresenter;

public interface INewsTapePresenter extends IPresenter<INewsTapeView> {
	void reloadNewsHeaders();

	void initialize(String sourceDomain, String sourceShortName, String sourceName);
}
