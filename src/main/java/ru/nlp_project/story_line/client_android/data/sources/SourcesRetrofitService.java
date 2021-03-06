package ru.nlp_project.story_line.client_android.data.sources;

import io.reactivex.Observable;
import java.util.List;
import retrofit2.http.GET;
import ru.nlp_project.story_line.client_android.data.models.SourceDataModel;

public interface SourcesRetrofitService {

	@GET("sources")
	Observable<List<SourceDataModel>> list();

}
