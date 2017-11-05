package ru.nlp_project.story_line.client_android.data.utils;

import static nl.qbusict.cupboard.CupboardFactory.cupboard;

import android.content.ContentValues;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import io.reactivex.Maybe;
import io.reactivex.Observable;
import java.util.Date;
import java.util.List;
import javax.inject.Inject;
import nl.qbusict.cupboard.DatabaseCompartment;
import ru.nlp_project.story_line.client_android.data.models.ChangeRecordDataModel;
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
	public void initializeDBStorage() {
		SQLiteDatabase wdb = databaseHelper.getWritableDatabase();
		wdb.execSQL(String.format("CREATE INDEX IF NOT EXISTS %1$s_%2$s ON "
				+ "%1$s (%2$s);", cupboard().getTable(NewsHeaderDataModel.class), "source"));
		wdb.execSQL(String.format("CREATE INDEX IF NOT EXISTS %1$s_%2$s ON "
				+ "%1$s (%2$s);", cupboard().getTable(NewsHeaderDataModel.class), "serverId"));
		wdb.execSQL(String.format("CREATE INDEX IF NOT EXISTS %1$s_%2$s ON "
				+ "%1$s (%2$s);", cupboard().getTable(NewsArticleDataModel.class), "serverId"));
	}

	@Override
	public void addSource(SourceDataModel dataModel) {
		SQLiteDatabase wdb = databaseHelper.getWritableDatabase();
		DatabaseCompartment withDatabase = cupboard().withDatabase(wdb);
		SourceDataModel existing = withDatabase.query(SourceDataModel.class)
				.withSelection("name = ?",
						dataModel.getName()).get();
		if (existing != null) {
			existing.updatePresentationData(dataModel);
			withDatabase.put(existing);
		} else {
			// set new date for new record
			dataModel.setAdditionDate(new Date());
			withDatabase.put(dataModel);
		}
	}


	@Override
	public Observable<SourceDataModel> createSourceStream() {
		return Observable.fromCallable(
				() -> {
					SQLiteDatabase rdb = databaseHelper.getReadableDatabase();
					// rdb.close();
					return cupboard().withDatabase(rdb)
							.query(SourceDataModel.class)
							.list();
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
					// rdb.close();
					return cupboard().withDatabase(rdb)
							.query(NewsArticleDataModel.class).withSelection
									("serverId = ?", serverId).get();
				}
		);
	}


	@Override
	public void addNewsArticle(NewsArticleDataModel dataModel) {
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
	public void addNewsHeader(NewsHeaderDataModel dataModel) {
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
					// rdb.close();
					return cupboard().withDatabase(rdb)
							.query(NewsHeaderDataModel.class)
							.withSelection("source = ?", sourceDomain)
							.list();
				}
		).flatMap(Observable::fromIterable);
	}


	@Override
	public void upsetSources(List<SourceDataModel> list) {
		SQLiteDatabase wdb = databaseHelper.getWritableDatabase();
		DatabaseCompartment withDatabase = cupboard().withDatabase(wdb);
		withDatabase.delete(SourceDataModel.class, "_id > 0");
		withDatabase.put(list);
	}

	@Override
	public void updateSourceState(String sourceName, boolean checked) {
		SQLiteDatabase wdb = databaseHelper.getWritableDatabase();
		DatabaseCompartment withDatabase = cupboard().withDatabase(wdb);
		SourceDataModel existing = withDatabase.query(SourceDataModel.class)
				.withSelection("name = ?",
						sourceName).get();
		if (existing != null) {
			existing.setEnabled(checked);
			withDatabase.put(existing);
		}
	}

	@Override
	public long getSourcesCount(boolean active, boolean notActive) {
		SQLiteDatabase rdb = databaseHelper.getReadableDatabase();
		/*
		SQLite does not have a separate Boolean storage class. Instead, Boolean values are stored as integers 0 (false) and 1 (true).
		http://www.sqlite.org/datatype3.html
		 */
		String selector = "enabled = 1";
		long res = DatabaseUtils
				.queryNumEntries(rdb, cupboard().getTable(SourceDataModel.class), selector);
		return res;
	}


	@Override
	public void setChangeRecordsAsSeen(List<Long> ids) {
		SQLiteDatabase rdb = databaseHelper.getReadableDatabase();
		DatabaseCompartment withDatabase = cupboard().withDatabase(rdb);
		ContentValues val = new ContentValues();
		val.put("seen", 1);
		withDatabase.update(ChangeRecordDataModel.class, val, "id IN {?}", ids.toString());

	}

	@Override
	public void addChangeRecord(ChangeRecordDataModel dataModel) {
		SQLiteDatabase wdb = databaseHelper.getWritableDatabase();
		DatabaseCompartment withDatabase = cupboard().withDatabase(wdb);
		withDatabase.put(dataModel);
	}

	@Override
	public boolean hasUnseenChangeRecords() {
		SQLiteDatabase rdb = databaseHelper.getReadableDatabase();
		/*
		SQLite does not have a separate Boolean storage class. Instead, Boolean values are stored as integers 0 (false) and 1 (true).
		http://www.sqlite.org/datatype3.html
		 */
		String selector = "seen = 0";
		long res = DatabaseUtils
				.queryNumEntries(rdb, cupboard().getTable(ChangeRecordDataModel.class), selector);
		return res > 0;
	}

	@Override
	public List<ChangeRecordDataModel> getUnseenChangeRecords() {
		SQLiteDatabase rdb = databaseHelper.getReadableDatabase();
		DatabaseCompartment withDatabase = cupboard().withDatabase(rdb);
		/*
		SQLite does not have a separate Boolean storage class. Instead, Boolean values are stored as integers 0 (false) and 1 (true).
		http://www.sqlite.org/datatype3.html
		 */
		String selector = "seen = 0";
		return withDatabase.query(ChangeRecordDataModel.class).withSelection(selector).list();
	}
}
