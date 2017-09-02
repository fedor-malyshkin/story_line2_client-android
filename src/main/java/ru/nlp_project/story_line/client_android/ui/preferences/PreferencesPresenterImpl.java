package ru.nlp_project.story_line.client_android.ui.preferences;

import javax.inject.Inject;
import ru.nlp_project.story_line.client_android.dagger.PreferencesScope;

@PreferencesScope
public class PreferencesPresenterImpl implements IPreferencesPresenter {

	private IPreferencesView view;

	@Override
	public void bindView(IPreferencesView view) {
		this.view = view;
	}

	@Override
	public void unbindView() {
		this.view = null;
	}

	@Override
	public void initialize() {

	}

	@Inject
	public PreferencesPresenterImpl() {
	}
}
