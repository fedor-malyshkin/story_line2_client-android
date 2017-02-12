package ru.nlp_project.story_line.client_android.ui.sources_browser;

import android.support.v4.app.Fragment;
import android.util.Log;
import io.reactivex.Observable;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import ru.nlp_project.story_line.client_android.business.models.SourceBusinessModel;
import ru.nlp_project.story_line.client_android.business.sources_browser.ISourcesBrowserInteractor;
import ru.nlp_project.story_line.client_android.dagger.SourcesBrowserScope;
import ru.nlp_project.story_line.client_android.ui.news_tape.NewsTapeFragment;

/**
 * Created by fedor on 07.02.17.
 */
@SourcesBrowserScope
public class SourcesBrowserPresenterImpl implements ISourcesBrowserPresenter {

	private ISourcesBrowserView view;

	@Inject
	public SourcesBrowserPresenterImpl() {
	}

	List<SourceBusinessModel> sources = new ArrayList<SourceBusinessModel>();

	@Inject
	ISourcesBrowserInteractor interactor;

	@Override
	public void initialize() {
		loadSources();
	}

	private void loadSources() {
		Observable<SourceBusinessModel> stream = interactor.createSourceStream();
		Iterable<SourceBusinessModel> models = stream.blockingIterable();
		sources.clear();
		for (SourceBusinessModel m : models) {
			sources.add(m);
		}
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
		return sources.get(position).getShortName();
	}

	@Override
	public void bindView(ISourcesBrowserView view) {
		this.view = view;
	}

	@Override
	public void unbindView() {
		this.view = null;
	}

	@Override
	public void categorySelected(String category) {
		Log.i("categorySelected", category);
	}
}
