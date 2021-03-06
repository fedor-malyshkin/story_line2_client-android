package ru.nlp_project.story_line.client_android.dagger;

import android.content.Context;

public class DaggerBuilder {

	private static ApplicationComponent applicationBuilder;
	private static NewsTapeComponent newsTapeBuilder;
	private static Context appContext;

	private static ApplicationComponent createApplicationBuilder() {
		return DaggerApplicationComponent.builder()
				.applicationModule(new ApplicationModule(appContext))
				.build();
	}

	public synchronized static ApplicationComponent getApplicationBuilder() {
		if (applicationBuilder == null) {
			applicationBuilder = createApplicationBuilder();
		}
		return applicationBuilder;
	}

	public static NewsTapeComponent createNewsTapeBuilder() {
		return getApplicationBuilder().addToGraph(new NewsTapeModule());
	}

	public static SourcesBrowserComponent createSourcesBrowserBuilder() {
		return getApplicationBuilder().addToGraph(new SourcesBrowserModule());
	}

	public static void initialize(Context applicationContext) {
		appContext = applicationContext;
	}

	public static NewsBrowserComponent createNewsBrowserBuilder() {
		return getApplicationBuilder().addToGraph(new NewsBrowserModule());
	}

	public static NewsWatcherComponent createNewsWatcherBuilder() {
		return getApplicationBuilder().addToGraph(new NewsWatcherModule());
	}

	public static PreferencesComponent createPreferencesBuilder() {
		return getApplicationBuilder().addToGraph(new PreferencesModule());
	}

	public static FeedbackComponent createFeedbackBuilder() {
		return getApplicationBuilder().addToGraph(new FeedbackModule());
	}

	public static ChangesComponent createChangesBuilder() {
		return getApplicationBuilder().addToGraph(new ChangesModule());
	}
}
