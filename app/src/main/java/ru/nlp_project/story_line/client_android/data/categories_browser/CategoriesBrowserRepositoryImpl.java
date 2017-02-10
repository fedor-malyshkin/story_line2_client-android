package ru.nlp_project.story_line.client_android.data.categories_browser;

import io.reactivex.Observable;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by fedor on 09.02.17.
 */

@Singleton
public class CategoriesBrowserRepositoryImpl implements ICategoriesBrowserRepository  {


	@Inject
	public CategoriesBrowserRepositoryImpl() {
	}

	@Override
	public Observable<CategoryDateModel> createCategoryStream() {
		return null;
	}
}
