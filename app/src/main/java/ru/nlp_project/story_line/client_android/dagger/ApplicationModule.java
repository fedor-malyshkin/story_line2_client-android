package ru.nlp_project.story_line.client_android.dagger;

import android.content.Context;
import dagger.Module;
import dagger.Provides;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import javax.inject.Singleton;
import ru.nlp_project.story_line.client_android.data.categories_browser.ICategoriesBrowserRepository;
import ru.nlp_project.story_line.client_android.data.news_tape.INewsTapeRepository;
import ru.nlp_project.story_line.client_android.data.news_tape.NewsTapeRepositoryDemo;
import ru.nlp_project.story_line.client_android.data.sources_browser.ISourcesBrowserRepository;
import ru.nlp_project.story_line.client_android.data.sources_browser.SourcesBrowserRepositoryDemo;
import ru.nlp_project.story_line.client_android.data.utils.RetrofiService;
import ru.nlp_project.story_line.client_android.data.utils.ServerWebEndpointRepositoryImpl;

/**
 * Created by fedor on 11.01.17.
 */

@Module
public class ApplicationModule {

	private Context context;
	public static final String BASE_URL = "http://localhost:3000/";

	public ApplicationModule(Context context) {
		this.context = context;
	}


	@Provides
	@Singleton
	public INewsTapeRepository provideNewsTapeRepository(NewsTapeRepositoryDemo implementation) {
		return implementation;
	}


	@Provides
	@Singleton
	public ISourcesBrowserRepository provideSourcesBrowserModuleRepository
		(SourcesBrowserRepositoryDemo implementation) {
		return implementation;
	}


	@Provides
	@Singleton
	public ICategoriesBrowserRepository provideCateriesBrowserModuleRepository(
		RetrofiService retrofiService,
		@SchedulerType(SchedulerType.background) Scheduler scheduler) {
		return new ServerWebEndpointRepositoryImpl(retrofiService, scheduler);
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
}
