package ru.nlp_project.story_line.client_android.business;

import java.util.Date;
import ru.nlp_project.story_line.client_android.ui.IStartupPresenter;

public interface IStartupInteractor extends IInteractor {

	void bindPresenter(IStartupPresenter presenter);

	/**
	 * Execute startup initialization.
	 *
	 * @param lastStartupDate - last application startup date
	 */
	void startupInitialization(Date lastStartupDate);
}
