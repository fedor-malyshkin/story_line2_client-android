package ru.nlp_project.story_line.client_android.ui.news_browser;

import android.support.v4.app.Fragment;
import java.util.ArrayList;
import ru.nlp_project.story_line.client_android.ui.IPresenter;


public interface INewsBrowserPresenter extends IPresenter<INewsBrowserView> {

	int getFragmentsCount();

	Fragment getFragmentByIndex(int position);

	CharSequence getFragmentTitleByIndex(int position);

	void setData(ArrayList<String> articleServerIds, int articlePos);

	boolean openSettings();

	boolean openSearch();

	boolean decreaseFont();

	boolean increaseFont();
}
