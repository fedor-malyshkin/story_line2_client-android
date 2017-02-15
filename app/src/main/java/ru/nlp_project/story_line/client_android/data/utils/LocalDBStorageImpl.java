package ru.nlp_project.story_line.client_android.data.utils;

import android.content.Context;
import io.reactivex.Maybe;
import io.reactivex.Observable;
import javax.inject.Inject;
import ru.nlp_project.story_line.client_android.data.models.CategoryDataModel;
import ru.nlp_project.story_line.client_android.data.models.NewsArticleDataModel;
import ru.nlp_project.story_line.client_android.data.models.NewsHeaderDataModel;
import ru.nlp_project.story_line.client_android.data.models.SourceDataModel;

public class LocalDBStorageImpl implements ILocalDBStorage {

	private final Context context;

	@Inject
	public LocalDBStorageImpl(Context context) {
		this.context = context;
	}

	@Override
	public void initialize() {
	}

	@Override
	public void addCategoryToCache(CategoryDataModel dataModel) {

	}

	@Override
	public void commitCategoryCacheUpdate() {

	}

	@Override
	public void cancelCategoryCacheUpdate(Throwable throwable) {
		// Log.w("cancelCategoryCacheUpdate", throwable.getMessage(), throwable);
	}

	@Override
	public Observable<CategoryDataModel> createCategoryStream() {
		return Observable.fromArray(new CategoryDataModel(0L, "cache", "server-1"));
	}

	@Override
	public void addSourceToCache(SourceDataModel sourceDataModel) {

	}

	@Override
	public void cancelSourceCacheUpdate(Throwable throwable) {

	}

	@Override
	public void commitSourceCacheUpdate() {

	}

	@Override
	public Observable<SourceDataModel> createSourceStream() {
		return Observable.fromArray(new SourceDataModel(0L, "bnkomi.ru", "BNK", "BNK Long",
			"bnk_server_id"), new SourceDataModel(0L, "komiinform.ru", "KomiInform", "KomiInform "
			+ "Long",
			"komiinform_server_id"));
	}

	@Override
	public Maybe<NewsArticleDataModel> createNewsArticleStream(String serverId) {
		return null;
	}

	@Override
	public void addNewsArticleToCache(NewsArticleDataModel newsArticleDataModel) {

	}

	@Override
	public void cancelNewsArticleCacheUpdate(Throwable throwable) {

	}

	@Override
	public void commitNewsArticleCacheUpdate() {

	}

	@Override
	public void addNewsHeaderToCache(NewsHeaderDataModel newsHeaderDataModel) {

	}

	@Override
	public void cancelNewsHeaderCacheUpdate(Throwable throwable) {

	}

	@Override
	public void commitNewsHeaderCacheUpdate() {

	}

	@Override
	public Observable<NewsHeaderDataModel> createNewsHeaderStream(String sourceDomain) {
		return Observable.fromArray(new NewsHeaderDataModel(0L, "News-1",
			"bnk_server_id"), new NewsHeaderDataModel(0L, "news-2",
			"komiinform_server_id"));
	}
}
