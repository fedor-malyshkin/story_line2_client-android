package ru.nlp_project.story_line.client_android.business.preferences;

import android.util.Log;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.inject.Inject;
import ru.nlp_project.story_line.client_android.business.models.SourceBusinessModel;
import ru.nlp_project.story_line.client_android.dagger.PreferencesScope;
import ru.nlp_project.story_line.client_android.data.models.SourceDataModel;
import ru.nlp_project.story_line.client_android.data.sources_browser.ISourcesBrowserRepository;

@PreferencesScope
public class PreferencesInteractorImpl implements IPreferencesInteractor {

	private static final String TAG = PreferencesInteractorImpl.class.getSimpleName();

	@Inject
	ISourcesBrowserRepository repository;

	private ObservableTransformer<SourceDataModel,
			SourceBusinessModel> transformer = new PreferencesInteractorImpl.DataToBusinessModelTransformer();


	@Inject
	public PreferencesInteractorImpl() {
	}


	@Override
	public void upsetSources(List<SourceBusinessModel> sources) {
		List<SourceDataModel> list = null;
		if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
			list = sources.stream().map(
					s -> new SourceDataModel(s.getId(), s.getName(), s.getTitle(), s.getTitleShort(),
							s.isEnabled(),
							s.getOrder())).collect(Collectors.toList());
		} else {
			list = new ArrayList<>();
			for (SourceBusinessModel s : sources) {
				list.add(new SourceDataModel(s.getId(), s.getName(), s.getTitle(), s.getTitleShort(),
						s.isEnabled(),
						s.getOrder()));
			}
		}
		repository.upsetSources(list);
	}

	@Override
	public Observable<SourceBusinessModel> createCombinedSourcePreferencesRemoteCachedStream() {
		Map<String, SourceDataModel> remoteLocalMap = new HashMap<>();
		Observable<SourceDataModel> source = Observable.wrap(repository.createSourceStreamRemote());
		// подстраховываемся локальным источником при проблемах с  сетью
		source = source
				.onErrorResumeNext(repository.createSourceStreamLocal());
		// фактически: берём локальный источник и его трансформируем
		// однако: при первоначальной подписке -- запрашиваем данные из сети
		// и для каждой записи из БД -- объединем с данными из сети
		return source.doOnSubscribe(
				(disposable) -> repository.createSourceStreamLocal().subscribe(
						// onNext
						val -> remoteLocalMap.put(val.getName(), val),
						// onError
						exc -> Log.e(TAG, exc.getMessage(), exc))
		).doOnNext(
				(SourceDataModel model) -> {
					SourceDataModel remoteLocal = remoteLocalMap.get(model.getName());
					if (remoteLocal == null) {
						return;
					}
					// update active/oder info
					model.updateSystemData(remoteLocal);
					// set ID !!!
					model.setId(remoteLocal.getId());
				}
		).compose(transformer);
	}

	private class DataToBusinessModelTransformer implements
			ObservableTransformer<SourceDataModel,
					SourceBusinessModel> {

		@Override
		public ObservableSource<SourceBusinessModel> apply(
				Observable<SourceDataModel> upstream) {
			return upstream.map(
					data -> new SourceBusinessModel(data.getId(), data.getName(), data.getTitle(), data
							.getTitleShort(), data.isEnabled(), data.getOrder())
			);
		}
	}

}
