package ru.nlp_project.story_line.client_android.dagger;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by fedor on 11.01.17.
 */

@Singleton
@Component(modules = ApplicationModule.class)
public abstract class ApplicationComponent {
	// don't forget to add in app init code new modules
	abstract public NewsTapeComponent addToGraph(NewsTapeModule module);
	abstract public SourcesBrowserComponent addToGraph(SourcesBrowserModule module);
}
