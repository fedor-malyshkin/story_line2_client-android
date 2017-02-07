package ru.nlp_project.story_line.client_android.dagger;

import android.content.Context;

/**
 * Created by fedor on 04.02.17.
 */

public class DaggerBuilder {
	private static ApplicationComponent applicationBuilder;
	private static NewsTapeComponent newsTapeBuilder;
	private static Context appContext;

	private static ApplicationComponent createApplicationBuilder() {
		return DaggerApplicationComponent.builder()
				.applicationModule(new ApplicationModule(appContext))
				.build();
	}

	public static ApplicationComponent getApplicationBuilder() {
		if (applicationBuilder == null)
			applicationBuilder = createApplicationBuilder();
		return applicationBuilder;
	}

	public static NewsTapeComponent createNewsTapeBuilder() {
		return getApplicationBuilder().addToGraph(new NewsTapeModule());
	}


	public static SourcesBrowserComponent createSourcesBrowserBuilder() {
		return getApplicationBuilder().addToGraph(new SourcesBrowserModule());
	}


	public static void inintialize(Context applicationContext) {
		appContext = applicationContext;
	}
}
