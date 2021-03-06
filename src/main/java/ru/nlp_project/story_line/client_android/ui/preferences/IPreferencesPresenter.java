package ru.nlp_project.story_line.client_android.ui.preferences;

import java.util.List;
import ru.nlp_project.story_line.client_android.business.models.SourceBusinessModel;
import ru.nlp_project.story_line.client_android.ui.IPresenter;

public interface IPreferencesPresenter extends IPresenter<ISourcePreferencesView> {

	String SHARED_PREFERENCES_IS_DARK_THEME_NAME = "is_dark_theme";

	String SHARED_PREFERENCES_SOURCES_NAME = "sources";
	String SHARED_PREFERENCES_FONT_SIZE_KEY_NAME = "font_size";
	String SHARED_PREFERENCES_FONT_SIZE_KEY_DEFAULT = "18";
	String SHARED_PREFERENCES_LAST_STARTUP_DATE = "last_startup_date";

	String SHARED_PREFERENCES_CACHE_SIZE_KEY_NAME = "image_cache_size";
	String SHARED_PREFERENCES_CACHE_SIZE_KEY_DEFAULT = "25";


	List<SourceBusinessModel> getAllSources();

	void updateSourceState(String key, boolean checked);

	boolean isUpdateSourceStateValid(String key, boolean newValue);
}
