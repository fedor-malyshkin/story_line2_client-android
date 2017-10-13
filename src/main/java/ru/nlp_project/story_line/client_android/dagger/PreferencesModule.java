package ru.nlp_project.story_line.client_android.dagger;

import dagger.Module;
import dagger.Provides;
import ru.nlp_project.story_line.client_android.ui.preferences.IPreferencesPresenter;
import ru.nlp_project.story_line.client_android.ui.preferences.PreferencesPresenterImpl;

/**
 * Created by fedor on 05.02.17.
 */
@Module
public class PreferencesModule {

	@Provides
	@PreferencesScope
	public IPreferencesPresenter providePreferencesPresenter(PreferencesPresenterImpl
			implementation) {
		return implementation;
	}

}
