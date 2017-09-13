package ru.nlp_project.story_line.client_android.ui.sources_browser;


import android.content.Context;
import android.content.Intent;
import ru.nlp_project.story_line.client_android.ui.categories_browser.ICategoriesBrowserView.ICategorySelectionListener;

public interface ISourcesBrowserView extends ICategorySelectionListener {

	Context getContext();

	void startActivity(Intent intent);

	void startSourcesUpdates();

	void finishSourcesUpdates();
}
