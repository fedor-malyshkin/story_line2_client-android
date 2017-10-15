package ru.nlp_project.story_line.client_android.ui.preferences;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

public class PreferencesActivity extends AppCompatActivity  {

	public static final String MASTER_SETTINGS = "master";
	public static final String PREFERENCES_TYPE = "type";
	public static final String SOURCES_SETTINGS = "sources";

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Intent intent = getIntent();
		Fragment fragment = null;
		switch (intent.getStringExtra(PREFERENCES_TYPE)) {
			case MASTER_SETTINGS: {
				fragment = MasterPreferencesFragment.newInstance();
				break;
			}
			case SOURCES_SETTINGS: {
				fragment = SourcesPreferencesFragment.newInstance();
				break;
			}
			default:
				fragment = MasterPreferencesFragment.newInstance();
		}

		// Display the fragment as the main content.
		getSupportFragmentManager().beginTransaction()
				.replace(android.R.id.content, fragment)
				.commit();
	}


}
