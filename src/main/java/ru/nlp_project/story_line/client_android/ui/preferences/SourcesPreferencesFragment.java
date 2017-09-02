package ru.nlp_project.story_line.client_android.ui.preferences;

import android.os.Bundle;
import android.support.v7.preference.PreferenceFragmentCompat;
import javax.inject.Inject;
import ru.nlp_project.story_line.client_android.R;

public class SourcesPreferencesFragment extends PreferenceFragmentCompat {

	@Inject
	IPreferencesPresenter presenter;

	public SourcesPreferencesFragment() {
	}

	public static PreferenceFragmentCompat newInstance() {
		SourcesPreferencesFragment result = new SourcesPreferencesFragment();
		return result;
	}


	@Override
	public void onCreatePreferences(Bundle bundle, String s) {
		setPreferencesFromResource(R.xml.preferences_sources, s);
	}


}
