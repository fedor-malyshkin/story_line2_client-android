package ru.nlp_project.story_line.client_android.data.news_headers;

import io.reactivex.Observable;
import java.util.List;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import ru.nlp_project.story_line.client_android.data.models.NewsHeaderDataModel;

public interface NewsHeadersRetrofitService {

	/**
	 * Получить список заголовков
	 *
	 * @param sourceDomain домен источника
	 */
	@GET("news_headers/{source_domain}")
	Observable<List<NewsHeaderDataModel>> listHeaders(@Path("source_domain") String sourceDomain,
			@Query("count") int count, @Query("last_news_id") String lastNewsId);


}
