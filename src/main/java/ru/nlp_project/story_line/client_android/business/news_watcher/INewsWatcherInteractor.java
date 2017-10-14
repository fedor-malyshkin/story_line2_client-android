package ru.nlp_project.story_line.client_android.business.news_watcher;

import io.reactivex.Single;
import ru.nlp_project.story_line.client_android.business.IInteractor;
import ru.nlp_project.story_line.client_android.business.models.NewsArticleBusinessModel;
import ru.nlp_project.story_line.client_android.ui.news_watcher.INewsWatcherPresenter;

public interface INewsWatcherInteractor extends IInteractor<INewsWatcherPresenter> {
	Single<NewsArticleBusinessModel> createNewsArticleRemoteCachedStream(String newsArticleServerId);
}
