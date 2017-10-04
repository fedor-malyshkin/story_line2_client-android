package ru.nlp_project.story_line.client_android;

import android.app.Application;
import android.content.Context;
import ru.nlp_project.story_line.client_android.dagger.DaggerBuilder;


public class ClientAndroidApp extends Application {

	public static final String PREFRERENCES_KEY = "preferences";

	@Override
	public void onCreate() {
		super.onCreate();
		Context applicationContext = getApplicationContext();
		DaggerBuilder.inintialize(applicationContext);
//		Stetho.initializeWithDefaults(this);
	}

}
