package ru.nlp_project.story_line.client_android.ui.sources_browser;

import android.support.v4.app.Fragment;
import ru.nlp_project.story_line.client_android.ui.IPresenter;


public interface ISourcesBrowserPresenter extends IPresenter<ISourcesBrowserView> {

	int getFragmentsCount();

	Fragment getFragmentByIndex(int position);
}