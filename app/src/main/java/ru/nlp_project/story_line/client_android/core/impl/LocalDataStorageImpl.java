package ru.nlp_project.story_line.client_android.core.impl;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import nl.qbusict.cupboard.QueryResultIterable;
import ru.nlp_project.story_line.client_android.core.ILocalDataStorage;
import ru.nlp_project.story_line.client_android.data.model.NewsArticleHeader;

import static nl.qbusict.cupboard.CupboardFactory.cupboard;

/**
 * Created by fedor on 11.01.17.
 */

public class LocalDataStorageImpl extends SQLiteOpenHelper implements ILocalDataStorage {

	private static final String DATABASE_NAME = "newsArticles.db";
	private static final int DATABASE_VERSION = 1;
	private SQLiteDatabase rd;
	private SQLiteDatabase wd;

	public LocalDataStorageImpl(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	static {
		// register our models
		cupboard().register(NewsArticleHeader.class);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// this will ensure that all tables are created
		cupboard().withDatabase(db).createTables();
		// add indexes and other database tweaks in this method if you want

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// this will upgrade tables, adding columns and new tables.
		// Note that existing columns will not be converted
		cupboard().withDatabase(db).upgradeTables();
		// do migration work if you have an alteration to make to your schema here

	}


	private SQLiteDatabase getReadableDatabaseInt() {
		if (rd == null || rd.isOpen())
			rd = super.getReadableDatabase();
		return rd;
	}

	private SQLiteDatabase getWritableDatabaseInt() {
		if (wd == null)
			wd = super.getWritableDatabase();
		return wd;
	}

	@Override
	public List<NewsArticleHeader> getExistingNewsArticleHeaders() {
		List<NewsArticleHeader> result = new ArrayList<>();
		SQLiteDatabase readableDatabase = getReadableDatabaseInt();
		// Get the cursor for this query
		Cursor bunnies = cupboard().withDatabase(readableDatabase).query(NewsArticleHeader.class)
				.getCursor();
		try {
			// Iterate Bunnys
			QueryResultIterable<NewsArticleHeader> itr = cupboard().withCursor(bunnies)
					.iterate(NewsArticleHeader.class);
			for (NewsArticleHeader news : itr) {
				result.add(news);
			}

		} finally {
			// close the cursor
			bunnies.close();
		}
		return result;
	}

	@Override
	public void populateDB() {
		SQLiteDatabase writableDatabase = getWritableDatabaseInt();
		for (int i = 0; i < 1000; i++) {
			cupboard().withDatabase(writableDatabase)
					.put(new NewsArticleHeader(null, "name:" + System.currentTimeMillis()));
		}

	}

}
