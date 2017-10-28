package ru.nlp_project.story_line.client_android.business.sources;

import io.reactivex.Observable;
import ru.nlp_project.story_line.client_android.business.IInteractor;
import ru.nlp_project.story_line.client_android.business.models.SourceBusinessModel;
import ru.nlp_project.story_line.client_android.ui.sources_browser.ISourcesBrowserPresenter;

public interface ISourcesInteractor extends IInteractor<ISourcesBrowserPresenter> {

	Observable<SourceBusinessModel> createSourceStreamRemoteCached();

	Observable<SourceBusinessModel> createSourceStreamFromCache();

	String getSourceTitleShortCached(String sourceName);

	Observable<SourceBusinessModel> createCombinedSourcesRemoteCachedStream();

	void updateSourceState(String sourceName, boolean checked);

	long getActiveSourcesCount();
}
