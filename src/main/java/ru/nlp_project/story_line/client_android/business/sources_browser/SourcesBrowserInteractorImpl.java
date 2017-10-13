package ru.nlp_project.story_line.client_android.business.sources_browser;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import java.util.HashMap;
import java.util.Map;
import javax.inject.Inject;
import ru.nlp_project.story_line.client_android.business.models.SourceBusinessModel;
import ru.nlp_project.story_line.client_android.data.models.SourceDataModel;
import ru.nlp_project.story_line.client_android.data.source.ISourcesRepository;


public class SourcesBrowserInteractorImpl implements ISourcesBrowserInteractor {

	@Inject
	ISourcesRepository repository;
	private Map<String, SourceBusinessModel> sourcesCache = new HashMap<>();


	@Inject
	public SourcesBrowserInteractorImpl() {
	}

	@Override
	public void initialize() {
		Map<String, SourceBusinessModel> newSources = new HashMap<>();
		createSourceStreamRemoteCached().subscribe(
				// onNext
				s -> {
					newSources.put(s.getName(), s);
				},
				// onError
				e -> {
				},
				// onComplete
				() -> {
					sourcesCache.clear();
					sourcesCache.putAll(newSources);
				});
	}

	@Override
	public Observable<SourceBusinessModel> createSourceStreamRemoteCached() {
		return repository.createSourceRemoteCachedStream();
	}

	@Override
	public Observable<SourceBusinessModel> createSourceStreamFromCache() {
		return repository.createSourceLocalStream();
	}

	@Override
	public String getSourceTitleShortCached(String sourceName) {
		if (sourceName == null) {
			return "";
		}
		SourceBusinessModel model = sourcesCache.get(sourceName);
		if (model == null) {
			return "";
		}
		return model.getTitleShort();
	}


}
