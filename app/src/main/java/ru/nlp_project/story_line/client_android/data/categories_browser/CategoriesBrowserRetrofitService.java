package ru.nlp_project.story_line.client_android.data.categories_browser;

import io.reactivex.Observable;
import java.util.List;
import retrofit2.http.GET;
import ru.nlp_project.story_line.client_android.data.models.CategoryDataModel;

public interface CategoriesBrowserRetrofitService {

	@GET("categories")
	Observable<List<CategoryDataModel>> list();

}
