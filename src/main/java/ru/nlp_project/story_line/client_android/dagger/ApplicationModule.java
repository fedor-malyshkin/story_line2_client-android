package ru.nlp_project.story_line.client_android.dagger;

import android.content.Context;
import dagger.Module;
import dagger.Provides;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import javax.inject.Singleton;
import ru.nlp_project.story_line.client_android.BuildConfig;
import ru.nlp_project.story_line.client_android.business.IStartupInteractor;
import ru.nlp_project.story_line.client_android.business.StartupInteractorImpl;
import ru.nlp_project.story_line.client_android.business.news_browser.INewsBrowserInteractor;
import ru.nlp_project.story_line.client_android.business.news_browser.NewsBrowserInteractorImpl;
import ru.nlp_project.story_line.client_android.business.news_tape.INewsTapeInteractor;
import ru.nlp_project.story_line.client_android.business.news_tape.NewsTapeInteractorImpl;
import ru.nlp_project.story_line.client_android.business.news_watcher.INewsWatcherInteractor;
import ru.nlp_project.story_line.client_android.business.news_watcher.NewsWatcherInteractorImpl;
import ru.nlp_project.story_line.client_android.business.preferences.IPreferencesInteractor;
import ru.nlp_project.story_line.client_android.business.preferences.PreferencesInteractorImpl;
import ru.nlp_project.story_line.client_android.business.sources_browser.ISourcesBrowserInteractor;
import ru.nlp_project.story_line.client_android.business.sources_browser.SourcesBrowserInteractorImpl;
import ru.nlp_project.story_line.client_android.data.IStartupRepository;
import ru.nlp_project.story_line.client_android.data.StartupRepositoryImpl;
import ru.nlp_project.story_line.client_android.data.news_article.INewsArticlesRepository;
import ru.nlp_project.story_line.client_android.data.news_article.NewsArticlesRepositoryImpl;
import ru.nlp_project.story_line.client_android.data.news_header.INewsHeadersRepository;
import ru.nlp_project.story_line.client_android.data.news_header.NewsHeadersRepositoryImpl;
import ru.nlp_project.story_line.client_android.data.source.ISourcesRepository;
import ru.nlp_project.story_line.client_android.data.source.SourcesRepositoryImpl;
import ru.nlp_project.story_line.client_android.data.utils.DatabaseHelper;
import ru.nlp_project.story_line.client_android.data.utils.ILocalDBStorage;
import ru.nlp_project.story_line.client_android.data.utils.LocalDBStorageImpl;
import ru.nlp_project.story_line.client_android.data.utils.RetrofitService;
import ru.nlp_project.story_line.client_android.ui.utils.IImageDownloader;
import ru.nlp_project.story_line.client_android.ui.utils.ImageDownloaderImpl;

/**
 * Created by fedor on 11.01.17.
 */

@Module
public class ApplicationModule {

	public static final String BASE_URL = "http://datahouse01.nlp-project.ru:8000";
	public static final String DATABASE_NAME = "story_line.db";
	public static final int DATABASE_VERSION = BuildConfig.VERSION_CODE;
	private Context context;

	public ApplicationModule(Context context) {
		this.context = context;
	}

	@Provides
	@Singleton
	public INewsArticlesRepository provideNewsArticlesRepository(NewsArticlesRepositoryImpl
			implementation) {
		implementation.initializeRepository();
		return implementation;
	}

	@Provides
	@Singleton
	public INewsHeadersRepository provideNewsHeadersRepository(
			NewsHeadersRepositoryImpl implementation) {
		implementation.initializeRepository();
		return implementation;
	}


	@Provides
	@Singleton
	public ISourcesRepository provideSourcesRepository
			(SourcesRepositoryImpl implementation) {
		implementation.initializeRepository();
		return implementation;
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
	public RetrofitService provideRetrofitService() {
		RetrofitService service = new RetrofitService(BASE_URL);
		service.build();
		return service;
	}

	@Provides
	@Singleton
	public ILocalDBStorage provideLocalDBStorage(LocalDBStorageImpl instance) {
		instance.initializeDBStorage();
		return instance;
	}

	@Provides
	@Singleton
	public IImageDownloader provideImageDownloader() {
		return new ImageDownloaderImpl(BASE_URL);
	}


	@Provides
	@Singleton
	public IStartupInteractor provideStartupInteractor(StartupInteractorImpl implementation) {
		implementation.initializeInteractor();
		return implementation;
	}


	@Provides
	@Singleton
	public IStartupRepository provideStartupRepository(StartupRepositoryImpl implementation) {
		implementation.initializeRepository();
		return implementation;
	}

	@Provides
	@Singleton
	public ISourcesBrowserInteractor provideSourcesBrowserInteractor(SourcesBrowserInteractorImpl
			implementation) {
		implementation.initializeInteractor();
		return implementation;
	}

	@Provides
	@Singleton
	public INewsBrowserInteractor provideNewsBrowserInteractor(NewsBrowserInteractorImpl
			implementation) {
		implementation.initializeInteractor();
		return implementation;
	}

	@Provides
	@Singleton
	public INewsTapeInteractor provideNewsTapeInteractor(NewsTapeInteractorImpl implementation) {
		implementation.initializeInteractor();
		return implementation;
	}

	@Provides
	@Singleton
	public IPreferencesInteractor providePreferencesInteractor(PreferencesInteractorImpl
			implementation) {
		implementation.initializeInteractor();
		return implementation;
	}

	@Provides
	@Singleton
	public INewsWatcherInteractor provideNewsWatcherInteractor(NewsWatcherInteractorImpl
			implementation) {
		implementation.initializeInteractor();
		return implementation;
	}


}

