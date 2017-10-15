package ru.nlp_project.story_line.client_android.business.preferences;

import io.reactivex.Observable;
import javax.inject.Inject;
import ru.nlp_project.story_line.client_android.business.models.SourceBusinessModel;
import ru.nlp_project.story_line.client_android.business.sources_browser.ISourcesBrowserInteractor;
import ru.nlp_project.story_line.client_android.data.source.ISourcesRepository;
import ru.nlp_project.story_line.client_android.ui.preferences.IPreferencesPresenter;

public class PreferencesInteractorImpl implements IPreferencesInteractor {

	private static final String TAG = PreferencesInteractorImpl.class.getSimpleName();

	@Inject
	ISourcesRepository repository;
	@Inject
	ISourcesBrowserInteractor sourcesBrowserInteractor;
	private IPreferencesPresenter presenter;

	@Inject
	public PreferencesInteractorImpl() {
	}

	@Override
	public Observable<SourceBusinessModel> createCombinedSourcesStream() {
		return sourcesBrowserInteractor.createCombinedSourcesRemoteCachedStream();
	}

	@Override
	public void updateSourceState(String key, boolean checked) {
		sourcesBrowserInteractor.updateSourceState(key,checked);
	}

	@Override
	public long getActiveSourcesCount() {
		return sourcesBrowserInteractor.getActiveSourcesCount();
	}

	@Override
	public void initializeInteractor() {

	}

	@Override
	public void bindPresenter(IPreferencesPresenter presenter) {
		this.presenter = presenter;
	}
}
