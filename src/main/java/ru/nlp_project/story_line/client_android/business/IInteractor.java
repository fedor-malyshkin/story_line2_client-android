package ru.nlp_project.story_line.client_android.business;

import ru.nlp_project.story_line.client_android.ui.IPresenter;

public interface IInteractor<T> {

	void initializeInteractor();

	void bindPresenter(T presenter);
}
