package ru.nlp_project.story_line.client_android.ui.news_browser;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import ru.nlp_project.story_line.client_android.business.news_headers.INewsHeadersInteractor;
import ru.nlp_project.story_line.client_android.dagger.NewsBrowserScope;
import ru.nlp_project.story_line.client_android.ui.news_watcher.NewsWatcherFragment;
import ru.nlp_project.story_line.client_android.ui.preferences.PreferencesActivity;

@NewsBrowserScope
public class NewsBrowserPresenterImpl implements INewsBrowserPresenter {

	@Inject
	INewsHeadersInteractor interactor;
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
	public void initializePresenter() {
	}

	@Override
	public CharSequence getFragmentTitleByIndex(int position) {
		return "" + position;
	}

	@Override
	public void setData(ArrayList<String> articleServerIds, int articlePos) {
		this.articleServerIds = articleServerIds;
		this.articlePos = articlePos;
	}

	@Override
	public boolean openSettings() {
		Intent intent = new Intent(view.getContext(), PreferencesActivity.class);
		intent.putExtra(PreferencesActivity.PREFERENCES_TYPE, PreferencesActivity.MASTER_SETTINGS);
		view.startActivity(intent);
		return true;

	}

	@Override
	public void bindView(INewsBrowserView view) {
		this.view = view;
	}

	@Override
	public void unbindView() {
		this.view = null;
	}


	@Override
	public boolean openSearch() {
		Toast toast = Toast.makeText(view.getContext(), "search", Toast.LENGTH_SHORT);
		toast.show();
		return true;
	}

	@Override
	public boolean decreaseFont() {
		view.decreaseFont();
//		view.refreshCurrentNewsWatcher();
		return true;
	}

	@Override
	public boolean increaseFont() {
		view.increaseFont();
//		view.refreshCurrentNewsWatcher();
		return false;
	}
}
