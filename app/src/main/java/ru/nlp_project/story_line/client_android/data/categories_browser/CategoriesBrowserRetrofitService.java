package ru.nlp_project.story_line.client_android.data.categories_browser;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface CategoriesBrowserRetrofitService {

	@GET("categories")
	Observable<CategoryDateModel> list();

}
