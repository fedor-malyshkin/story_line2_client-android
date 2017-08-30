package ru.nlp_project.story_line.client_android.data.sources_browser;

import io.reactivex.Observable;
import ru.nlp_project.story_line.client_android.data.models.SourceDataModel;

public interface ISourcesBrowserRepository {
	Observable<SourceDataModel> createSourceStream();
}
