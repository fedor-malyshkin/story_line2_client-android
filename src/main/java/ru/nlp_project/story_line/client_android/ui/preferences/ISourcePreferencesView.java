package ru.nlp_project.story_line.client_android.ui.preferences;

import ru.nlp_project.story_line.client_android.ui.IPresenter;

public interface ISourcePreferencesView {

	void startUpdates();

	void finishUpdates();

	void sourceChangeEnabled(int position, boolean checked);
}