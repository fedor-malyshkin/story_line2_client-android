package ru.nlp_project.story_line.client_android.data.news_tape;

import io.reactivex.Observable;
import javax.inject.Inject;
import javax.inject.Singleton;
import ru.nlp_project.story_line.client_android.data.models.NewsHeaderDataModel;

/**
 * Created by fedor on 05.02.17.
 */
@Singleton
public class NewsTapeRepositoryImpl implements INewsTapeRepository {

	@Inject
	public NewsTapeRepositoryImpl() {
	}

	// TODO: implements with DB as cache
	@Override
	public Observable<NewsHeaderDataModel> createNewsArticleStream(String sourceServerId) {
		return null;
	}

	// TODO: implements with DB as cache
	@Override
	public Observable<NewsHeaderDataModel> createAdditionNewsArticleStream(String sourceServerId,
		Long lastNewsId) {
		return null;
	}

}
