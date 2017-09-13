package ru.nlp_project.story_line.client_android.data.categories_browser;

import io.reactivex.Observable;
import ru.nlp_project.story_line.client_android.data.models.CategoryDataModel;

public interface ICategoriesBrowserRepository {

	Observable<CategoryDataModel> createCategoryRemoteCachedStream();
}
