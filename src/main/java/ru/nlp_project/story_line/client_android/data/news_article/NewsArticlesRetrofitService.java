package ru.nlp_project.story_line.client_android.data.news_article;

import io.reactivex.Maybe;
import retrofit2.http.GET;
import retrofit2.http.Path;
import ru.nlp_project.story_line.client_android.data.models.NewsArticleDataModel;

public interface NewsArticlesRetrofitService {

	/**
	 * Запросить статью по идентификатору.
	 */
	@GET("news_articles/{article_id}")
	Maybe<NewsArticleDataModel> getNewsArticleById(
		@Path("article_id") String articleServerId);


}
