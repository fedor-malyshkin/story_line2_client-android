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
import ru.nlp_project.story_line.client_android.business.change_records.ChangeRecordsInteractorImpl;
import ru.nlp_project.story_line.client_android.business.change_records.IChangeRecordsInteractor;
import ru.nlp_project.story_line.client_android.business.feedback.FeedbackInteractorImpl;
import ru.nlp_project.story_line.client_android.business.feedback.IFeedbackInteractor;
import ru.nlp_project.story_line.client_android.business.news_articles.INewsArticlesInteractor;
import ru.nlp_project.story_line.client_android.business.news_articles.NewsArticlesInteractorImpl;
import ru.nlp_project.story_line.client_android.business.news_headers.INewsHeadersInteractor;
import ru.nlp_project.story_line.client_android.business.news_headers.NewsHeadersInteractorImpl;
import ru.nlp_project.story_line.client_android.business.preferences.IPreferencesInteractor;
import ru.nlp_project.story_line.client_android.business.preferences.PreferencesInteractorImpl;
import ru.nlp_project.story_line.client_android.business.sources.ISourcesInteractor;
import ru.nlp_project.story_line.client_android.business.sources.SourcesInteractorImpl;
import ru.nlp_project.story_line.client_android.data.IStartupRepository;
import ru.nlp_project.story_line.client_android.data.StartupRepositoryImpl;
import ru.nlp_project.story_line.client_android.data.change_records.ChangeRecordsRepositoryImpl;
import ru.nlp_project.story_line.client_android.data.change_records.IChangeRecordsRepository;
import ru.nlp_project.story_line.client_android.data.feedback.FeedbackRepositoryImpl;
import ru.nlp_project.story_line.client_android.data.feedback.IFeedbackRepository;
import ru.nlp_project.story_line.client_android.data.news_articles.INewsArticlesRepository;
import ru.nlp_project.story_line.client_android.data.news_articles.NewsArticlesRepositoryImpl;
import ru.nlp_project.story_line.client_android.data.news_headers.INewsHeadersRepository;
import ru.nlp_project.story_line.client_android.data.news_headers.NewsHeadersRepositoryImpl;
import ru.nlp_project.story_line.client_android.data.sources.ISourcesRepository;
import ru.nlp_project.story_line.client_android.data.sources.SourcesRepositoryImpl;
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
	//	public static final String BASE_URL = "http://192.168.1.100:8001";
	public static final String DATABASE_NAME = "story_line.db";
	public static final int DATABASE_VERSION = BuildConfig.VERSION_CODE;
	private Context context;

	public ApplicationModule(Context context) {
		this.context = context;
	}


	@Provides
	@Singleton
	public IFeedbackRepository provideFeedbackRepository(FeedbackRepositoryImpl implementation) {
		implementation.initializeRepository();
		return implementation;
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
		return new ImageDownloaderImpl(BASE_URL, context);
	}


	@Provides
	@Singleton
	public IFeedbackInteractor provideFeedbackInteractor(FeedbackInteractorImpl implementation) {
		implementation.initializeInteractor();
		return implementation;
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
	public ISourcesInteractor provideSourcesBrowserInteractor(SourcesInteractorImpl
			implementation) {
		implementation.initializeInteractor();
		return implementation;
	}

	@Provides
	@Singleton
	public INewsHeadersInteractor provideNewsHeadersInteractor(NewsHeadersInteractorImpl
			implementation) {
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
	public INewsArticlesInteractor provideNewsArticlesInteractor(NewsArticlesInteractorImpl
			implementation) {
		implementation.initializeInteractor();
		return implementation;
	}


	@Provides
	@Singleton
	public IChangeRecordsRepository provideChangeRecordsRepository(ChangeRecordsRepositoryImpl
			implementation) {
		implementation.initializeRepository();
		return implementation;
	}


	@Provides
	@Singleton
	public IChangeRecordsInteractor provideChangeRecordsInteractor(ChangeRecordsInteractorImpl
			implementation) {
		implementation.initializeInteractor();
		return implementation;
	}

}

