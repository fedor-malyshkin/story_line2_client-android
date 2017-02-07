package ru.nlp_project.story_line.client_android.dagger;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ru.nlp_project.story_line.client_android.data.news_tape.INewsTapeRepository;
import ru.nlp_project.story_line.client_android.data.news_tape.NewsTapeRepositoryDemo;
import ru.nlp_project.story_line.client_android.data.sources_browser.ISourcesBrowserRepository;
import ru.nlp_project.story_line.client_android.data.sources_browser.SourcesBrowserRepositoryDemo;

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
			(SourcesBrowserRepositoryDemo
					 implementation) {
		return implementation;
	}
}
