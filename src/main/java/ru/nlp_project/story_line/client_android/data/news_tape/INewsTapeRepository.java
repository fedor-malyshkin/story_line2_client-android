package ru.nlp_project.story_line.client_android.data.news_tape;

import io.reactivex.Observable;
import ru.nlp_project.story_line.client_android.data.models.NewsHeaderDataModel;


public interface INewsTapeRepository {

	Observable<NewsHeaderDataModel> createCachedNewsHeaderStream(String sourceDomain);

	Observable<NewsHeaderDataModel> createAdditionNewsHeaderStream(String sourceDomain,
		Long lastNewsId);
}
