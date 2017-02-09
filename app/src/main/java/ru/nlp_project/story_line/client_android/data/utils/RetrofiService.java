package ru.nlp_project.story_line.client_android.data.utils;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import javax.inject.Singleton;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;
import ru.nlp_project.story_line.client_android.data.categories_browser.CategoriesBrowserRetrofitService;

/**
 * Created by fedor on 10.02.17.
 */
@Singleton
public class RetrofiService {

	private String baseUrl;
	private Retrofit retrofit;

	public RetrofiService(String baseUrl) {
		this.baseUrl = baseUrl;
	}

	public void build() {
		retrofit = new Retrofit.Builder()
			.baseUrl(baseUrl)
			.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
			.addConverterFactory(JacksonConverterFactory.create())
			.build();
	}

	CategoriesBrowserRetrofitService getCategoriesBrowserService() {
		return retrofit.create(CategoriesBrowserRetrofitService.class);
	}
}
