package ru.nlp_project.story_line.client_android.dagger;

import dagger.Component;
import dagger.Subcomponent;
import ru.nlp_project.story_line.client_android.ui.news_tape.NewsTapeFragment;

@Subcomponent(modules = NewsTapeModule.class)
@NewsTapeScope
public abstract class NewsTapeComponent {
	public abstract void inject(NewsTapeFragment fragment);
}
