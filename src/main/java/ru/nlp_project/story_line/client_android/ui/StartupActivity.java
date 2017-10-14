package ru.nlp_project.story_line.client_android.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import ru.nlp_project.story_line.client_android.ui.sources_browser.SourcesBrowserActivity;

public class StartupActivity extends AppCompatActivity implements IStartupView {

	private StartupPresenter presenter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// тут создание осуществляется не ч/з Dagger
		presenter = new StartupPresenter();
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
		return getContext();
	}
}
