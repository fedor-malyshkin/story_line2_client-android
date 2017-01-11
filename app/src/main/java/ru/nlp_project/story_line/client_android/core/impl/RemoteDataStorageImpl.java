package ru.nlp_project.story_line.client_android.core.impl;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.nlp_project.story_line.client_android.core.IRemoteDataStorage;
import ru.nlp_project.story_line.client_android.datamodel.NewsArticle;

/**
 * Created by fedor on 11.01.17.
 */

public class RemoteDataStorageImpl implements IRemoteDataStorage {

	private final StoryLine2EndpointInterface apiService;

	public RemoteDataStorageImpl(StoryLine2EndpointInterface apiService) {
		this.apiService = apiService;
	}

	@Override
	public List<NewsArticle> getNewsArticles() {

		Call<NewsArticle> call = apiService.getNewsArticles();
		call.enqueue(new Callback<NewsArticle>() {
			@Override
			public void onResponse(Call<NewsArticle> call, Response<NewsArticle> response) {

			}

			@Override
			public void onFailure(Call<NewsArticle> call, Throwable throwable) {
				throwable.printStackTrace();
			}
		});
		return null;
	}

	@Override
	public List<NewsArticle> getNewsArticles(long fromId) {
		return null;
	}
}
