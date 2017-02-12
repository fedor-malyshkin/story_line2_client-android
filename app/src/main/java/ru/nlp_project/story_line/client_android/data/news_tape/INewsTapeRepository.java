package ru.nlp_project.story_line.client_android.data.news_tape;

import io.reactivex.Observable;
import ru.nlp_project.story_line.client_android.data.models.NewsHeaderDataModel;

/**
 * Created by fedor on 05.02.17.
 */

public interface INewsTapeRepository {
	Observable<NewsHeaderDataModel> createNewsArticleStream();
	Observable<NewsHeaderDataModel> createAdditionNewsArticleStream(Long lastNewsId);
}
