package ru.nlp_project.story_line.client_android.dagger;

import dagger.Subcomponent;
import ru.nlp_project.story_line.client_android.ui.sources_browser.SourcesBrowserActivity;

/**
 * Created by fedor on 05.02.17.
 */

@Subcomponent(modules = SourcesBrowserModule.class)
@SourcesBrowserScope
public abstract class SourcesBrowserComponent {

	public abstract void inject(SourcesBrowserActivity fragment);
}
