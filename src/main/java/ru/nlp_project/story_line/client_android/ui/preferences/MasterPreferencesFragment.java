package ru.nlp_project.story_line.client_android.ui.preferences;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.preference.PreferenceScreen;
import javax.inject.Inject;
import ru.nlp_project.story_line.client_android.R;

public class MasterPreferencesFragment extends PreferenceFragmentCompat {

	@Inject
	IPreferencesPresenter presenter;

	public MasterPreferencesFragment() {
	}

	public static PreferenceFragmentCompat newInstance() {
		MasterPreferencesFragment result = new MasterPreferencesFragment();
		return result;
	}

	@Override
	public void onCreatePreferences(Bundle bundle, String s) {
		setPreferencesFromResource(R.xml.preferences_master, s);
	}

	@Override
	public void onNavigateToScreen(PreferenceScreen preferenceScreen) {
		if (preferenceScreen.getKey().equalsIgnoreCase("screen_preference")) {
			Intent intent = new Intent(getContext(), PreferencesActivity.class);
			intent.putExtra(PreferencesActivity.PREFERENCES_TYPE, PreferencesActivity.SOURCES_SETTINGS);
			startActivity(intent);
		}
		super.onNavigateToScreen(preferenceScreen);
	}
}
