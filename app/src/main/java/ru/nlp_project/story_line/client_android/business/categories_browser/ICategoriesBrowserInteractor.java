package ru.nlp_project.story_line.client_android.business.categories_browser;

import io.reactivex.Observable;
import ru.nlp_project.story_line.client_android.dagger.CategoriesBrowserScope;

@CategoriesBrowserScope
public interface ICategoriesBrowserInteractor {

	Observable<CategoryBusinessModel> createCategoryStream();
}
