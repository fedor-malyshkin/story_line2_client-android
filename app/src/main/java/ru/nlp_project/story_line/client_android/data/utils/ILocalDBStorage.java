package ru.nlp_project.story_line.client_android.data.utils;

import io.reactivex.Maybe;
import io.reactivex.Observable;
import ru.nlp_project.story_line.client_android.data.models.CategoryDataModel;
import ru.nlp_project.story_line.client_android.data.models.NewsArticleDataModel;
import ru.nlp_project.story_line.client_android.data.models.SourceDataModel;

public interface ILocalDBStorage {

	void addCategoryToCache(CategoryDataModel dateModel);

	void commitCategoryCacheUpdate();

	void initialize();

	void cancelCategoryCacheUpdate(Throwable throwable);

	Observable<CategoryDataModel> createCategoryStream();

	void addSourceToCache(SourceDataModel sourceDataModel);

	void cancelSourceCacheUpdate(Throwable throwable);

	void commitSourceCacheUpdate();

	Observable<SourceDataModel> createSourceStream();

	Maybe<NewsArticleDataModel> createNewsArticleStream(String serverId);

	void addNewsArticleToCache(NewsArticleDataModel newsArticleDataModel);

	void cancelNewsArticleCacheUpdate(Throwable throwable);

	void commitNewsArticleCacheUpdate();
}
