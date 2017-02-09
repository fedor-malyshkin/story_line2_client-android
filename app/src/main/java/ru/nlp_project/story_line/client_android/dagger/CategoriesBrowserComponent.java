package ru.nlp_project.story_line.client_android.dagger;

import dagger.Subcomponent;
import ru.nlp_project.story_line.client_android.ui.categories_browser.CategoriesBrowserFragment;
import ru.nlp_project.story_line.client_android.ui.sources_browser.SourcesBrowserActivity;

/**
 * Created by fedor on 05.02.17.
 */

@Subcomponent(modules = CategoriesBrowserModule.class)
@CategoriesBrowserScope
public abstract class CategoriesBrowserComponent {
	public abstract void inject(CategoriesBrowserFragment fragment);
}
