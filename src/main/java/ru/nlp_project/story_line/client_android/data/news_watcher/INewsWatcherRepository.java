package ru.nlp_project.story_line.client_android.data.news_watcher;

import io.reactivex.Single;
import ru.nlp_project.story_line.client_android.data.models.NewsArticleDataModel;

public interface INewsWatcherRepository {

	Single<NewsArticleDataModel> createNewsArticleRemoteCachedStream(String articleServerId);
}
