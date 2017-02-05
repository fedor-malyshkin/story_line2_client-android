package ru.nlp_project.story_line.client_android;

import android.app.Application;
import android.content.Context;

import ru.nlp_project.story_line.client_android.dagger.DaggerBuilder;

/**
 * Created by fedor on 05.02.17.
 */

public class ClientAndroidApp extends Application {
	@Override
	public void onCreate() {
		super.onCreate();
		Context applicationContext = getApplicationContext();
		DaggerBuilder.inintialize(applicationContext);
	}
}
