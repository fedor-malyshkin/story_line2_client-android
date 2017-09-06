package ru.nlp_project.story_line.client_android.data.utils;

import io.reactivex.Maybe;
import io.reactivex.Observable;
import java.util.List;
import ru.nlp_project.story_line.client_android.data.models.CategoryDataModel;
import ru.nlp_project.story_line.client_android.data.models.NewsArticleDataModel;
import ru.nlp_project.story_line.client_android.data.models.NewsHeaderDataModel;
import ru.nlp_project.story_line.client_android.data.models.SourceDataModel;

public interface ILocalDBStorage {

	void addCategoryToCache(long requestId, CategoryDataModel dateModel);


	void initialize();


	Observable<CategoryDataModel> createCategoryStream();

	void addSourceToCache(long requestId, SourceDataModel sourceDataModel);


	Observable<SourceDataModel> createSourceStream();

	Maybe<NewsArticleDataModel> createNewsArticleStream(String articleServerId);

	void addNewsArticleToCache(NewsArticleDataModel newsArticleDataModel);


	void addNewsHeaderToCache(NewsHeaderDataModel newsHeaderDataModel);

	Observable<NewsHeaderDataModel> createNewsHeaderStream(String sourceDomain);


	void cancelSourceCacheUpdate(long requestId);

	void commitSourceCacheUpdate(long requestId);

	void cancelCategoryCacheUpdate(long requestId);

	void commitCategoryCacheUpdate(long requestId);

	/**
	 * Add/Update
	 * @param list
	 */
	void upsetSources(List<SourceDataModel> list);
}
