package ru.nlp_project.story_line.client_android.dagger;

import dagger.Component;
import javax.inject.Singleton;

@Singleton
@Component(modules = ApplicationModule.class)
public abstract class ApplicationComponent {

	// don't forget to add in app init code new modules
	abstract public NewsBrowserComponent addToGraph(NewsBrowserModule module);

	abstract public NewsTapeComponent addToGraph(NewsTapeModule module);

	abstract public SourcesBrowserComponent addToGraph(SourcesBrowserModule module);

	abstract public CategoriesBrowserComponent addToGraph(CategoriesBrowserModule module);

	public abstract NewsWatcherComponent addToGraph(NewsWatcherModule module);
}
