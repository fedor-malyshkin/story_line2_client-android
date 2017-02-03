package ru.nlp_project.story_line.client_android.builder;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ru.nlp_project.story_line.client_android.core.IDataManager;
import ru.nlp_project.story_line.client_android.core.ILocalDataStorage;
import ru.nlp_project.story_line.client_android.core.IRemoteDataStorage;
import ru.nlp_project.story_line.client_android.core.impl.DataManagerImpl;
import ru.nlp_project.story_line.client_android.core.impl.LocalDataStorageImpl;

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
	public ILocalDataStorage provideLocalStorage() {
		return new LocalDataStorageImpl(context);
	}

	@Provides
	@Singleton
	public IRemoteDataStorage provideRemoteStorage() {
		return null;
	}

	@Provides
	@Singleton
	public IDataManager provideDataManager(ILocalDataStorage localDataStorage, IRemoteDataStorage
			remoteDataStorage) {
		return new DataManagerImpl(localDataStorage, remoteDataStorage);
	}

}
