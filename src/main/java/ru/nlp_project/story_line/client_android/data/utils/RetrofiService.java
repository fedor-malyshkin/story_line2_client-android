package ru.nlp_project.story_line.client_android.data.utils;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import javax.inject.Singleton;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;
import ru.nlp_project.story_line.client_android.data.categories_browser.CategoriesBrowserRetrofitService;
import ru.nlp_project.story_line.client_android.data.news_tape.NewsTapeRetrofitService;
import ru.nlp_project.story_line.client_android.data.news_watcher.NewsWatcherRetrofitService;
import ru.nlp_project.story_line.client_android.data.sources_browser.SourcesBrowserRetrofitService;

@Singleton
public class RetrofiService {

	private String baseUrl;
	private Retrofit retrofit;

	public RetrofiService(String baseUrl) {
		this.baseUrl = baseUrl;
	}

	public void build() {
		OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();
		clientBuilder.addNetworkInterceptor(new StethoInterceptor());

		retrofit = new Retrofit.Builder()
			.baseUrl(baseUrl)
			.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
			.addConverterFactory(JacksonConverterFactory.create())
			.client(clientBuilder.build())
			.build();
	}

	public CategoriesBrowserRetrofitService getCategoriesBrowserService() {
		return retrofit.create(CategoriesBrowserRetrofitService.class);
	}

	public SourcesBrowserRetrofitService getSourcesBrowserService() {
		return retrofit.create(SourcesBrowserRetrofitService.class);
	}

	public NewsWatcherRetrofitService getNewsWatcherService() {
		return retrofit.create(NewsWatcherRetrofitService.class);
	}

	public NewsTapeRetrofitService getNewsTapeService() {
		return retrofit.create(NewsTapeRetrofitService.class);
	}

}
