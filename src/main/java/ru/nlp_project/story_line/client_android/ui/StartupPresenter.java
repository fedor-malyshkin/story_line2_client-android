package ru.nlp_project.story_line.client_android.ui;

import android.support.v7.preference.PreferenceManager;
import javax.inject.Inject;
import ru.nlp_project.story_line.client_android.R;
import ru.nlp_project.story_line.client_android.business.IStartupInteractor;
import ru.nlp_project.story_line.client_android.dagger.ApplicationComponent;
import ru.nlp_project.story_line.client_android.dagger.DaggerBuilder;

public class StartupPresenter implements IStartupPresenter {

	@Inject
	IStartupInteractor interactor;
	private IStartupView view;

	public StartupPresenter() {

	}


	@Override
	public void initializePresenter() {
		ApplicationComponent builder = DaggerBuilder
				.getApplicationBuilder();
		builder.inject(this);
		interactor.bindPresenter(this);
	}

	@Override
	public void bindView(IStartupView view) {
		this.view = view;
	}

	@Override
	public void unbindView() {

	}


	@Override
	public void startApplication() {
		view.startApplication();
	}

	public void startupInitialization() {
		PreferenceManager.setDefaultValues(view.getContext(), R.xml.preferences_master, false);
		interactor.startupInitialization();
		startApplication();
	}
}
