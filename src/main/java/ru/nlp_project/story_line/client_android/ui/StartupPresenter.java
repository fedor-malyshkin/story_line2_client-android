package ru.nlp_project.story_line.client_android.ui;

import javax.inject.Inject;
import ru.nlp_project.story_line.client_android.business.IStartupInteractor;
import ru.nlp_project.story_line.client_android.dagger.ApplicationComponent;
import ru.nlp_project.story_line.client_android.dagger.DaggerBuilder;

public class StartupPresenter implements IStartupPresenter {

	private final IStartupView view;
	@Inject
	IStartupInteractor interactor;

	public StartupPresenter(IStartupView startupView) {
		this.view = startupView;
	}

	public void initialize() {
		ApplicationComponent builder = DaggerBuilder
				.getApplicationBuilder();
		builder.inject(this);
		interactor.initialize(this);
	}

	@Override
	public void startApplication() {
		view.startApplication();
	}
}
