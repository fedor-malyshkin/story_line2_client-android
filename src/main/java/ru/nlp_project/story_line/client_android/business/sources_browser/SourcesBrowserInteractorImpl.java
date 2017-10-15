package ru.nlp_project.story_line.client_android.business.sources_browser;

import android.util.Log;
import io.reactivex.Observable;
import java.util.HashMap;
import java.util.Map;
import javax.inject.Inject;
import ru.nlp_project.story_line.client_android.business.models.SourceBusinessModel;
import ru.nlp_project.story_line.client_android.data.source.ISourcesRepository;
import ru.nlp_project.story_line.client_android.ui.sources_browser.ISourcesBrowserPresenter;


public class SourcesBrowserInteractorImpl implements ISourcesBrowserInteractor {

	private static final String TAG = SourcesBrowserInteractorImpl.class.getName();
	@Inject
	ISourcesRepository repository;
	private Map<String, SourceBusinessModel> sourcesCache = new HashMap<>();


	@Inject
	public SourcesBrowserInteractorImpl() {
	}

	@Override
	public void initializeInteractor() {
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

	@Override
	public Observable<SourceBusinessModel> createCombinedSourcesRemoteCachedStream() {
		Map<String, SourceBusinessModel> remoteLocalMap = new HashMap<>();
		Observable<SourceBusinessModel> sourceRemote = Observable
				.wrap(repository.createSourceRemoteStream());
		// подстраховываемся локальным источником при проблемах с  сетью
		sourceRemote = sourceRemote
				.onErrorResumeNext(repository.createSourceLocalStream());
		// фактически: берём локальный источник и его трансформируем в Map
		// однако: при первоначальной подписке -- запрашиваем данные из сети
		// и для каждой записи из БД -- объединем с данными из сети (модифицируя получаемые данные)
		return sourceRemote.doOnSubscribe(
				(disposable) -> repository.createSourceLocalStream().subscribe(
						// onNext
						val -> remoteLocalMap.put(val.getName(), val),
						// onError
						exc -> Log.e(TAG, exc.getMessage(), exc))
		).doOnNext(
				(SourceBusinessModel model) -> {
					SourceBusinessModel remoteLocal = remoteLocalMap.get(model.getName());
					if (remoteLocal == null) {
						return;
					}
					// update active/oder info
					model.updateSystemData(remoteLocal);
					// set ID !!!
					model.setId(remoteLocal.getId());
				}
		);

	}

	@Override
	public void updateSourceState(String sourceName, boolean checked) {
		repository.updateSourceState(sourceName, checked);

		sourcesCache.get(sourceName).setEnabled(checked);
	}

	@Override
	public long getActiveSourcesCount() {
		return repository.getActiveSourcesCount();
	}

	@Override
	public void bindPresenter(ISourcesBrowserPresenter presenter) {

	}
}
