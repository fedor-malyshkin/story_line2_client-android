package ru.nlp_project.story_line.client_android.ui.sources_browser;

import android.content.Intent;
import android.support.v4.app.Fragment;
import io.reactivex.Observable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javax.inject.Inject;
import ru.nlp_project.story_line.client_android.business.models.SourceBusinessModel;
import ru.nlp_project.story_line.client_android.business.sources_browser.ISourcesBrowserInteractor;
import ru.nlp_project.story_line.client_android.dagger.SourcesBrowserScope;
import ru.nlp_project.story_line.client_android.ui.news_tape.NewsTapeFragment;
import ru.nlp_project.story_line.client_android.ui.preferences.PreferencesActivity;

/**
 * Created by fedor on 07.02.17.
 */
@SourcesBrowserScope
public class SourcesBrowserPresenterImpl implements ISourcesBrowserPresenter {

	@Inject
	ISourcesBrowserInteractor interactor;
	private ISourcesBrowserView view;
	private Comparator<SourceBusinessModel> orderComparator;
	private List<SourceBusinessModel> sources = new ArrayList<>();

	@Inject
	public SourcesBrowserPresenterImpl() {
		orderComparator = new Comparator<SourceBusinessModel>() {

			@Override
			public int compare(SourceBusinessModel o1, SourceBusinessModel o2) {
				return o1.getOrder() - o2.getOrder();
			}
		};
	}

	@Override
	public void initializePresenter() {
	}

	@Override
	public int getFragmentsCount() {
		return sources.size();
	}

	@Override
	public Fragment getFragmentByIndex(int position) {
		SourceBusinessModel model = sources.get(position);
		return NewsTapeFragment.newInstance(model);
	}

	@Override
	public CharSequence getFragmentTitleByIndex(int position) {
		return sources.get(position).getTitleShort();
	}

	@Override
	public boolean openSettings() {
		Intent intent = new Intent(view.getContext(), PreferencesActivity.class);
		intent.putExtra(PreferencesActivity.PREFERENCES_TYPE, PreferencesActivity.MASTER_SETTINGS);
		view.startActivity(intent);
		return true;
	}

	@Override
	public boolean openSources() {
		Intent intent = new Intent(view.getContext(), PreferencesActivity.class);
		intent.putExtra(PreferencesActivity.PREFERENCES_TYPE, PreferencesActivity.SOURCES_SETTINGS);
		view.startActivity(intent);
		return true;
	}

	@Override
	public void refreshSourcesList() {
		checkAndUpdateSources(interactor.createSourceStreamFromCache());
	}

	@Override
	public void bindView(ISourcesBrowserView view) {
		this.view = view;
	}

	@Override
	public void unbindView() {
		this.view = null;
	}

	private void checkAndUpdateSources(Observable<SourceBusinessModel> stream) {
		Iterable<SourceBusinessModel> models = stream.blockingIterable();
		List<SourceBusinessModel> newSources = new ArrayList<>();

		for (SourceBusinessModel m : models) {
			if (m.isEnabled()) {
				newSources.add(m);
			}
		}
		Collections.sort(newSources, orderComparator);
		if (newSources.equals(sources)) {
			return;
		}

// refresh main sources
		view.startSourcesUpdates();
		sources.clear();
		sources.addAll(newSources);
		view.finishSourcesUpdates();
	}

	@Override
	public List<SourceBusinessModel> getSources() {
		return sources;
	}
}
