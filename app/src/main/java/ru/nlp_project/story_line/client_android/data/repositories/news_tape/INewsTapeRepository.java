package ru.nlp_project.story_line.client_android.data.repositories.news_tape;

import io.reactivex.Observable;

/**
 * Created by fedor on 05.02.17.
 */

public interface INewsTapeRepository {
	Observable<NewsArticleDataModel> createNewsArticleStream();
	Observable<NewsArticleDataModel> createAdditionNewsArticleStream(Long lastNewsId);
}
