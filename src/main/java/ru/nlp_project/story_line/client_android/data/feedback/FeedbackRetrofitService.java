package ru.nlp_project.story_line.client_android.data.feedback;

import io.reactivex.Completable;
import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.CallAdapter;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.PUT;

public interface FeedbackRetrofitService {

	@GET("feedback/about")
	Observable<String> getAboutInfo();

	@FormUrlEncoded
	@PUT("feedback/send")
	Completable sendFeedback(@Field("from") String from, @Field("message") String feedBack);

}
