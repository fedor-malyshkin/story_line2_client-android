package ru.nlp_project.story_line.client_android.dagger;

import dagger.Module;
import dagger.Provides;
import ru.nlp_project.story_line.client_android.business.categories_browser.CategoriesBrowserInteractorImpl;
import ru.nlp_project.story_line.client_android.business.categories_browser.ICategoriesBrowserInteractor;
import ru.nlp_project.story_line.client_android.ui.categories_browser.CategoriesBrowserPresenterImpl;
import ru.nlp_project.story_line.client_android.ui.categories_browser.ICategoriesBrowserPresenter;

@Module
public class CategoriesBrowserModule {

	@Provides
	@CategoriesBrowserScope
	public ICategoriesBrowserPresenter provideCategoriesBrowserPresenter(
		CategoriesBrowserPresenterImpl implementation) {
		return implementation;
	}

	@Provides
	@CategoriesBrowserScope
	public ICategoriesBrowserInteractor provideCategoriesBrowserInteractor
		(CategoriesBrowserInteractorImpl implementation) {
		return implementation;
	}

}
