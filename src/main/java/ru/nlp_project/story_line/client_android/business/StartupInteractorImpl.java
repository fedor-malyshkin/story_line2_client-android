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
	private IStartupPresenter presenter;

	@Inject
	public StartupInteractorImpl() {
	}


	@Override
	public void initializeInteractor() {

	}

	@Override
	public void bindPresenter(IStartupPresenter presenter) {
		this.presenter = presenter;
	}


	@Override
	public void startupInitialization() {
	}
}
