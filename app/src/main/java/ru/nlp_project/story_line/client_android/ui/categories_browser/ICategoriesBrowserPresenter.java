package ru.nlp_project.story_line.client_android.ui.categories_browser;


import android.view.View;
import ru.nlp_project.story_line.client_android.ui.IPresenter;
import ru.nlp_project.story_line.client_android.ui.categories_browser.ICategoriesBrowserView.ICategorySelectionListener;

public interface ICategoriesBrowserPresenter extends IPresenter<ICategoriesBrowserView> {

	void loadCategories();

	void onCategorySelection(View view);

	void setCategorySelectionListener(ICategorySelectionListener activity);
}
