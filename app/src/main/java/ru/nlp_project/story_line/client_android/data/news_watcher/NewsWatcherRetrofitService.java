package ru.nlp_project.story_line.client_android.data.news_watcher;

import io.reactivex.Maybe;
import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;
import ru.nlp_project.story_line.client_android.data.models.NewsArticleDataModel;

public interface NewsWatcherRetrofitService {

	@GET("news_articles/{id}")
	Maybe<NewsArticleDataModel> get(@Path("id") String serverId);

}
