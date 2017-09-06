package ru.nlp_project.story_line.client_android.ui.preferences;

import ru.nlp_project.story_line.client_android.business.models.SourceBusinessModel;
import ru.nlp_project.story_line.client_android.ui.IPresenter;

public interface IPreferencesPresenter  extends IPresenter<ISourcePreferencesView> {

	void initialize();

	SourceBusinessModel getSource(int position);

	int getSourcesCount();

	void saveSources();

	void sourceChangeEnabled(int position, boolean checked);
}
