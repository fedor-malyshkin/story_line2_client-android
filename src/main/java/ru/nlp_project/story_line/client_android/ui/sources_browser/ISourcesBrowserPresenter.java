package ru.nlp_project.story_line.client_android.ui.sources_browser;

import android.support.v4.app.Fragment;
import java.util.List;
import ru.nlp_project.story_line.client_android.business.models.SourceBusinessModel;
import ru.nlp_project.story_line.client_android.ui.IPresenter;


public interface ISourcesBrowserPresenter extends IPresenter<ISourcesBrowserView> {

	List<SourceBusinessModel> getSources();

	int getFragmentsCount();

	Fragment getFragmentByIndex(int position);

	void initialize();

	CharSequence getFragmentTitleByIndex(int position);

	boolean openSettings();

	boolean openSources();

	void updateSources();
}
