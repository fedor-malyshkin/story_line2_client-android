package ru.nlp_project.story_line.client_android.data.news_watcher;

import io.reactivex.Maybe;
import retrofit2.http.GET;
import retrofit2.http.Query;
import ru.nlp_project.story_line.client_android.data.models.NewsArticleDataModel;

public interface NewsWatcherRetrofitService {

	/**
	 * Запросить статью по идентификатору.
	 */
	@GET("news_articles/{article_id}")
	Maybe<NewsArticleDataModel> getNewsArticleById(
		@Query("article_id") String articleServerId);


}
