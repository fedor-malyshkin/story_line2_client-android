package ru.nlp_project.story_line.client_android.dagger;

import android.support.v4.app.Fragment;
import android.support.v7.preference.PreferenceFragmentCompat;
import dagger.Subcomponent;
import ru.nlp_project.story_line.client_android.ui.preferences.PreferencesActivity;
import ru.nlp_project.story_line.client_android.ui.preferences.SourcesEditorPreferencesFragment;

/**
 * Created by fedor on 05.02.17.
 */

@Subcomponent(modules = PreferencesModule.class)
@PreferencesScope
public abstract class PreferencesComponent {

	public abstract void inject(PreferencesActivity activity);

	public abstract void inject(SourcesEditorPreferencesFragment fragment);
}