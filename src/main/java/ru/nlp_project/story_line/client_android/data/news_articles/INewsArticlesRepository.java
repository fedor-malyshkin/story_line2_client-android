package ru.nlp_project.story_line.client_android.data.news_articles;

import io.reactivex.Single;
import ru.nlp_project.story_line.client_android.business.models.NewsArticleBusinessModel;
import ru.nlp_project.story_line.client_android.data.IRepository;

public interface INewsArticlesRepository extends IRepository {

	Single<NewsArticleBusinessModel> createNewsArticleRemoteCachedStream(String articleServerId);
}
