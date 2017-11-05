package ru.nlp_project.story_line.client_android.business.sources;

import io.reactivex.Observable;
import ru.nlp_project.story_line.client_android.business.IInteractor;
import ru.nlp_project.story_line.client_android.business.models.SourceBusinessModel;

public interface ISourcesInteractor extends IInteractor {

	/**
	 * Receive data from network (primary), from cache (in case of failure).
	 * <p/>
	 * <b>Important Note:</b> if data was received from network - they aren't enrichied by local info (order,
	 * additionDate, enabled, etc...). if you need this date use ({@link
	 * #createCombinedSourcesStreamRemoteCached()} - but you don't receive this date for fresh
	 * sources, {@link #createSourceStreamCached()} - only local data)
	 */
	Observable<SourceBusinessModel> createSourceStreamRemoteCached();

	/**
	 * Receive data from cache.
	 */
	Observable<SourceBusinessModel> createSourceStreamCached();

	String getSourceTitleShortCached(String sourceName);

	/**
	 * Receive data from network (primary), from cache (in case of failure).
	 * <p/>
	 * <b>Note:</b> data from network will be combined with local date (except cases for very fresh data).
	 */
	Observable<SourceBusinessModel> createCombinedSourcesStreamRemoteCached();

	void updateSourceState(String sourceName, boolean checked);

	long getActiveSourcesCount();
}
