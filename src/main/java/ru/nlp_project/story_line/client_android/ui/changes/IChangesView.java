package ru.nlp_project.story_line.client_android.ui.changes;


import android.content.Context;

public interface IChangesView {

	Context getContext();

	void setChangesText(String presentation);

	void hideDialog();
}
