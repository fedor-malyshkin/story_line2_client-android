package ru.nlp_project.story_line.client_android.business;

import javax.inject.Inject;
import ru.nlp_project.story_line.client_android.business.sources_browser.ISourcesBrowserInteractor;
import ru.nlp_project.story_line.client_android.data.IStartupRepository;
import ru.nlp_project.story_line.client_android.ui.IStartupPresenter;

public class StartupInteractorImpl implements IStartupInteractor {

	@Inject
	IStartupRepository repository;
	@Inject
	ISourcesBrowserInteractor sourcesBrowserPresenter;

	@Inject
	public StartupInteractorImpl() {
	}

	@Override
	public void initialize(IStartupPresenter presenter) {
		// do something
		repository.initialize();
		// sourcesBrowserPresenter is initialized in Dagger2

		presenter.startApplication();
	}
}
