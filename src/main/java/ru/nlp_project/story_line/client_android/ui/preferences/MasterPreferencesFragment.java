package ru.nlp_project.story_line.client_android.ui.preferences;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.preference.ListPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.preference.PreferenceScreen;
import javax.inject.Inject;
import ru.nlp_project.story_line.client_android.R;

public class MasterPreferencesFragment extends PreferenceFragmentCompat {

	@Inject
	IPreferencesPresenter presenter;

	public MasterPreferencesFragment() {
	}

	public static Fragment newInstance() {
		MasterPreferencesFragment result = new MasterPreferencesFragment();
		return result;
	}

	@Override
	public void onCreatePreferences(@Nullable Bundle bundle, String s) {
		setPreferencesFromResource(R.xml.preferences_master, s);
		initializeListeners();
	}

	private void initializeListeners() {
		SharedPreferences preferences = getPreferenceManager().getSharedPreferences();
		// image cache size
		ListPreference imageCacheSizePref = (ListPreference) getPreferenceScreen()
				.findPreference(IPreferencesPresenter.SHARED_PREFERENCES_CACHE_SIZE_KEY_NAME);
		{
			imageCacheSizePref.setSummary(imageCacheSizePref.getValue() + " Mb");
			imageCacheSizePref.setOnPreferenceChangeListener(this::preferenceChangeListener);
		}
		// font size
		ListPreference fontSizePref = (ListPreference) getPreferenceScreen()
				.findPreference(IPreferencesPresenter.SHARED_PREFERENCES_FONT_SIZE_KEY_NAME);
		{
			CharSequence entry = fontSizePref.getEntry();
			fontSizePref.setSummary(entry);
			fontSizePref.setOnPreferenceChangeListener(this::preferenceChangeListener);
		}
	}

	private boolean preferenceChangeListener(Preference preference, Object newValue) {
		SharedPreferences preferences = getPreferenceManager().getSharedPreferences();
		if (preference.getKey()
				.equalsIgnoreCase(IPreferencesPresenter.SHARED_PREFERENCES_CACHE_SIZE_KEY_NAME)) {
			ListPreference imageCacheSizePref = (ListPreference) preference;
			imageCacheSizePref.setSummary(newValue + " Mb");
		} else if (preference.getKey()
				.equalsIgnoreCase(IPreferencesPresenter.SHARED_PREFERENCES_FONT_SIZE_KEY_NAME)) {
			ListPreference fontSizePref = (ListPreference) preference;
			int index = fontSizePref.findIndexOfValue((String) newValue);
			fontSizePref.setSummary(fontSizePref.getEntries()[index]);
		}
		return true;
	}


	@Override
	public void onNavigateToScreen(PreferenceScreen preferenceScreen) {
		if (preferenceScreen.getKey().equalsIgnoreCase("preferences_screen_sources_key")) {
			Intent intent = new Intent(getContext(), PreferencesActivity.class);
			intent.putExtra(PreferencesActivity.PREFERENCES_TYPE, PreferencesActivity.SOURCES_SETTINGS);
			startActivity(intent);
		}
		super.onNavigateToScreen(preferenceScreen);
	}

}
