package ru.nlp_project.story_line.client_android.dagger;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by fedor on 11.01.17.
 */

@Singleton
@Component(modules = ApplicationModule.class)
public abstract class ApplicationComponent {
	abstract public NewsTapeComponent addToGraph(NewsTapeModule module);
}
