package ru.nlp_project.story_line.client_android.business.news_articles;

import io.reactivex.Single;
import ru.nlp_project.story_line.client_android.business.IInteractor;
import ru.nlp_project.story_line.client_android.business.models.NewsArticleBusinessModel;

public interface INewsArticlesInteractor extends IInteractor {

	Single<NewsArticleBusinessModel> createNewsArticleRemoteCachedStream(String newsArticleServerId);

	String getSourceTitleShortCached(String source);
}
