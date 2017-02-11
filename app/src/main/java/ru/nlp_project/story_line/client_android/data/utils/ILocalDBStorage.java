package ru.nlp_project.story_line.client_android.data.utils;

import io.reactivex.Observable;
import ru.nlp_project.story_line.client_android.data.categories_browser.CategoryDataModel;

public interface ILocalDBStorage {

	void addCategoryToCache(CategoryDataModel dateModel);

	void commitCategoryCacheUpdate();

	void initialize();

	void cancelCategoryCacheUpdate(Throwable throwable);

	Observable<CategoryDataModel> createCategoryStream();
}
