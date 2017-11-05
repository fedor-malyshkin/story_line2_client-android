package ru.nlp_project.story_line.client_android.business.sources;

import android.util.Log;
import io.reactivex.Observable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import javax.inject.Inject;
import ru.nlp_project.story_line.client_android.business.models.SourceBusinessModel;
import ru.nlp_project.story_line.client_android.data.sources.ISourcesRepository;


public class SourcesInteractorImpl implements ISourcesInteractor {

	private static final String TAG = SourcesInteractorImpl.class.getName();
	@Inject
	ISourcesRepository repository;
	private Map<String, SourceBusinessModel> sourcesCache = Collections
			.synchronizedMap(new HashMap<>());


	@Inject
	public SourcesInteractorImpl() {
	}

	@Override
	public void initializeInteractor() {
	}

	@Override
	public Observable<SourceBusinessModel> createSourceStreamRemoteCached() {
		Map<String, SourceBusinessModel> newSources = new HashMap<>();
		Observable<SourceBusinessModel> result = repository
				.createSourceStreamRemoteCached().doOnNext(s -> newSources.put(s.getName(), s))
				.doOnComplete(
						() -> {
							sourcesCache.clear();
							sourcesCache.putAll(newSources);
						}
				);
		// return result with listeners (to update cache)
		return result;
	}

	@Override
	public Observable<SourceBusinessModel> createSourceStreamCached() {
		Map<String, SourceBusinessModel> newSources = new HashMap<>();
		Observable<SourceBusinessModel> result = repository.createSourceStreamLocal()
				.doOnNext(s -> newSources.put(s.getName(), s))
				.doOnComplete(
						() -> {
							sourcesCache.clear();
							sourcesCache.putAll(newSources);
						}
				);
		// return result with listeners (to update cache)
		return result;
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
	public Observable<SourceBusinessModel> createCombinedSourcesStreamRemoteCached() {
		Map<String, SourceBusinessModel> remoteLocalMap = new HashMap<>();
		Observable<SourceBusinessModel> sourceRemote = Observable
				.wrap(repository.createSourceRemoteStream());
		// подстраховываемся локальным источником при проблемах с  сетью
		sourceRemote = sourceRemote
				.onErrorResumeNext(repository.createSourceStreamLocal());
		// фактически: берём локальный источник и его трансформируем в Map
		// однако: при первоначальной подписке -- запрашиваем данные из сети
		// и для каждой записи из БД -- объединем с данными из сети (модифицируя получаемые данные)
		return sourceRemote.doOnSubscribe(
				(disposable) -> repository.createSourceStreamLocal().subscribe(
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
					// update active/oder/additionDate/etc info
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


}
