package ru.nlp_project.story_line.client_android.data.utils;

import static nl.qbusict.cupboard.CupboardFactory.cupboard;

import android.database.sqlite.SQLiteDatabase;
import io.reactivex.Maybe;
import io.reactivex.Observable;
import java.util.List;
import javax.inject.Inject;
import nl.qbusict.cupboard.DatabaseCompartment;
import ru.nlp_project.story_line.client_android.data.models.CategoryDataModel;
import ru.nlp_project.story_line.client_android.data.models.NewsArticleDataModel;
import ru.nlp_project.story_line.client_android.data.models.NewsHeaderDataModel;
import ru.nlp_project.story_line.client_android.data.models.SourceDataModel;

public class LocalDBStorageImpl implements ILocalDBStorage {


	@Inject
	DatabaseHelper databaseHelper;


	@Inject
	public LocalDBStorageImpl() {
	}

	@Override
	public void initialize() {
		SQLiteDatabase wdb = databaseHelper.getWritableDatabase();
		wdb.execSQL("CREATE INDEX IF NOT EXISTS NewsHeaderDataModel.source ON "
			+ "NewsHeaderDataModel (source);");
		wdb.execSQL("CREATE INDEX IF NOT EXISTS NewsHeaderDataModel.serverId ON "
			+ "NewsHeaderDataModel (serverId);");
		wdb.execSQL("CREATE INDEX IF NOT EXISTS NewsArticleDataModel.serverId ON "
			+ "NewsArticleDataModel (serverId);");
		// wdb.close();
	}


	@Override
	public void addCategoryToCache(long requestId, CategoryDataModel dataModel) {
		dataModel.setRequestId(requestId);
		SQLiteDatabase wdb = databaseHelper.getWritableDatabase();
		// delete all categories
		// cupboard().withDatabase(wdb).delete(CategoryDataModel.class, "", "");
		cupboard().withDatabase(wdb).put(dataModel);
		// wdb.close();
	}

	@Override
	public Observable<CategoryDataModel> createCategoryStream() {

		return Observable.fromCallable(
			() -> {
				SQLiteDatabase rdb = databaseHelper.getReadableDatabase();
				List<CategoryDataModel> list = cupboard().withDatabase(rdb)
					.query(CategoryDataModel.class)
					.list();
				// rdb.close();
				return list;
			}
		).flatMap(Observable::fromIterable);
	}


	@Override
	public void addSourceToCache(long requestId, SourceDataModel dataModel) {
		dataModel.setRequestId(requestId);
		SQLiteDatabase wdb = databaseHelper.getWritableDatabase();
		// delete all sources
		// cupboard().withDatabase(wdb).delete(SourceDataModel.class, "", "");
		cupboard().withDatabase(wdb).put(dataModel);
		// wdb.close();
	}


	@Override
	public Observable<SourceDataModel> createSourceStream() {
		return Observable.fromCallable(
			() -> {
				SQLiteDatabase rdb = databaseHelper.getReadableDatabase();
				List<SourceDataModel> list = cupboard().withDatabase(rdb)
					.query(SourceDataModel.class)
					.list();
				// rdb.close();
				return list;
			}
		).flatMap(Observable::fromIterable);
	}

	@Override
	@SuppressWarnings("unchecked")
	public Maybe<NewsArticleDataModel> createNewsArticleStream(String serverId) {

		// considering null value from the callable as indication for valueless completion.
		return Maybe.fromCallable(
			() -> {
				SQLiteDatabase rdb = databaseHelper.getReadableDatabase();
				NewsArticleDataModel model = cupboard().withDatabase(rdb)
					.query(NewsArticleDataModel.class).withSelection
						("serverId = ?", serverId).get();
				// rdb.close();
				return model;
			}
		);
	}


	@Override
	public void addNewsArticleToCache(NewsArticleDataModel dataModel) {
		SQLiteDatabase wdb = databaseHelper.getWritableDatabase();
		DatabaseCompartment withDatabase = cupboard().withDatabase(wdb);
		NewsArticleDataModel existing = withDatabase.query(NewsArticleDataModel.class)
			.withSelection("serverId = ?",
				dataModel.getServerId()).get();
		if (existing == null) {
			withDatabase.put(dataModel);
		}
		// wdb.close();
	}


	@Override
	public void addNewsHeaderToCache(NewsHeaderDataModel dataModel) {
		SQLiteDatabase wdb = databaseHelper.getWritableDatabase();
		DatabaseCompartment withDatabase = cupboard().withDatabase(wdb);
		NewsHeaderDataModel existing = withDatabase.query(NewsHeaderDataModel.class)
			.withSelection("serverId = ?",
				dataModel.getServerId()).get();
		if (existing == null) {
			withDatabase.put(dataModel);
		}
		// wdb.close();
	}


	@Override
	public Observable<NewsHeaderDataModel> createNewsHeaderStream(String sourceDomain) {
		return Observable.fromCallable(
			() -> {
				SQLiteDatabase rdb = databaseHelper.getReadableDatabase();
				List<NewsHeaderDataModel> list = cupboard().withDatabase(rdb)
					.query(NewsHeaderDataModel.class)
					.withSelection("source = ?", sourceDomain)
					.list();
				// rdb.close();
				return list;
			}
		).flatMap(Observable::fromIterable);
	}

	/**
	 * Отменить и удалить все записи с текущим идентификатором requestId (неудачным).
	 */
	@Override
	public void cancelSourceCacheUpdate(long requestId) {
		SQLiteDatabase wdb = databaseHelper.getWritableDatabase();
		DatabaseCompartment withDatabase = cupboard().withDatabase(wdb);
		withDatabase.delete(SourceDataModel.class, "requestId = ?", String.valueOf(requestId));

	}

	/**
	 * После успешного обновления - удалить все записи с идентификатором запроса (requestId), не
	 * совпадающим с текущим.
	 */
	@Override
	public void commitSourceCacheUpdate(long requestId) {
		SQLiteDatabase wdb = databaseHelper.getWritableDatabase();
		DatabaseCompartment withDatabase = cupboard().withDatabase(wdb);
		withDatabase.delete(SourceDataModel.class, "requestId != ?", String.valueOf(requestId));

	}

	/**
	 * Отменить и удалить все записи с текущим идентификатором requestId (неудачным).
	 */
	@Override
	public void cancelCategoryCacheUpdate(long requestId) {
		SQLiteDatabase wdb = databaseHelper.getWritableDatabase();
		DatabaseCompartment withDatabase = cupboard().withDatabase(wdb);
		withDatabase.delete(CategoryDataModel.class, "requestId = ?", String.valueOf(requestId));
	}

	/**
	 * После успешного обновления - удалить все записи с идентификатором запроса (requestId), не
	 * совпадающим с текущим.
	 */
	@Override
	public void commitCategoryCacheUpdate(long requestId) {
		SQLiteDatabase wdb = databaseHelper.getWritableDatabase();
		DatabaseCompartment withDatabase = cupboard().withDatabase(wdb);
		withDatabase.delete(CategoryDataModel.class, "requestId != ?", String.valueOf(requestId));
	}
}
