package ru.nlp_project.story_line.client_android.dagger;

import dagger.Component;
import javax.inject.Singleton;
import ru.nlp_project.story_line.client_android.ui.StartupPresenter;

@Singleton
@Component(modules = ApplicationModule.class)
public abstract class ApplicationComponent {

	// don't forget to add in app init code new modules
	abstract public NewsBrowserComponent addToGraph(NewsBrowserModule module);

	abstract public NewsTapeComponent addToGraph(NewsTapeModule module);

	abstract public SourcesBrowserComponent addToGraph(SourcesBrowserModule module);

	public abstract NewsWatcherComponent addToGraph(NewsWatcherModule module);

	public abstract PreferencesComponent addToGraph(PreferencesModule module);

	public abstract void inject(StartupPresenter instance);
}
