package ru.nlp_project.story_line.client_android.ui.categories_browser;


import ru.nlp_project.story_line.client_android.ui.IPresenter;

public interface ICategoriesBrowserPresenter extends IPresenter<ICategoriesBrowserView> {
	void loadCategories();
}
