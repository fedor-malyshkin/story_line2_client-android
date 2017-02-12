package ru.nlp_project.story_line.client_android.ui.news_watcher;

import android.support.v4.app.Fragment;
import ru.nlp_project.story_line.client_android.ui.IPresenter;


public interface INewsWatcherPresenter extends IPresenter<INewsWatcherView> {

	int getFragmentsCount();

	Fragment getFragmentByIndex(int position);

}
