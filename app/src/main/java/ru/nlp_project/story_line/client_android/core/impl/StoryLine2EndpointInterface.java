package ru.nlp_project.story_line.client_android.core.impl;

import retrofit2.Call;
import retrofit2.http.GET;
import ru.nlp_project.story_line.client_android.datamodel.NewsArticle;

/**
 * Created by fedor on 11.01.17.
 */

public interface StoryLine2EndpointInterface {
	@GET("news")
	Call<NewsArticle> getNewsArticles();

}
