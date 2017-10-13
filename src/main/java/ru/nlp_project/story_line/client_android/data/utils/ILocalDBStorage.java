package ru.nlp_project.story_line.client_android.data.utils;

import io.reactivex.Maybe;
import io.reactivex.Observable;
import java.util.List;
import ru.nlp_project.story_line.client_android.data.models.NewsArticleDataModel;
import ru.nlp_project.story_line.client_android.data.models.NewsHeaderDataModel;
import ru.nlp_project.story_line.client_android.data.models.SourceDataModel;

public interface ILocalDBStorage {

	void initialize();

	void addSourceToCache(SourceDataModel sourceDataModel);

	Observable<SourceDataModel> createSourceStream();

	Maybe<NewsArticleDataModel> createNewsArticleStream(String articleServerId);

	void addNewsArticleToCache(NewsArticleDataModel newsArticleDataModel);

	void addNewsHeaderToCache(NewsHeaderDataModel newsHeaderDataModel);

	Observable<NewsHeaderDataModel> createNewsHeaderStream(String sourceDomain);


	/**
	 * Add/Update
	 */
	void upsetSources(List<SourceDataModel> list);
}
