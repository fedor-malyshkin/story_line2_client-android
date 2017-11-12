package ru.nlp_project.story_line.client_android;

import android.app.Application;
import android.content.Context;
import ru.nlp_project.story_line.client_android.dagger.DaggerBuilder;


public class ClientAndroidApp extends Application {

	@Override
	public void onCreate() {
		super.onCreate();
		Context applicationContext = getApplicationContext();
		// important part to pass context to builder
		DaggerBuilder.initialize(applicationContext);
//		Stetho.initializeWithDefaults(this);
	}

}
