package ru.nlp_project.story_line.client_android.data.utils;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import javax.inject.Singleton;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import ru.nlp_project.story_line.client_android.data.feedback.FeedbackRetrofitService;
import ru.nlp_project.story_line.client_android.data.news_article.NewsArticlesRetrofitService;
import ru.nlp_project.story_line.client_android.data.news_header.NewsHeadersRetrofitService;
import ru.nlp_project.story_line.client_android.data.source.SourcesRetrofitService;

@Singleton
public class RetrofitService {

	private String baseUrl;
	private Retrofit retrofitJson;
	private Retrofit retrofitMisc;

	public RetrofitService(String baseUrl) {
		this.baseUrl = baseUrl;
	}

	public void build() {
		OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();
		//	clientBuilder.addNetworkInterceptor(new StethoInterceptor());

		retrofitJson = new Retrofit.Builder()
				.baseUrl(baseUrl)
				.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
				.addConverterFactory(JacksonConverterFactory.create())
				.client(clientBuilder.build())
				.build();

		retrofitMisc = new Retrofit.Builder()
				.baseUrl(baseUrl)
				.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
				.addConverterFactory(ScalarsConverterFactory.create())
				.client(clientBuilder.build())
				.build();

	}

	public FeedbackRetrofitService getFeedbackRetrofitService() {
		return retrofitMisc.create(FeedbackRetrofitService.class);
	}

	public SourcesRetrofitService getSourcesBrowserService() {
		return retrofitJson.create(SourcesRetrofitService.class);
	}

	public NewsArticlesRetrofitService getNewsWatcherService() {
		return retrofitJson.create(NewsArticlesRetrofitService.class);
	}

	public NewsHeadersRetrofitService getNewsTapeService() {
		return retrofitJson.create(NewsHeadersRetrofitService.class);
	}

}
