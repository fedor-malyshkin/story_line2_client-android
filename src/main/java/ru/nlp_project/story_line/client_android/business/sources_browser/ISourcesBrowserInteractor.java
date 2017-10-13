package ru.nlp_project.story_line.client_android.business.sources_browser;

import io.reactivex.Observable;
import ru.nlp_project.story_line.client_android.business.models.SourceBusinessModel;

public interface ISourcesBrowserInteractor {

	void initialize();

	Observable<SourceBusinessModel> createSourceStreamRemoteCached();

	Observable<SourceBusinessModel> createSourceStreamFromCache();

	String getSourceTitleShortCached(String source);
}
