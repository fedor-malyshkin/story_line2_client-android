package ru.nlp_project.story_line.client_android.ui.news_browser;

import android.support.v4.app.Fragment;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import ru.nlp_project.story_line.client_android.business.news_browser.INewsBrowserInteractor;
import ru.nlp_project.story_line.client_android.dagger.NewsBrowserScope;
import ru.nlp_project.story_line.client_android.ui.news_watcher.NewsWatcherFragment;

@NewsBrowserScope
public class NewsBrowserPresenterImpl implements INewsBrowserPresenter {

	@Inject
	INewsBrowserInteractor interactor;
	private INewsBrowserView view;
	private List<String> articleServerIds;
	private int articlePos;

	@Inject
	public NewsBrowserPresenterImpl() {
	}

	@Override
	public int getFragmentsCount() {
		return articleServerIds.size();
	}

	@Override
	public Fragment getFragmentByIndex(int position) {
		String serverId = articleServerIds.get(position);
		return NewsWatcherFragment.newInstance(serverId);
	}

	@Override
	public void initialize(ArrayList<String> articleServerIds, int articlePos) {
		this.articleServerIds = articleServerIds;
		this.articlePos = articlePos;
	}

	@Override
	public CharSequence getFragmentTitleByIndex(int position) {
		return "" + position;
	}

	@Override
	public void bindView(INewsBrowserView view) {
		this.view = view;
	}

	@Override
	public void unbindView() {
		this.view = null;
	}

}
