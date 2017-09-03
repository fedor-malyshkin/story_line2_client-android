package ru.nlp_project.story_line.client_android.ui.preferences;

import io.reactivex.Scheduler;
import javax.inject.Inject;
import ru.nlp_project.story_line.client_android.business.models.SourceBusinessModel;
import ru.nlp_project.story_line.client_android.business.news_watcher.INewsWatcherInteractor;
import ru.nlp_project.story_line.client_android.business.preferences.IPreferencesInteractor;
import ru.nlp_project.story_line.client_android.dagger.PreferencesScope;
import ru.nlp_project.story_line.client_android.dagger.SchedulerType;

@PreferencesScope
public class PreferencesPresenterImpl implements IPreferencesPresenter {

	private ISourcePreferencesView view;

	@Inject
	@SchedulerType(SchedulerType.ui)
	public Scheduler uiScheduler;

	@Inject
	IPreferencesInteractor interactor;

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
	public void initialize() {

	}

	@Override
	public SourceBusinessModel getSource(int position) {
		return null;
	}

	@Override
	public int getSourcesCount() {
		return 0;
	}
}
