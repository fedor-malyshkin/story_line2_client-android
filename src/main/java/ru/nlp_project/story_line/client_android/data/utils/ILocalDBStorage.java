package ru.nlp_project.story_line.client_android.data.utils;

import io.reactivex.Maybe;
import io.reactivex.Observable;
import java.util.List;
import ru.nlp_project.story_line.client_android.data.models.ChangeRecordDataModel;
import ru.nlp_project.story_line.client_android.data.models.NewsArticleDataModel;
import ru.nlp_project.story_line.client_android.data.models.NewsHeaderDataModel;
import ru.nlp_project.story_line.client_android.data.models.SourceDataModel;

public interface ILocalDBStorage {

	void initializeDBStorage();

	void addSource(SourceDataModel sourceDataModel);

	Observable<SourceDataModel> createSourceStream();

	Maybe<NewsArticleDataModel> createNewsArticleStream(String articleServerId);

	void addNewsArticle(NewsArticleDataModel newsArticleDataModel);

	void addNewsHeader(NewsHeaderDataModel newsHeaderDataModel);

	Observable<NewsHeaderDataModel> createNewsHeaderStream(String sourceDomain);


	/**
	 * Add/Update
	 */
	void upsetSources(List<SourceDataModel> list);

	void updateSourceState(String sourceName, boolean checked);

	long getSourcesCount(boolean active, boolean notActive);

	void setChangeRecordsAsSeen(List<Long> ids);

	void addChangeRecord(ChangeRecordDataModel dataModel);

	boolean hasUnseenChangeRecords();

	List<ChangeRecordDataModel> getUnseenChangeRecords();
}
