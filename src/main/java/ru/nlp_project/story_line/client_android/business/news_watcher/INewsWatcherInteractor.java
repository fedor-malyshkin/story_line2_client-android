package ru.nlp_project.story_line.client_android.business.news_watcher;

import io.reactivex.Single;
import ru.nlp_project.story_line.client_android.business.models.NewsArticleBusinessModel;

public interface INewsWatcherInteractor {
	Single<NewsArticleBusinessModel> createCachedNewsArticleStream(String newsArticleServerId);
}
