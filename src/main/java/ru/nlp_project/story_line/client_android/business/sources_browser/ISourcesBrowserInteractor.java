package ru.nlp_project.story_line.client_android.business.sources_browser;

import io.reactivex.Observable;
import ru.nlp_project.story_line.client_android.business.IInteractor;
import ru.nlp_project.story_line.client_android.business.models.SourceBusinessModel;
import ru.nlp_project.story_line.client_android.ui.sources_browser.ISourcesBrowserPresenter;

public interface ISourcesBrowserInteractor extends IInteractor<ISourcesBrowserPresenter> {

	Observable<SourceBusinessModel> createSourceStreamRemoteCached();

	Observable<SourceBusinessModel> createSourceStreamFromCache();

	String getSourceTitleShortCached(String sourceName);
}
