package ru.nlp_project.story_line.client_android.ui.preferences;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javax.inject.Inject;
import ru.nlp_project.story_line.client_android.business.models.SourceBusinessModel;
import ru.nlp_project.story_line.client_android.business.preferences.IPreferencesInteractor;
import ru.nlp_project.story_line.client_android.dagger.PreferencesScope;
import ru.nlp_project.story_line.client_android.dagger.SchedulerType;

@PreferencesScope
public class PreferencesPresenterImpl implements IPreferencesPresenter {

	@Inject
	@SchedulerType(SchedulerType.ui)
	public Scheduler uiScheduler;
	@Inject
	IPreferencesInteractor interactor;
	private List<SourceBusinessModel> sources = new ArrayList<>();
	private ISourcePreferencesView view;

	@Inject
	public PreferencesPresenterImpl() {
	}

	@Override
	public void bindView(ISourcePreferencesView view) {
		this.view = view;
	}

	@Override
	public void unbindView() {
		this.view = null;
	}

	@Override
	public void initializePresenter() {
		loadSources();
	}

	private void loadSources() {
		Observable<SourceBusinessModel> stream = interactor.createCombinedSourcePreferencesRemoteCachedStream();
		Iterable<SourceBusinessModel> models = stream.blockingIterable();

		view.startUpdates();
		sources.clear();
		for (SourceBusinessModel m : models) {
			sources.add(m);
		}
		Collections.sort(sources, new Comparator<SourceBusinessModel>() {
			@Override
			public int compare(SourceBusinessModel o1, SourceBusinessModel o2) {
				return
						o1.getOrder() - o2.getOrder();
			}
		});

		view.finishUpdates();
	}

	@Override
	public SourceBusinessModel getSource(int position) {
		return sources.get(position);
	}

	@Override
	public int getSourcesCount() {
		return sources.size();
	}

	@Override
	public void saveSources() {
		interactor.upsetSources(sources);
	}

	@Override
	public void onSourceEnabledChanged(int position, boolean checked) {
		SourceBusinessModel model = sources.get(position);
		model.setEnabled(checked);
	}
}
