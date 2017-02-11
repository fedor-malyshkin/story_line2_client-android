package ru.nlp_project.story_line.client_android.data.utils;

import android.content.Context;
import android.util.Log;
import io.reactivex.Observable;
import javax.inject.Inject;
import ru.nlp_project.story_line.client_android.data.categories_browser.CategoryDataModel;

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
		Log.i("addCategoryToCache", dataModel.toString());
	}

	@Override
	public void commitCategoryCacheUpdate() {
		Log.i("commitCategoryCacheUpdate", "");
	}

	@Override
	public void cancelCategoryCacheUpdate(Throwable throwable) {
		Log.w("cancelCategoryCacheUpdate", throwable.getMessage(), throwable);
	}

	@Override
	public Observable<CategoryDataModel> createCategoryStream() {
		return Observable.fromArray(new CategoryDataModel(0L, "cache", "server-1"));
	}
}
