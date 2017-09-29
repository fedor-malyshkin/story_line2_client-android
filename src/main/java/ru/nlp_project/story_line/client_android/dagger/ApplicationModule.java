package ru.nlp_project.story_line.client_android.dagger;

import android.content.Context;
import dagger.Module;
import dagger.Provides;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import javax.inject.Singleton;
import ru.nlp_project.story_line.client_android.data.categories_browser.CategoriesBrowserRepositoryImpl;
import ru.nlp_project.story_line.client_android.data.categories_browser.ICategoriesBrowserRepository;
import ru.nlp_project.story_line.client_android.data.news_tape.INewsTapeRepository;
import ru.nlp_project.story_line.client_android.data.news_tape.NewsTapeRepositoryImpl;
import ru.nlp_project.story_line.client_android.data.news_watcher.INewsWatcherRepository;
import ru.nlp_project.story_line.client_android.data.news_watcher.NewsWatcherRepositoryImpl;
import ru.nlp_project.story_line.client_android.data.sources_browser.ISourcesBrowserRepository;
import ru.nlp_project.story_line.client_android.data.sources_browser.SourcesBrowserRepositoryImpl;
import ru.nlp_project.story_line.client_android.data.utils.DatabaseHelper;
import ru.nlp_project.story_line.client_android.data.utils.ILocalDBStorage;
import ru.nlp_project.story_line.client_android.data.utils.LocalDBStorageImpl;
import ru.nlp_project.story_line.client_android.data.utils.RetrofiService;
import ru.nlp_project.story_line.client_android.ui.utils.IImageDownloader;
import ru.nlp_project.story_line.client_android.ui.utils.ImageDownloaderImpl;

/**
 * Created by fedor on 11.01.17.
 */

@Module
public class ApplicationModule {

	public static final String BASE_URL = "http://datahouse01.nlp-project.ru:8000";
	public static final String DATABASE_NAME = "story_line.db";
	public static final int DATABASE_VERSION = 1;
	private Context context;

	public ApplicationModule(Context context) {
		this.context = context;
	}

	@Provides
	@Singleton
	public INewsWatcherRepository provideNewsWatcherRepository(NewsWatcherRepositoryImpl
			implementation) {
		return implementation;
	}

	@Provides
	@Singleton
	public INewsTapeRepository provideNewsTapeRepository(NewsTapeRepositoryImpl implementation) {
		return implementation;
	}


	@Provides
	@Singleton
	public ISourcesBrowserRepository provideSourcesBrowserModuleRepository
			(SourcesBrowserRepositoryImpl implementation) {
		return implementation;
	}


	@Provides
	@Singleton
	public ICategoriesBrowserRepository provideCateriesBrowserModuleRepository(
			CategoriesBrowserRepositoryImpl instance) {
		return instance;
	}

	@Singleton
	@Provides
	public DatabaseHelper provideDatabaseHelper() {
		DatabaseHelper dbHelper = new DatabaseHelper(context, DATABASE_NAME, DATABASE_VERSION);
		return dbHelper;
	}

	@Provides
	@SchedulerType(SchedulerType.background)
	public Scheduler provideBackgroundScheduler() {
		return Schedulers.io();
	}


	@Provides
	@SchedulerType(SchedulerType.ui)
	public Scheduler provideUIScheduler() {
		return AndroidSchedulers.mainThread();
	}

	@Provides
	@Singleton
	public RetrofiService provideRetrofiService() {
		RetrofiService service = new RetrofiService(BASE_URL);
		service.build();
		return service;
	}

	@Provides
	@Singleton
	public ILocalDBStorage provideLocalDBStorage(LocalDBStorageImpl instance) {
		return instance;
	}

	@Provides
	@Singleton
	public IImageDownloader provideImageDownloader() {
		return new ImageDownloaderImpl(BASE_URL);
	}
}

