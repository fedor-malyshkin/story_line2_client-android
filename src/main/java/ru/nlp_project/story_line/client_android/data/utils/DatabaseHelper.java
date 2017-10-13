package ru.nlp_project.story_line.client_android.data.utils;

import static nl.qbusict.cupboard.CupboardFactory.cupboard;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import ru.nlp_project.story_line.client_android.data.models.NewsArticleDataModel;
import ru.nlp_project.story_line.client_android.data.models.NewsHeaderDataModel;
import ru.nlp_project.story_line.client_android.data.models.SourceDataModel;

public class DatabaseHelper extends SQLiteOpenHelper {

	private static final String TAG = DatabaseHelper.class.getSimpleName();

	static {
		// register our models
		cupboard().register(NewsArticleDataModel.class);
		cupboard().register(NewsHeaderDataModel.class);
		cupboard().register(SourceDataModel.class);
	}

	public DatabaseHelper(Context context, String databaseName, int databaseVersion) {
		super(context, databaseName, null, databaseVersion);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// this will ensure that all tables are created
		cupboard().withDatabase(db).createTables();

		SQLiteDatabase wdb = this.getWritableDatabase();
		wdb.execSQL(String.format("CREATE INDEX IF NOT EXISTS %1$s.%2$s ON "
				+ "%1$s (%2$s);", cupboard().getTable(NewsHeaderDataModel.class), "source"));
		wdb.execSQL(String.format("CREATE INDEX IF NOT EXISTS %1$s.%2$s ON "
				+ "%1$s (%2$s);", cupboard().getTable(NewsHeaderDataModel.class), "serverId"));
		wdb.execSQL(String.format("CREATE INDEX IF NOT EXISTS %1$s.%2$s ON "
				+ "%1$s (%2$s);", cupboard().getTable(NewsArticleDataModel.class), "serverId"));
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// this will upgrade tables, adding columns and new tables.
		// Note that existing columns will not be converted
		cupboard().withDatabase(db).upgradeTables();
		// do migration work if you have an alteration to make to your schema here

	}

}