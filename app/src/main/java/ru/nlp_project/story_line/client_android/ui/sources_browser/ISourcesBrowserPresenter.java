package ru.nlp_project.story_line.client_android.ui.sources_browser;

import android.support.v4.app.Fragment;
import ru.nlp_project.story_line.client_android.ui.IPresenter;
import ru.nlp_project.story_line.client_android.ui.categories_browser.ICategoriesBrowserView.ICategorySelectionListener;


public interface ISourcesBrowserPresenter extends IPresenter<ISourcesBrowserView>,
	ICategorySelectionListener {

	int getFragmentsCount();

	Fragment getFragmentByIndex(int position);

	void initialize();

	CharSequence getFragmentTitleByIndex(int position);
}
