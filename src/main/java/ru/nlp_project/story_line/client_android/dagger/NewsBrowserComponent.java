package ru.nlp_project.story_line.client_android.dagger;

import dagger.Subcomponent;
import ru.nlp_project.story_line.client_android.ui.news_browser.NewsBrowserActivity;

@Subcomponent(modules = NewsBrowserModule.class)
@NewsBrowserScope
public abstract class NewsBrowserComponent {

	public abstract void inject(NewsBrowserActivity activity);
}
