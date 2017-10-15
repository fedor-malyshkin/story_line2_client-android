package ru.nlp_project.story_line.client_android.dagger;

import dagger.Subcomponent;
import ru.nlp_project.story_line.client_android.ui.preferences.PreferencesActivity;
import ru.nlp_project.story_line.client_android.ui.preferences.SourcesPreferencesFragment;

/**
 * Created by fedor on 05.02.17.
 */

@Subcomponent(modules = PreferencesModule.class)
@PreferencesScope
public abstract class PreferencesComponent {

	public abstract void inject(PreferencesActivity activity);

	public abstract void inject(SourcesPreferencesFragment fragment);
}
