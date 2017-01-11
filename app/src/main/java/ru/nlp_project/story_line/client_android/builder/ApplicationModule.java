package ru.nlp_project.story_line.client_android.builder;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.nlp_project.story_line.client_android.core.IDataManager;
import ru.nlp_project.story_line.client_android.core.ILocalDataStorage;
import ru.nlp_project.story_line.client_android.core.IRemoteDataStorage;
import ru.nlp_project.story_line.client_android.core.impl.DataManagerImpl;
import ru.nlp_project.story_line.client_android.core.impl.LocalDataStorageImpl;
import ru.nlp_project.story_line.client_android.core.impl.RemoteDataStorageImpl;
import ru.nlp_project.story_line.client_android.core.impl.StoryLine2EndpointInterface;

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
		Gson gson = new GsonBuilder()
				.setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
				.create();

		Retrofit retrofit = new Retrofit.Builder()
				.baseUrl(BASE_URL)
				.addConverterFactory(GsonConverterFactory.create(gson))
				.build();
		StoryLine2EndpointInterface apiService =
				retrofit.create(StoryLine2EndpointInterface.class);

		return new RemoteDataStorageImpl(apiService);
	}

	@Provides
	@Singleton
	public IDataManager provideDataManager(ILocalDataStorage localDataStorage, IRemoteDataStorage
			remoteDataStorage) {
		return new DataManagerImpl(localDataStorage, remoteDataStorage);
	}

}
