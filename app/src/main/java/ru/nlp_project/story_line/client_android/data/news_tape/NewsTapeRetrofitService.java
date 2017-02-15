package ru.nlp_project.story_line.client_android.data.news_tape;

import io.reactivex.Maybe;
import io.reactivex.Observable;
import java.util.List;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import ru.nlp_project.story_line.client_android.data.models.NewsArticleDataModel;
import ru.nlp_project.story_line.client_android.data.models.NewsHeaderDataModel;

public interface NewsTapeRetrofitService {

	/**
	 * Получить список заголовков
	 *
	 * @param sourceDomain домен источника
	 */
	@GET("news_articles/{source_domain}?headers=true")
	Observable<List<NewsHeaderDataModel>> listHeaders(@Path("source_domain") String sourceDomain,
		@Query("count")
			int count);


}
