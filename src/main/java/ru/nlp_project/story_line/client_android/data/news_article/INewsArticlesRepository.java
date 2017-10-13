package ru.nlp_project.story_line.client_android.data.news_article;

import io.reactivex.Single;
import ru.nlp_project.story_line.client_android.business.models.NewsArticleBusinessModel;

public interface INewsArticlesRepository {

	Single<NewsArticleBusinessModel> createNewsArticleRemoteCachedStream(String articleServerId);
}
