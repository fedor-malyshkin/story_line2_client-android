package ru.nlp_project.story_line.client_android.business;

import ru.nlp_project.story_line.client_android.ui.IStartupPresenter;
import ru.nlp_project.story_line.client_android.ui.StartupPresenter;

public interface IStartupInteractor extends  IInteractor<IStartupPresenter>{

	void bindPresenter(IStartupPresenter presenter);

	void startupInitialization();
}
