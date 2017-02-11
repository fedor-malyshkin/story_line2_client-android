package ru.nlp_project.story_line.client_android.data.categories_browser;

import io.reactivex.Observable;

public interface ICategoriesBrowserRepository {

	Observable<CategoryDataModel> createCategoryStream();
}
