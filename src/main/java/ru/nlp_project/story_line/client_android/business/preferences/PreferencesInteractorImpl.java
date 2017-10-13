package ru.nlp_project.story_line.client_android.business.preferences;

import android.util.Log;
import io.reactivex.Observable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import ru.nlp_project.story_line.client_android.business.models.SourceBusinessModel;
import ru.nlp_project.story_line.client_android.data.source.ISourcesRepository;

public class PreferencesInteractorImpl implements IPreferencesInteractor {

	private static final String TAG = PreferencesInteractorImpl.class.getSimpleName();

	@Inject
	ISourcesRepository repository;

	@Inject
	public PreferencesInteractorImpl() {
	}


	@Override
	public void upsetSources(List<SourceBusinessModel> sources) {
		repository.upsetSources(sources);
	}

	@Override
	public Observable<SourceBusinessModel> createCombinedSourcePreferencesRemoteCachedStream() {
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

}
