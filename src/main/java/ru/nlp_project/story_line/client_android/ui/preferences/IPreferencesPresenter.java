package ru.nlp_project.story_line.client_android.ui.preferences;

import java.util.List;
import ru.nlp_project.story_line.client_android.business.models.SourceBusinessModel;
import ru.nlp_project.story_line.client_android.ui.IPresenter;

public interface IPreferencesPresenter extends IPresenter<ISourcePreferencesView> {

	String SHARED_PREFERENCES_SOURCES_NAME = "sources";

	List<SourceBusinessModel> getAllSources();

	void updateSourceState(String key, boolean checked);

	boolean isUpdateSourceStateValid(String key, boolean newValue);
}
