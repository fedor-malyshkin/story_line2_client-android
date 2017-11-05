package ru.nlp_project.story_line.client_android.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.preference.PreferenceManager;
import java.util.Date;
import ru.nlp_project.story_line.client_android.ui.preferences.IPreferencesPresenter;
import ru.nlp_project.story_line.client_android.ui.sources_browser.SourcesBrowserActivity;

public class StartupActivity extends AppCompatActivity implements IStartupView {

	private StartupPresenter presenter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// тут создание осуществляется не ч/з Dagger
		presenter = new StartupPresenter();
		presenter.bindView(this);
		presenter.initializePresenter();
		presenter.startupInitialization();
	}


	@Override
	public void startApplication() {
		Intent intent = new Intent(this, SourcesBrowserActivity.class);
		startActivity(intent);
	}

	@Override
	public Context getContext() {
		return getBaseContext();
	}


	@Override
	public Date getStartupDateInPreferences() {
		SharedPreferences mSharedPreferences = PreferenceManager
				.getDefaultSharedPreferences(getContext());
		long lastStartupDate = mSharedPreferences
				.getLong(IPreferencesPresenter.SHARED_PREFERENCES_LAST_STARTUP_DATE,
						0);
		return lastStartupDate == 0 ? null : new Date(lastStartupDate);
	}

	@Override
	public void storeStartupDateInPreferences(Date date) {
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(getContext());
		Editor editor = prefs.edit();
		editor.putLong(IPreferencesPresenter.SHARED_PREFERENCES_LAST_STARTUP_DATE, date.getTime());
		editor.apply();
	}

}