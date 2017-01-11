package ru.nlp_project.story_line.client_android.builder;

import javax.inject.Singleton;

import dagger.Component;
import ru.nlp_project.story_line.client_android.EventProcessingService;

/**
 * Created by fedor on 11.01.17.
 */

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {
	void inject(EventProcessingService service);
}
